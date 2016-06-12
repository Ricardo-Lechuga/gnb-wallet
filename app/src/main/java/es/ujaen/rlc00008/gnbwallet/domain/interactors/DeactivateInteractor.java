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
public class DeactivateInteractor extends BaseInteractor {

	public interface DeactivateCallback extends BaseInteractorCallback {

		void deactivationOk(Card card);
	}

	@Inject
	public DeactivateInteractor() {
	}

	public void deactivateCard(final Card card, final String operationSignature, final DeactivateCallback callback) {
		new Thread() {
			@Override
			public void run() {
				repository.deactivateCard(card.getCardDTO(), operationSignature, new RepositoryCallback<CardDTO>() {
					@Override
					public void resultOk(final CardDTO cardDTO) {
						if (card.isFavorite()) {
							unsetFavoriteAfterDeactivateCard(card, callback);
						} else {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									callback.deactivationOk(CardFactory.get(cardDTO, card.isFavorite()));
								}
							});
						}
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

	private void unsetFavoriteAfterDeactivateCard(final Card card, final DeactivateCallback callback) {
		repository.unsetFavorite(card.getCardDTO(), new RepositoryCallback<CardDTO>() {
			@Override
			public void resultOk(final CardDTO cardDTO) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						callback.deactivationOk(CardFactory.get(cardDTO, card.isFavorite()));
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
}
