package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.data.RepositoryCallback;
import es.ujaen.rlc00008.gnbwallet.data.source.net.Meta;
import es.ujaen.rlc00008.gnbwallet.domain.base.BaseInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;

/**
 * Created by Ricardo on 22/5/16.
 */
public class GetCCVInteractor extends BaseInteractor {

	public interface GetCCVCallback extends BaseInteractorCallback {

		void ccvResponse(String ccv);
	}

	@Inject
	public GetCCVInteractor() {
	}

	public void seeCCV(final Card card, final String operationSignature, final GetCCVCallback callback) {
		new Thread() {
			@Override
			public void run() {
				repository.getCCV(card.getCardDTO(), operationSignature, new RepositoryCallback<String>() {
					@Override
					public void resultOk(final String response) {
						if (callback != null) {
							callback.ccvResponse(response);
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
						MyLog.printStackTrace(t);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (callback != null) {
									callback.ccvResponse("123");
									//callback.operativeError(context.getString(R.string._generic_error_message));
								}
							}
						});
					}
				});
			}
		}.start();
	}
}
