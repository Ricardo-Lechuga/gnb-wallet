package es.ujaen.rlc00008.gnbwallet.di.modules;

import android.app.Service;

import dagger.Module;
import dagger.Provides;
import es.ujaen.rlc00008.gnbwallet.di.scopes.ActivityScope;

/**
 * Created by Ricardo Lechuga on 18/1/16.
 */
@Module
public class ServiceModule {

	private final Service service;

	public ServiceModule(Service service) {
		this.service = service;
	}

	@Provides
	@ActivityScope
	Service getService() {
		return this.service;
	}

	//@Provides
	//@ActivityScope
	//Context getContext() {
	//	return this.service;
	//}
}