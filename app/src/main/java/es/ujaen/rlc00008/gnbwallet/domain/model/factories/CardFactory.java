package es.ujaen.rlc00008.gnbwallet.domain.model.factories;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.domain.model.CreditCard;
import es.ujaen.rlc00008.gnbwallet.domain.model.DebitCard;
import es.ujaen.rlc00008.gnbwallet.domain.model.PrepaidCard;

/**
 * Created by Ricardo on 4/6/16.
 */
public abstract class CardFactory {

	public static final Card get(CardDTO cardDTO) {

		if (cardDTO == null) {
			return null;
		}

		Card card = null;

		if (CardDTO.TYPE_CREDIT.equals(cardDTO.getType())) {
			card = new CreditCard(cardDTO);
		} else if (CardDTO.TYPE_DEBIT.equals(cardDTO.getType())) {
			card = new DebitCard(cardDTO);
		} else if (CardDTO.TYPE_PREPAID.equals(cardDTO.getType())) {
			card = new PrepaidCard(cardDTO);
		}

		return card;
	}

}
