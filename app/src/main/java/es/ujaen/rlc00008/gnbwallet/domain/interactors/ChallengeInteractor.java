package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.data.RepositoryCallback;
import es.ujaen.rlc00008.gnbwallet.data.source.net.Meta;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.ChallengeResponse;
import es.ujaen.rlc00008.gnbwallet.domain.base.BaseInteractor;

/**
 * Created by Ricardo on 22/5/16.
 */
public class ChallengeInteractor extends BaseInteractor {

	public interface ChallengeCallback extends BaseInteractorCallback {

		void challengePresented(String question);
	}

	@Inject
	public ChallengeInteractor() {
	}

	public void generateChallenge(final ChallengeCallback challengeCallback) {
		new Thread() {
			@Override
			public void run() {
				repository.generateChallenge(new RepositoryCallback<ChallengeResponse>() {
					@Override
					public void resultOk(final ChallengeResponse response) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (challengeCallback != null) {
									challengeCallback.challengePresented(response.getQuestion());
								}
							}
						});
					}

					@Override
					public void resultError(Meta meta) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								if (challengeCallback != null) {
									challengeCallback.operativeError(context.getString(R.string._generic_error_message));
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
								if (challengeCallback != null) {
									challengeCallback.operativeError(context.getString(R.string._generic_error_message));
								}
							}
						});
					}
				});
			}
		}.start();
	}
}
