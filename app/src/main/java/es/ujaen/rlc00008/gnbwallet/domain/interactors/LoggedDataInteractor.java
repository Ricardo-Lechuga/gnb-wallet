package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;
import es.ujaen.rlc00008.gnbwallet.domain.base.BaseInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.domain.model.User;
import es.ujaen.rlc00008.gnbwallet.domain.model.factories.CardFactory;

/**
 * Created by Ricardo on 3/6/16.
 */
public class LoggedDataInteractor extends BaseInteractor {

	@Inject
	public LoggedDataInteractor() {
	}

	public User getUser() {
		UserDTO userDTO = repository.getCurrentUser();
		if (userDTO == null) {
			throw new RuntimeException("user is null! Call after loadGlobalPosition() and before logout()");
		}
		return new User(userDTO);
	}

	public List<Card> getCards() {
		List<CardDTO> cardDTOList = repository.getCards();
		String favorite = repository.getFavoriteCardPan();
		if (cardDTOList == null) {
			throw new RuntimeException("user is null! Call after loadGlobalPosition() and before logout()");
		}
		List<Card> cards = new ArrayList<>();
		for (CardDTO cardDTO : cardDTOList) {
			Card card = CardFactory.get(cardDTO, cardDTO.getPan().equals(favorite));
			if (card != null) {
				cards.add(card);
			}
		}
		return cards;
	}
}
