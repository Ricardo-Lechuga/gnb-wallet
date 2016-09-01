package es.ujaen.rlc00008.gnbwallet.data.source.persistence;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;

/**
 * Created by Ricardo on 21/5/16.
 */
public interface PersistenceDataSource {

	CardDTO getFavoriteCard();

	void setFavoriteCard(CardDTO favoriteCard);

	CardDTO getTempPaymentCard();

	void setTempPaymentCard(CardDTO cardDTO);
}
