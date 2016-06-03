package es.ujaen.rlc00008.gnbwallet.domain.model;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;

/**
 * Created by Ricardo on 3/6/16.
 */
public class PrepaidCard extends Card {

	public PrepaidCard(CardDTO cardDTO) {
		super(cardDTO);
	}

	public Amount getBalance() {
		return new Amount(cardDTO.getBalance());
	}
}
