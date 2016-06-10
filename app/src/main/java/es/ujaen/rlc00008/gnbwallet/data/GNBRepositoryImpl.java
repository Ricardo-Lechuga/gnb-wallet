package es.ujaen.rlc00008.gnbwallet.data;

import android.content.Context;
import android.support.annotation.Nullable;

import java.io.EOFException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.ChallengeDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.FallbackMemory;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MainMemory;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MemoryDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.fallback.MemoryFallbackDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.net.GNBServices;
import es.ujaen.rlc00008.gnbwallet.data.source.net.Meta;
import es.ujaen.rlc00008.gnbwallet.data.source.net.ResponseWrapper;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.ChallengeResponse;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.GlobalPositionResponse;
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

	@Nullable
	@Override
	public UserDTO getPersistedUser() {
		return persistenceDataSource.getStoredUser();
	}

	@Nullable
	@Override
	public List<CardDTO> getPersistedCards() {
		UserDTO userDTO = persistenceDataSource.getStoredUser();
		if (userDTO != null) {
			return persistenceDataSource.getUserCards(userDTO.getUserId());
		}
		return null;
	}

	@Override
	public void authenticateUser(final String userDoc, String password, final RepositoryCallback<LoginResponse> callback) {
		Call<ResponseWrapper<LoginResponse>> call = gnbServices.userLogin(userDoc);
		call.enqueue(new Callback<ResponseWrapper<LoginResponse>>() {
			@Override
			public void onResponse(Call<ResponseWrapper<LoginResponse>> call, Response<ResponseWrapper<LoginResponse>> response) {
				if (response.isSuccessful()) {
					if (Meta.CODE_OK == response.body().getMeta().getCode()) {
						LoginResponse loginResponse = response.body().getResponse();
						memoryDataSource.setUserToken(loginResponse.getToken());
						memoryFallbackDataSource.setUserToken(loginResponse.getToken());
						memoryDataSource.setUserLogin(userDoc);
						memoryFallbackDataSource.setUserLogin(userDoc);
						callback.resultOk(loginResponse);
					} else {
						callback.resultError(response.body().getMeta());
					}
				} else {
					callback.genericException(new RuntimeException(response.errorBody().toString()));
				}
			}

			@Override
			public void onFailure(Call<ResponseWrapper<LoginResponse>> call, Throwable t) {
				if (t instanceof EOFException) {
					Meta meta = new Meta();
					meta.setErrorMessage(context.getString(R.string._invalid_login));
					callback.resultError(meta);
				} else {
					callback.genericException(t);
				}
			}
		});
	}

	@Override
	public void loadGlobalPosition(final RepositoryCallback<GlobalPositionResponse> callback) {

		Call<ResponseWrapper<GlobalPositionResponse>> call = gnbServices.getGlobalPosition(getUserLogin());
		call.enqueue(new Callback<ResponseWrapper<GlobalPositionResponse>>() {
			@Override
			public void onResponse(Call<ResponseWrapper<GlobalPositionResponse>> call, Response<ResponseWrapper<GlobalPositionResponse>> response) {
				if (response.isSuccessful()) {
					if (Meta.CODE_OK == response.body().getMeta().getCode()) {
						GlobalPositionResponse globalPositionResponse = response.body().getResponse();
						memoryDataSource.setUserData(globalPositionResponse.getUserData());
						memoryFallbackDataSource.setUserData(globalPositionResponse.getUserData());
						memoryDataSource.setUserCards(globalPositionResponse.getCards());
						memoryFallbackDataSource.setUserCards(globalPositionResponse.getCards());
						memoryDataSource.setFavoriteCard(globalPositionResponse.getFavoriteCard());
						memoryFallbackDataSource.setFavoriteCard(globalPositionResponse.getFavoriteCard());
						callback.resultOk(globalPositionResponse);
					} else {
						callback.resultError(response.body().getMeta());
					}
				} else {
					callback.genericException(new RuntimeException(response.errorBody().toString()));
				}
			}

			@Override
			public void onFailure(Call<ResponseWrapper<GlobalPositionResponse>> call, Throwable t) {
				callback.genericException(t);
			}
		});
	}

	@Override
	public void logout() {
		memoryDataSource.cleanSessionData();
		memoryFallbackDataSource.cleanSessionData();
	}

	@Nullable
	@Override
	public UserDTO getCurrentUser() {
		UserDTO currentUser = memoryDataSource.getUserData();
		if (currentUser == null) {
			currentUser = memoryFallbackDataSource.getUserData();
		}
		return currentUser;
	}

	@Nullable
	@Override
	public List<CardDTO> getCards() {

		UserDTO currentUser = getCurrentUser();
		List<CardDTO> cards = null;
		if (currentUser != null) {
			cards = memoryDataSource.getUserCards(currentUser.getUserId());
			if (cards == null) {
				cards = memoryFallbackDataSource.getUserCards(currentUser.getUserId());
			}
		}
		return cards;
	}

	@Nullable
	@Override
	public String getFavoriteCardPan() {

		String favorite = memoryDataSource.getFavoriteCard();
		if (favorite == null) {
			favorite = memoryFallbackDataSource.getFavoriteCard();
		}

		return favorite;
	}

	@Override
	public void generateChallenge(final RepositoryCallback<ChallengeResponse> callback) {

		Call<ResponseWrapper<ChallengeResponse>> call = gnbServices.generateChallenge(getUserLogin());
		call.enqueue(new Callback<ResponseWrapper<ChallengeResponse>>() {
			@Override
			public void onResponse(Call<ResponseWrapper<ChallengeResponse>> call, Response<ResponseWrapper<ChallengeResponse>> response) {
				if (response.isSuccessful()) {
					if (Meta.CODE_OK == response.body().getMeta().getCode()) {
						ChallengeResponse challengeResponse = response.body().getResponse();
						ChallengeDTO challengeDTO = new ChallengeDTO(challengeResponse.getIdChallenge(), challengeResponse.getQuestion());
						memoryDataSource.setChallengeDTO(challengeDTO);
						memoryFallbackDataSource.setChallengeDTO(challengeDTO);
						callback.resultOk(response.body().getResponse());
					} else {
						callback.resultError(response.body().getMeta());
					}
				} else {
					callback.genericException(new RuntimeException(response.errorBody().toString()));
				}
			}

			@Override
			public void onFailure(Call<ResponseWrapper<ChallengeResponse>> call, Throwable t) {
				callback.genericException(t);
			}
		});
	}

	@Override
	public void activateCard(final CardDTO cardDTO, final RepositoryCallback<CardDTO> callback) {
		Call<ResponseWrapper<Void>> call = gnbServices.enableCard(cardDTO.getPan());
		call.enqueue(new Callback<ResponseWrapper<Void>>() {
			@Override
			public void onResponse(Call<ResponseWrapper<Void>> call, Response<ResponseWrapper<Void>> response) {
				if (response.isSuccessful()) {
					if (Meta.CODE_OK == response.body().getMeta().getCode()) {
						cardDTO.setEnabled(true);
						memoryDataSource.updateUserCard(cardDTO);
						memoryFallbackDataSource.updateUserCard(cardDTO);
						callback.resultOk(cardDTO);
					} else {
						callback.resultError(response.body().getMeta());
					}
				} else {
					callback.genericException(new RuntimeException(response.errorBody().toString()));
				}
			}

			@Override
			public void onFailure(Call<ResponseWrapper<Void>> call, Throwable t) {
				callback.genericException(t);
			}
		});
	}

	@Override
	public void deactivateCard(final CardDTO cardDTO, final RepositoryCallback<CardDTO> callback) {
		Call<ResponseWrapper<Void>> call = gnbServices.disableCard(cardDTO.getPan());
		call.enqueue(new Callback<ResponseWrapper<Void>>() {
			@Override
			public void onResponse(Call<ResponseWrapper<Void>> call, Response<ResponseWrapper<Void>> response) {
				if (response.isSuccessful()) {
					if (Meta.CODE_OK == response.body().getMeta().getCode()) {
						cardDTO.setEnabled(false);
						memoryDataSource.updateUserCard(cardDTO);
						memoryFallbackDataSource.updateUserCard(cardDTO);
						callback.resultOk(cardDTO);
					} else {
						callback.resultError(response.body().getMeta());
					}
				} else {
					callback.genericException(new RuntimeException(response.errorBody().toString()));
				}
			}

			@Override
			public void onFailure(Call<ResponseWrapper<Void>> call, Throwable t) {
				callback.genericException(t);
			}
		});
	}

	private String getUserLogin() {
		String userLogin = memoryDataSource.getUserLogin();
		if (userLogin == null) {
			userLogin = memoryFallbackDataSource.getUserLogin();
		}
		if (userLogin == null) {
			throw new RuntimeException("user must be logged!");
		}
		return userLogin;
	}
}
