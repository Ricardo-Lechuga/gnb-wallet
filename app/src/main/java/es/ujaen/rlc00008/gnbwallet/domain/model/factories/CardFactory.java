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

	public static Card get(CardDTO cardDTO, boolean isFavorite) {

		if (cardDTO == null) {
			return null;
		}

		Card card = null;

		if (CardDTO.TYPE_CREDIT.equals(cardDTO.getType())) {
			card = new CreditCard(cardDTO, isFavorite);
		} else if (CardDTO.TYPE_DEBIT.equals(cardDTO.getType())) {
			card = new DebitCard(cardDTO, isFavorite);
		} else if (CardDTO.TYPE_PREPAID.equals(cardDTO.getType())) {
			card = new PrepaidCard(cardDTO, isFavorite);
		}

		return card;
	}

}
