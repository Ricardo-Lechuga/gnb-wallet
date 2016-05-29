package es.ujaen.rlc00008.gnbwallet.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.ButterKnife;
import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.InitInteractor;

/**
 * Created by Ricardo on 22/5/16.
 */
public abstract class BaseDialogFragment extends DialogFragment {

	private static final String POPUP_FRAGMENT_TAG = "POPUP_FRAGMENT_TAG";

	@Inject protected Context context;

	@Inject protected InitInteractor initInteractor;

	//protected MyProgressDialog myProgressDialog;

	protected View mainView;

	@Override
	public void onAttach(Context context) {

		if (context instanceof BaseActivity) {
			((BaseActivity) this.context).component().inject(this);
		} else {
			throw new RuntimeException("context not instanceof BaseActivity");
		}
		super.onAttach(context);
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

	protected BaseDialogFragment findFragmentById(int containerId) {
		try {
			return (BaseDialogFragment) getChildFragmentManager().findFragmentById(containerId);
		} catch (Exception e) {
			MyLog.printStackTrace(e);
		}
		return null;
	}

	protected void replaceFragment(BaseDialogFragment fragment, int containerId) {
		getChildFragmentManager().
				beginTransaction().
				replace(containerId, fragment)
				.commit();
		getActivity().overridePendingTransition(0, 0);
	}

	protected void replaceFragment(BaseDialogFragment fragment, int containerId, String backStackName) {
		getChildFragmentManager()
				.beginTransaction()
				.addToBackStack(backStackName)
				.replace(containerId, fragment)
				.commit();
		getActivity().overridePendingTransition(0, 0);
	}

	protected void replaceFragment(BaseDialogFragment fragment, int containerId, int enterAnim, int exitAnim) {
		getChildFragmentManager()
				.beginTransaction()
				.replace(containerId, fragment)
				.setCustomAnimations(enterAnim, exitAnim)
				.commit();
	}

	protected void removeFragment(int containerId) {
		try {
			BaseDialogFragment fragment = findFragmentById(containerId);
			if (fragment != null) {
				getChildFragmentManager().beginTransaction()
						.remove(getChildFragmentManager().findFragmentById(containerId)).commit();
			}
		} catch (Exception e) {
			MyLog.printStackTrace(e);
		}
	}

	//public void showLoading() {
	//
	//	myProgressDialog = MyProgressDialog.ctor(getActivity());
	//	myProgressDialog.show();
	//}
	//
	//public void hideLoading() {
	//
	//	if (myProgressDialog != null && myProgressDialog.isShowing()) {
	//		myProgressDialog.dismiss();
	//	}
	//}
	//
	//public void showError(String message) {
	//	MyAlertDialog.showGenericError(getActivity(), message);
	//}

	/**
	 * Muestra un DialogFragment - fullScreen
	 */
	public void showPopUpFragment(BaseDialogFragment fragment) {
		try {
			fragment.show(getChildFragmentManager(), POPUP_FRAGMENT_TAG);
		} catch (Exception e) {
			MyLog.printStackTrace(e);
		}
	}

	/**
	 * Oculta el pop-up fragment en la vista superior de la Activity
	 */
	public void hidePopUpFragment() {
		try {
			BaseDialogFragment fragment = (BaseDialogFragment) getChildFragmentManager().findFragmentByTag(POPUP_FRAGMENT_TAG);
			if (fragment != null) {
				fragment.dismiss();
			}
		} catch (Exception e) {
			MyLog.printStackTrace(e);
		}
	}

	/**
	 * Devuelve true si se está visualizando el pop-up fragment en la vista superior de la Activity
	 */
	public boolean isPopUpFragmentShowing() {
		try {
			BaseDialogFragment fragment = (BaseDialogFragment) getChildFragmentManager().findFragmentByTag(POPUP_FRAGMENT_TAG);
			return (fragment != null);
		} catch (Exception e) {
			MyLog.printStackTrace(e);
		}
		return false;
	}

	protected abstract int getContentView();

	protected abstract void prepareInterface(View mainView);

	@Override
	public final Dialog onCreateDialog(Bundle savedInstanceState) {
		return new Dialog(getActivity(), getTheme()) {
			@Override
			public void onBackPressed() {
				backPressed();
			}
		};
	}

}
