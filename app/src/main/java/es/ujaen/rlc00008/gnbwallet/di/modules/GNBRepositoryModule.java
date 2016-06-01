package es.ujaen.rlc00008.gnbwallet.di.modules;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.ujaen.rlc00008.gnbwallet.SysConstants;
import es.ujaen.rlc00008.gnbwallet.data.GNBRepository;
import es.ujaen.rlc00008.gnbwallet.data.GNBRepositoryImpl;
import es.ujaen.rlc00008.gnbwallet.data.gson.AnnotationExclusionStrategy;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MemoryDataSourceImpl;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.fallback.MemoryFallbackDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.net.GNBServices;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.db.PersistenceDataSourceImpl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mobilers on 18/1/16.
 */
@Module
public class GNBRepositoryModule {

	private static GNBServices service;

	@Provides
	@Singleton
	GNBServices provideGNBServices() {
		if (service == null) {

			HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
			logging.setLevel(HttpLoggingInterceptor.Level.BODY);

			OkHttpClient httpClient = new OkHttpClient.Builder()
					.addNetworkInterceptor(logging)
					.build();

			Gson gson = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).
					create();

			Retrofit retrofit = new Retrofit.Builder()
					.baseUrl(SysConstants.HOST_BASE_URL + "/")
					.addConverterFactory(GsonConverterFactory.create(gson))
					.client(httpClient)
					.build();

			service = retrofit.create(GNBServices.class);
		}
		return service;
	}

	@Provides
	@Singleton
	GNBRepository provideGNBRepository(Context context, MemoryDataSourceImpl memoryDataSource, MemoryFallbackDataSource memoryFallbackDataSource,
	                                   PersistenceDataSourceImpl persistenceDataSource, GNBServices gnbServices) {
		return new GNBRepositoryImpl(context, memoryDataSource, memoryFallbackDataSource, persistenceDataSource, gnbServices);
	}
}
