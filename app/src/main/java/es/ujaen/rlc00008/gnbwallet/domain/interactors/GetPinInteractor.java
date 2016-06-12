package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.data.RepositoryCallback;
import es.ujaen.rlc00008.gnbwallet.data.source.net.Meta;
import es.ujaen.rlc00008.gnbwallet.domain.base.BaseInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;

/**
 * Created by Ricardo on 22/5/16.
 */
public class GetPinInteractor extends BaseInteractor {

	public interface GetPinCallback extends BaseInteractorCallback {

		void pinResponse(String pin);
	}

	@Inject
	public GetPinInteractor() {
	}

	public void seePin(final Card card, final String operationSignature, final GetPinCallback callback) {
		new Thread() {
			@Override
			public void run() {
				repository.getPIN(card.getCardDTO(), operationSignature, new RepositoryCallback<String>() {
					@Override
					public void resultOk(final String response) {
						if (callback != null) {
							callback.pinResponse(response);
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
}
