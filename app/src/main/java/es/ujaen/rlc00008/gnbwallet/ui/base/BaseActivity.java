package es.ujaen.rlc00008.gnbwallet.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import es.ujaen.rlc00008.gnbwallet.GNBApplication;
import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.di.components.BaseActivityComponent;
import es.ujaen.rlc00008.gnbwallet.di.components.DaggerBaseActivityComponent;
import es.ujaen.rlc00008.gnbwallet.di.modules.ActivityModule;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.ActivateInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.ChallengeInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.DeactivateInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.InitInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.LoggedDataInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.LoginInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.LogoutInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.SetFavoriteInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.UnsetFavoriteInteractor;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.dialogs.GenericDialogFragment;

/**
 * Created by Ricardo on 22/5/16.
 */
public abstract class BaseActivity extends AppCompatActivity implements
		GenericDialogFragment.GenericDialogListener {

	private static final String POPUP_FRAGMENT_TAG = "POPUP_FRAGMENT_TAG";

	BaseActivityComponent component;

	protected ProgressDialog progressDialog;

	@Inject protected InitInteractor initInteractor;
	@Inject protected LoginInteractor loginInteractor;
	@Inject protected LogoutInteractor logoutInteractor;
	@Inject protected LoggedDataInteractor loggedDataInteractor;
	@Inject protected ChallengeInteractor challengeInteractor;
	@Inject protected ActivateInteractor activateInteractor;
	@Inject protected DeactivateInteractor deactivateInteractor;
	@Inject protected SetFavoriteInteractor setFavoriteInteractor;
	@Inject protected UnsetFavoriteInteractor unsetFavoriteInteractor;

	public BaseActivityComponent component() {
		if (component == null) {
			component = DaggerBaseActivityComponent.builder()
					.applicationComponent(getGNBApplication().component())
					.activityModule(new ActivityModule(this))
					.build();
		}
		return component;
	}

	@Override
	public final void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);

		setOnCreate(savedInstanceState);
	}

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setOnCreate(savedInstanceState);
	}

	protected void setOnCreate(Bundle savedInstanceState) {
		initState(savedInstanceState);
		if (getContentView() > 0) {
			setContentView(getContentView());
			ButterKnife.bind(this);
			component().inject(this);
			prepareInterface();
		}
	}

	protected void initState(Bundle savedInstanceState) {
	}

	protected abstract int getContentView();

	protected abstract void prepareInterface();

	protected void replaceFragment(BaseFragment fragment, View containerView) {
		getSupportFragmentManager().beginTransaction().replace(containerView.getId(), fragment).commit();
		overridePendingTransition(0, 0);
	}

	protected void replaceFragment(Fragment fragment, View containerView, String backStackName) {
		getSupportFragmentManager()
				.beginTransaction()
				.addToBackStack(backStackName)
				.replace(containerView.getId(), fragment)
				.commit();
		overridePendingTransition(0, 0);
	}

	protected BaseFragment findFragmentById(View containerView) {
		return (BaseFragment) getSupportFragmentManager().findFragmentById(containerView.getId());
	}

	protected GNBApplication getGNBApplication() {
		return (GNBApplication) getApplication();
	}

	public void showLoading() {

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getResources().getString(R.string._loading));
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	public void hideLoading() {

		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	/**
	 * Muestra un DialogFragment - fullScreen
	 */
	public void showPopUpFragment(BaseDialogFragment fragment) {
		try {
			fragment.show(getSupportFragmentManager(), POPUP_FRAGMENT_TAG);
		} catch (Exception e) {
			MyLog.printStackTrace(e);
		}
	}

	/**
	 * Shows a DialogFragment - fullScreen with OK icon
	 */
	public void showOkFragment(String message) {
		BaseDialogFragment baseDialogFragment = GenericDialogFragment.newInstance(R.drawable.icn_check, message);
		showPopUpFragment(baseDialogFragment);
	}

	/**
	 * Muestra un DialogFragment - fullScreen with Error icon
	 */
	public void showErrorFragment(String message) {
		BaseDialogFragment baseDialogFragment = GenericDialogFragment.newInstance(R.drawable.icn_cancelled, message);
		showPopUpFragment(baseDialogFragment);
	}

	/**
	 * Oculta el pop-up fragment en la vista superior de la Activity
	 */
	public void hidePopUpFragment() {
		try {
			BaseDialogFragment fragment = (BaseDialogFragment) getSupportFragmentManager().findFragmentByTag(POPUP_FRAGMENT_TAG);
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
			BaseDialogFragment fragment = (BaseDialogFragment) getSupportFragmentManager().findFragmentByTag(POPUP_FRAGMENT_TAG);
			return (fragment != null);
		} catch (Exception e) {
			MyLog.printStackTrace(e);
		}
		return false;
	}

	@Override
	public void genericDialogCancel() {
		hidePopUpFragment();
	}

	@Override
	public void genericDialogLeftClick() {
		hidePopUpFragment();
	}

	@Override
	public void genericDialogRightClick() {
		hidePopUpFragment();
	}
}
