package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;
import es.ujaen.rlc00008.gnbwallet.domain.base.BaseInteractor;

/**
 * Created by Ricardo on 22/5/16.
 */
public class InitInteractor extends BaseInteractor {

	public interface InitCallback extends BaseInteractorCallback {

		void noUser();

		void userOk();
	}

	@Inject
	public InitInteractor() {
	}

	public void loadInitialData(final InitCallback initCallback) {
		new Thread() {
			@Override
			public void run() {
				try {
					final UserDTO userDTO = repository.getPersistedUser();

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (initCallback != null) {
								if (userDTO != null) {
									initCallback.userOk();
								} else {
									initCallback.noUser();
								}
							}
						}
					});
				} catch (Exception e) {
					if (initCallback != null) {
						initCallback.operativeError(context.getString(R.string._generic_error_message));
					}
				}
			}
		}.start();
	}
}
