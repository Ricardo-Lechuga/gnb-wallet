package es.ujaen.rlc00008.gnbwallet.data;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.ujaen.rlc00008.gnbwallet.data.source.memory.FallbackMemory;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MainMemory;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MemoryDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.fallback.MemoryFallbackDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.net.GNBServices;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.db.PersistenceDataSourceImpl;

/**
 * Created by Ricardo on 15/5/16.
 */
@Singleton
public class GNBRepositoryImpl implements GNBRepository {

	private Context context;

	private MemoryDataSource memoryDataSource;
	private MemoryDataSource memoryFallbackDataSource;
	private PersistenceDataSourceImpl persistenceDataSource;
	private GNBServices gnbServices;

	@Inject
	public GNBRepositoryImpl(Context context, @MainMemory MemoryDataSource memoryDataSource, @FallbackMemory MemoryFallbackDataSource memoryFallbackDataSource,
	                         PersistenceDataSourceImpl persistenceDataSource, GNBServices gnbServices) {
		this.context = context;
		this.memoryDataSource = memoryDataSource;
		this.memoryFallbackDataSource = memoryFallbackDataSource;
		this.persistenceDataSource = persistenceDataSource;
		this.gnbServices = gnbServices;
	}

	@Override
	public void userLogin(String userDoc, String password) {
		//persistenceDataSource.setUserCards
	}
}
