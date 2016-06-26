package es.ujaen.rlc00008.gnbwallet.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.ujaen.rlc00008.gnbwallet.GNBApplication;

/**
 * Created by Ricardo Lechuga on 18/1/16.
 */
@Module
public class ApplicationModule {

	private final GNBApplication application;

	public ApplicationModule(GNBApplication application) {
		this.application = application;
	}

	@Provides
	@Singleton
	Context provideApplicationContext() {
		return this.application.getApplicationContext();
	}

	@Provides
	@Singleton
	GNBApplication provideGNBApplication() {
		return this.application;
	}

}