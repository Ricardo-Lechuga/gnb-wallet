package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.domain.BaseInteractor;

/**
 * Created by Ricardo on 22/5/16.
 */
public class InitInteractor extends BaseInteractor{

	public interface InitCallback extends BaseInteractorCallback {

		void noUser();

		void userOk();
	}

	@Inject
	public InitInteractor() {
	}
}
