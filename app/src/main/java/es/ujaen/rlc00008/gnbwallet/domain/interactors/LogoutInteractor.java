package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.domain.base.BaseInteractor;

/**
 * Created by Ricardo on 22/5/16.
 */
public class LogoutInteractor extends BaseInteractor {

	@Inject
	public LogoutInteractor() {
	}

	public void logout() {
		repository.logout();
	}
}
