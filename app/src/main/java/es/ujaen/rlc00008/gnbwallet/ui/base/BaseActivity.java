package es.ujaen.rlc00008.gnbwallet.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import es.ujaen.rlc00008.gnbwallet.GNBApplication;
import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.di.components.BaseActivityComponent;
import es.ujaen.rlc00008.gnbwallet.di.components.DaggerBaseActivityComponent;
import es.ujaen.rlc00008.gnbwallet.di.modules.ActivityModule;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.InitInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.LoginInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.LogoutInteractor;

/**
 * Created by Ricardo on 22/5/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

	private static final String POPUP_FRAGMENT_TAG = "POPUP_FRAGMENT_TAG";

	BaseActivityComponent component;

	@Inject protected InitInteractor initInteractor;
	@Inject protected LoginInteractor loginInteractor;
	@Inject protected LogoutInteractor logoutInteractor;

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

		setOnCreate();
	}

	@Override
	protected final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setOnCreate();
	}

	protected void setOnCreate() {
		if (getIntent() != null && getIntent().getExtras() != null){
			readArguments(getIntent().getExtras());
		}
		if (getContentView() > 0) {
			setContentView(getContentView());
			ButterKnife.bind(this);
			component().inject(this);
			prepareInterface();
		}
	}

	protected void readArguments(Bundle bundle){};

	protected abstract int getContentView();

	protected abstract void prepareInterface();

	protected void replaceFragment(BaseFragment fragment, View containerView) {
		getSupportFragmentManager().beginTransaction().replace(containerView.getId(), fragment).commit();
		overridePendingTransition(0, 0);
	}

	protected BaseFragment findFragmentById(View containerView) {
		return (BaseFragment) getSupportFragmentManager().findFragmentById(containerView.getId());
	}

	protected GNBApplication getGNBApplication() {
		return (GNBApplication) getApplication();
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

}
