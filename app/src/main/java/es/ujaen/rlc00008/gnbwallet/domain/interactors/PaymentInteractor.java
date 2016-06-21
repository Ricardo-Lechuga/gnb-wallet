package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.base.BaseInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;

/**
 * Created by Ricardo on 19/6/16.
 */
public class PaymentInteractor extends BaseInteractor {

	public interface PaymentCallback extends BaseInteractorCallback {
		void awaitingForPayment();
	}

	@Inject
	public PaymentInteractor() {
	}

	public void payNow(Card card, PaymentCallback callback) {
		if (!NFCUtils.isNfcCompatible(context)) {
			callback.operativeError(context.getString(R.string.nfc_not_compatible));
		} else if (!NFCUtils.isNfcEnabled(context)) {
			callback.operativeError(context.getString(R.string.nfc_not_enabled));
		} else {
			callback.awaitingForPayment();
		}
	}
}
