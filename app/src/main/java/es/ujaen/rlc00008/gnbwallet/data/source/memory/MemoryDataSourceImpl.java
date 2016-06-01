package es.ujaen.rlc00008.gnbwallet.data.source.memory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;

/**
 * Created by Ricardo on 21/5/16.
 */
@Singleton
public class MemoryDataSourceImpl implements MemoryDataSource {

	private SessionData sessionData;

	@Inject
	public MemoryDataSourceImpl() {
		sessionData = new SessionData();
	}

	@Override
	public UserDTO getUserData() {
		return new UserDTO(sessionData.getCurrentUser());
	}

	@Override
	public void setUserData(UserDTO userDTO) {
		sessionData.setCurrentUser(userDTO);
	}

	@Override
	public List<CardDTO> getUserCards(String userId) {
		ArrayList<CardDTO> cards = new ArrayList<>();
		if (sessionData.getUserCards() != null) {
			for (CardDTO cardDTO : sessionData.getUserCards()) {
				cards.add(new CardDTO(cardDTO));
			}
		}
		return cards;
	}

	@Override
	public void setUserCards(List<CardDTO> userCards) {
		ArrayList<CardDTO> userNewCards = new ArrayList<>();
		userNewCards.addAll(userCards);
		sessionData.setUserCards(userNewCards);
	}

	@Override
	public void updateUserCard(CardDTO cardDTO) {
		if (sessionData.getUserCards() != null) {
			int position = sessionData.getUserCards().indexOf(cardDTO);
			if (position >= 0) {
				sessionData.getUserCards().set(position, cardDTO);
			}
		}
	}

	@Override
	public void cleanSessionData() {
		this.sessionData = new SessionData();
	}
}