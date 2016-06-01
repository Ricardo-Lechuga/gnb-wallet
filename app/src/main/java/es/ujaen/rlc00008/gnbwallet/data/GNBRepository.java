package es.ujaen.rlc00008.gnbwallet.data;

import java.util.List;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.LoginResponse;

/**
 * Created by Ricardo on 15/5/16.
 */
public interface GNBRepository {

	UserDTO getPersistedUser();

	List<CardDTO> getPersistedCards();

	UserDTO getCurrentUser();

	void userLogin(String userDoc, String password, RepositoryCallback<LoginResponse> callback);
}
