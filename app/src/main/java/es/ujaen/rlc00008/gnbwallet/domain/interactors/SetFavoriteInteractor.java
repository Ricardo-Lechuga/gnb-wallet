package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.data.RepositoryCallback;
import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.source.net.Meta;
import es.ujaen.rlc00008.gnbwallet.domain.base.BaseInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.domain.model.factories.CardFactory;

/**
 * Created by Ricardo on 22/5/16.
 */
public class SetFavoriteInteractor extends BaseInteractor {

	public interface SetFavoriteCallback extends BaseInteractorCallback {

		void setFavoriteOk(Card card);

		void setFavoriteOkWithWarning(Card card, String message);
	}

	@Inject
	public SetFavoriteInteractor() {
	}

	public void setFavorite(final Card card, final SetFavoriteCallback callback) {
		new Thread() {
			@Override
			public void run() {
				if (!NFCUtils.isNfcCompatible(context)) {
					callback.operativeError(context.getString(R.string.nfc_not_compatible));
					return;
				}
				repository.setFavorite(card.getCardDTO(), new RepositoryCallback<CardDTO>() {
					@Override
					public void resultOk(final CardDTO cardDTO) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (NFCUtils.isNfcEnabled(context)) {
									callback.setFavoriteOk(CardFactory.get(cardDTO, card.isFavorite()));
								} else {
									callback.setFavoriteOkWithWarning(CardFactory.get(cardDTO, card.isFavorite()), context.getString(R.string.nfc_favorite_but_not_enabled));
								}
							}
						});
					}

					@Override
					public void resultError(final Meta meta) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (callback != null) {
									callback.operativeError(meta.getErrorMessage());
								}
							}
						});
					}

					@Override
					public void genericException(Throwable t) {
						MyLog.printStackTrace(t);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (callback != null) {
									callback.operativeError(context.getString(R.string._generic_error_message));
								}
							}
						});
					}
				});
			}
		}.start();
	}
}
