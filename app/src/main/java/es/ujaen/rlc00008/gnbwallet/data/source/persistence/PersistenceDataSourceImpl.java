package es.ujaen.rlc00008.gnbwallet.data.source.persistence;

import android.content.Context;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;

/**
 * Created by Ricardo on 27/6/16.
 */
public class PersistenceDataSourceImpl implements PersistenceDataSource {

	private static final String GNB_SP_NAME = "gnb_preferences";

	private static final String TAG_FAVORITE_CARD = "TAG_TOKEN";
	private static final String TAG_TEMP_PAYMENT_MAP = "TAG_TOKEN";

	private Context context;

	@Inject
	public PersistenceDataSourceImpl(Context context) {
		this.context = context;
	}

	@Override
	public CardDTO getFavoriteCard() {
		return null;
	}

	@Override
	public void setFavoriteCard(CardDTO favoriteCard) {

	}

	@Override
	public CardDTO getTempPaymentCard() {
		return null;
	}

	@Override
	public void setTempPaymentCard(CardDTO cardDTO) {

	}
}
