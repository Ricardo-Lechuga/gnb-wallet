package es.ujaen.rlc00008.gnbwallet.data;

import android.support.annotation.Nullable;

import java.util.List;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.ChallengeResponse;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.GlobalPositionResponse;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.LoginResponse;

/**
 * Created by Ricardo on 15/5/16.
 */
public interface GNBRepository {

	@Nullable
	UserDTO getPersistedUser();

	@Nullable
	List<CardDTO> getPersistedCards();

	void authenticateUser(String userDoc, String password, RepositoryCallback<LoginResponse> callback);

	void loadGlobalPosition(RepositoryCallback<GlobalPositionResponse> callback);

	void logout();

	@Nullable
	UserDTO getCurrentUser();

	@Nullable
	List<CardDTO> getCards();

	@Nullable
	String getFavoriteCardPan();

	void generateChallenge(RepositoryCallback<ChallengeResponse> callback);

	void activateCard(CardDTO cardDTO, RepositoryCallback<CardDTO> callback);

	void deactivateCard(CardDTO cardDTO, RepositoryCallback<CardDTO> callback);

	void setFavorite(CardDTO cardDTO, RepositoryCallback<CardDTO> callback);

	void unsetFavorite(CardDTO cardDTO, RepositoryCallback<CardDTO> callback);
}
