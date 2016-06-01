package es.ujaen.rlc00008.gnbwallet.data;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.FallbackMemory;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MainMemory;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MemoryDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.fallback.MemoryFallbackDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.net.GNBServices;
import es.ujaen.rlc00008.gnbwallet.data.source.net.Meta;
import es.ujaen.rlc00008.gnbwallet.data.source.net.ResponseWrapper;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.LoginResponse;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.db.PersistenceDataSourceImpl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
	public UserDTO getPersistedUser() {
		return persistenceDataSource.getStoredUser();
	}

	@Override
	public List<CardDTO> getPersistedCards() {
		UserDTO userDTO = persistenceDataSource.getStoredUser();
		if (userDTO != null) {
			return persistenceDataSource.getUserCards(userDTO.getUserId());
		}
		return null;
	}

	@Override
	public UserDTO getCurrentUser() {
		return null;
	}

	@Override
	public void userLogin(String userDoc, String password, final RepositoryCallback<LoginResponse> callback) {
		Call<ResponseWrapper<LoginResponse>> call = gnbServices.userLogin(userDoc);
		call.enqueue(new Callback<ResponseWrapper<LoginResponse>>() {
			@Override
			public void onResponse(Call<ResponseWrapper<LoginResponse>> call, Response<ResponseWrapper<LoginResponse>> response) {
				if(response.isSuccessful()) {
					if (Meta.CODE_OK == response.body().getMeta().getCode()) {
						// TODO Save user...
						callback.resultOk(response.body().getResponse());
					} else {
						callback.resultError(response.body().getMeta());
					}
				} else {
					callback.genericException(new RuntimeException(response.errorBody().toString()));
				}
			}

			@Override
			public void onFailure(Call<ResponseWrapper<LoginResponse>> call, Throwable t) {
				callback.genericException(t);
			}
		});
	}
}
