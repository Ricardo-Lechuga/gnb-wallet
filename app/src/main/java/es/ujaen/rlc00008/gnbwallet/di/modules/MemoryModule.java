package es.ujaen.rlc00008.gnbwallet.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.FallbackMemory;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MainMemory;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MemoryDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MemoryDataSourceImpl;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.fallback.MemoryFallbackDataSource;

/**
 * Created by Ricardo on 22/5/16.
 */
@Module
public class MemoryModule {

	@Provides
	@Singleton
	@MainMemory
	MemoryDataSource provideMainMemoryDataSource() {
		return new MemoryDataSourceImpl();
	}

	@Provides
	@Singleton
	@FallbackMemory
	MemoryDataSource provideFallbackMemoryDataSource(Context context) {
		return new MemoryFallbackDataSource(context);
	}

}
