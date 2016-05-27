package es.ujaen.rlc00008.gnbwallet.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.ujaen.rlc00008.gnbwallet.data.GNBRepository;
import es.ujaen.rlc00008.gnbwallet.data.GNBRepositoryImpl;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MemoryDataSourceImpl;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.fallback.MemoryFallbackDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.net.GNBServices;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.PersistenceDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.db.PersistenceDataSourceImpl;

/**
 * Created by Mobilers on 18/1/16.
 */
@Module
public class GNBRepositoryModule {

	//private static KidsServices service;
	//
	//@Provides
	//@Singleton
	//KidsServices provideRestServices() {
	//	if (service == null) {
	//
	//		OkHttpClient httpClient = SysConstants.getOkHttpClient();
	//
	//		Gson gson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).
	//				create();
	//
	//		Retrofit retrofit = new Retrofit.Builder()
	//				.baseUrl(SysConstants.KID_ENVIRONMENT_DEFAULT.getUrlBase() + "/")
	//				.addConverterFactory(GsonConverterFactory.create(gson))
	//				.client(httpClient)
	//				.build();
	//
	//		service = retrofit.create(KidsServices.class);
	//	}
	//	return service;
	//}
	//

	@Provides
	@Singleton
	GNBRepository provideGNBRepository(Context context, MemoryDataSourceImpl memoryDataSource, MemoryFallbackDataSource memoryFallbackDataSource,
	                                   PersistenceDataSourceImpl persistenceDataSource, GNBServices gnbServices) {
		return new GNBRepositoryImpl(context, memoryDataSource, memoryFallbackDataSource, persistenceDataSource, gnbServices);
	}

	@Provides
	@Singleton
	PersistenceDataSource providePersistenceDataSource() {
		return null;
	}
}
