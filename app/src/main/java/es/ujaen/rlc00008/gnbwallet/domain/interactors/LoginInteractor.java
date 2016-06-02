package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.data.RepositoryCallback;
import es.ujaen.rlc00008.gnbwallet.data.source.net.Meta;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.GlobalPositionResponse;
import es.ujaen.rlc00008.gnbwallet.data.source.net.responses.LoginResponse;
import es.ujaen.rlc00008.gnbwallet.domain.BaseInteractor;

/**
 * Created by Ricardo on 22/5/16.
 */
public class LoginInteractor extends BaseInteractor {

	public interface LoginCallback extends BaseInteractorCallback {

		void loginOk();
	}

	@Inject
	public LoginInteractor() {
	}

	public void login(final String userDoc, final String password, final LoginCallback loginCallback) {
		new Thread() {
			@Override
			public void run() {

				repository.userLogin(userDoc, password, new RepositoryCallback<LoginResponse>() {
					@Override
					public void resultOk(LoginResponse response) {
						loadGlobalPosition(loginCallback);
					}

					@Override
					public void resultError(Meta meta) {
						loginCallback.operativeError(meta.getErrorMessage());
					}

					@Override
					public void genericException(Throwable t) {
						loginCallback.operativeError(context.getString(R.string._generic_error_message));
					}
				});
			}
		}.start();
	}

	private void loadGlobalPosition(final LoginCallback loginCallback) {

		repository.getGlobalPosition(new RepositoryCallback<GlobalPositionResponse>() {
			@Override
			public void resultOk(GlobalPositionResponse response) {
				loadGlobalPositionOk(loginCallback);
			}

			@Override
			public void resultError(Meta meta) {
				loginCallback.operativeError(meta.getErrorMessage());
			}

			@Override
			public void genericException(Throwable t) {
				loginCallback.operativeError(context.getString(R.string._generic_error_message));
			}
		});
	}

	private void loadGlobalPositionOk(final LoginCallback loginCallback) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (loginCallback != null) {
					loginCallback.loginOk();
				}
			}
		});
	}
}
