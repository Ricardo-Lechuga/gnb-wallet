package es.ujaen.rlc00008.gnbwallet;

import android.support.multidex.MultiDexApplication;

import es.ujaen.rlc00008.gnbwallet.di.components.ApplicationComponent;
import es.ujaen.rlc00008.gnbwallet.di.components.DaggerApplicationComponent;
import es.ujaen.rlc00008.gnbwallet.di.modules.ApplicationModule;

/**
 * Created by Ricardo on 15/5/16.
 */
public class GNBApplication extends MultiDexApplication {

	private static final String LOG_TAG = GNBApplication.class.getSimpleName();

	private ApplicationComponent component;

	@Override
	public void onCreate() {
		super.onCreate();

		component = DaggerApplicationComponent
				.builder()
				.applicationModule(new ApplicationModule(this))
				.build();

	}

	public ApplicationComponent component() {
		return component;
	}
}
