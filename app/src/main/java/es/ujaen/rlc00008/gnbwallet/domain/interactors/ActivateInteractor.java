package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

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
public class ActivateInteractor extends BaseInteractor {

	public interface ActivateCallback extends BaseInteractorCallback {

		void activationOk(Card card);
	}

	@Inject
	public ActivateInteractor() {
	}

	public void activateCard(final Card card, final ActivateCallback callback) {
		new Thread() {
			@Override
			public void run() {
				repository.activateCard(card.getCardDTO(), new RepositoryCallback<CardDTO>() {
					@Override
					public void resultOk(final CardDTO cardDTO) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								callback.activationOk(CardFactory.get(cardDTO, card.isFavorite()));
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
