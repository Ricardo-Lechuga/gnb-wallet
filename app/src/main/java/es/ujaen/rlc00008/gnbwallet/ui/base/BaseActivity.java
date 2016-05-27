package es.ujaen.rlc00008.gnbwallet.ui.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import es.ujaen.rlc00008.gnbwallet.GNBApplication;
import es.ujaen.rlc00008.gnbwallet.di.components.BaseActivityComponent;
import es.ujaen.rlc00008.gnbwallet.di.components.DaggerBaseActivityComponent;
import es.ujaen.rlc00008.gnbwallet.di.modules.ActivityModule;

/**
 * Created by Ricardo on 22/5/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

	BaseActivityComponent component;

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
	public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);

		setOnCreate();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setOnCreate();
	}

	private void setOnCreate() {
		if (getContentView() > 0) {
			setContentView(getContentView());
			ButterKnife.bind(this);
			component().inject(this);
			prepareInterface();
		}
	}

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
}
