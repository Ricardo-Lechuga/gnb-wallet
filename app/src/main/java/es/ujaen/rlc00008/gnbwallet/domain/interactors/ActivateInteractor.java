package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.domain.base.BaseInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;

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
}
