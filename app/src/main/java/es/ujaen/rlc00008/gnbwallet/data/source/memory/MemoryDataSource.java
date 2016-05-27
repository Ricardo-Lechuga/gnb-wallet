package es.ujaen.rlc00008.gnbwallet.data.source.memory;

import java.util.List;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;

/**
 * Created by Ricardo on 16/5/16.
 */
public interface MemoryDataSource {

	UserDTO getUserData();

	void setUserData(UserDTO userDTO);

	List<CardDTO> getUserCards(String userId);

	void setUserCards(List<CardDTO> userCards);

	void updateUserCard(CardDTO cardDTO);

	void cleanSessionData();
}
