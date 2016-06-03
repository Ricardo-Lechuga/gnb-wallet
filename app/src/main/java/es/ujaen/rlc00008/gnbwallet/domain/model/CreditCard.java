package es.ujaen.rlc00008.gnbwallet.domain.model;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;

/**
 * Created by Ricardo on 3/6/16.
 */
public class CreditCard extends Card {

	public CreditCard(CardDTO cardDTO) {
		super(cardDTO);
	}

	public Amount getCurrentBalance() {
		return new Amount(cardDTO.getCurrentBalance());
	}

	public Amount getAvailableCredit() {
		return new Amount(cardDTO.getAvailableCredit());
	}

	public Amount getCreditLimit() {
		return new Amount(cardDTO.getCreditLimit());
	}
}
