package es.ujaen.rlc00008.gnbwallet.data.source.persistence;

import java.util.List;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;

/**
 * Created by Ricardo on 21/5/16.
 */
public interface PersistenceDataSource {

	UserDTO getStoredUser();

	void setStoredUser(UserDTO userDTO);

	List<CardDTO> getUserCards(String userId);

	void setUserCards(List<CardDTO> userCards);

	void deleteAllData();
}
