package es.ujaen.rlc00008.gnbwallet.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.ButterKnife;
import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.ActivateInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.ChallengeInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.DeactivateInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.LoggedDataInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.InitInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.LoginInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.LogoutInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.SetFavoriteInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.UnsetFavoriteInteractor;

/**
 * Created by Ricardo on 22/5/16.
 */
public abstract class BaseDialogFragment extends DialogFragment {

	private static final String POPUP_FRAGMENT_TAG = "POPUP_FRAGMENT_TAG";

	@Inject protected Context context;

	@Inject protected InitInteractor initInteractor;
	@Inject protected LoginInteractor loginInteractor;
	@Inject protected LogoutInteractor logoutInteractor;
	@Inject protected LoggedDataInteractor loggedDataInteractor;
	@Inject protected ChallengeInteractor challengeInteractor;
	@Inject protected ActivateInteractor activateInteractor;
	@Inject protected DeactivateInteractor deactivateInteractor;
	@Inject protected SetFavoriteInteractor setFavoriteInteractor;
	@Inject protected UnsetFavoriteInteractor unsetFavoriteInteractor;

	protected View mainView;

	@Override
	public void onAttach(Context context) {
		this.context = context;
		if (context instanceof BaseActivity) {
			((BaseActivity) this.context).component().inject(this);
		} else {
			throw new RuntimeException("context not instanceof BaseActivity");
		}
		super.onAttach(context);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_Transparent);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mainView = inflater.inflate(getContentView(), container, false);

		ButterKnife.bind(this, mainView);

		prepareInterface(mainView);

		return mainView;
	}

	@Override
	public void onPause() {
		super.onPause();
		try {
			hideKeyBoard(getView().getWindowToken());
		} catch (Exception e) {
			//
		}
	}

	/**
	 * Handle the back button press event
	 *
	 * @return - true if the event is consumed by this method - false otherwise
	 */
	public boolean backPressed() {
		return false;
	}

	protected void showKeyBoard(final EditText myEditText) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				try {
					myEditText.requestFocus();
					InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInput(myEditText, 0);
				} catch (Exception e) {
					MyLog.printStackTrace(e);
				}
			}
		}, 100);
	}

	protected void hideKeyBoard(IBinder windowToken) {
		try {
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(windowToken, 0);
		} catch (Exception e) {
			MyLog.printStackTrace(e);
		}
	}

	protected void hideKeyBoard(EditText myEditText) {
		try {
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
		} catch (Exception e) {
			MyLog.printStackTrace(e);
		}
	}

	protected abstract int getContentView();

	protected abstract void prepareInterface(View mainView);

	@Override
	@NonNull
	public final Dialog onCreateDialog(Bundle savedInstanceState) {
		return new Dialog(getActivity(), getTheme()) {
			@Override
			public void onBackPressed() {
				backPressed();
			}
		};
	}
}
