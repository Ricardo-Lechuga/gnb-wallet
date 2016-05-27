package es.ujaen.rlc00008.gnbwallet.di.modules;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.ujaen.rlc00008.gnbwallet.di.scopes.ActivityScope;

/**
 * Created by Mobilers on 18/1/16.
 */
@Module
public class ActivityModule {

	private final Activity activity;

	public ActivityModule(Activity activity) {
		this.activity = activity;
	}

	@Provides
	@ActivityScope
	Activity getActivity() {
		return this.activity;
	}

	@Provides
	@ActivityScope
	Context getActivityContext() {
		return this.activity;
	}
}