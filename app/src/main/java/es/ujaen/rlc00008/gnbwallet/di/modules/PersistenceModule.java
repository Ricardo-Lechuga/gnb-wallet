package es.ujaen.rlc00008.gnbwallet.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.PersistenceDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.PersistenceDataSourceImpl;

/**
 * Created by Ricardo on 22/5/16.
 */
@Module
public class PersistenceModule {

	@Provides
	@Singleton
	PersistenceDataSource providePersistenceDataSource(Context context) {
		return new PersistenceDataSourceImpl(context);
	}

}
