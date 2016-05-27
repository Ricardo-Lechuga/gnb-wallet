package es.ujaen.rlc00008.gnbwallet.domain;

import android.content.Context;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.data.GNBRepository;

/**
 * Created by Ricardo on 22/5/16.
 */
public class BaseInteractor {

	@Inject Context context;
	@Inject GNBRepository repository;

	protected interface BaseInteractorCallback {
		void operativeError(String message);
	}
}
