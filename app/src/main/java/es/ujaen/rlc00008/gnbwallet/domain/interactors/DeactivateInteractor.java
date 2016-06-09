package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.domain.base.BaseInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;

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
}
