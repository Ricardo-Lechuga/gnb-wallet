package es.ujaen.rlc00008.gnbwallet.domain.interactors;

import android.content.Context;
import android.nfc.NfcAdapter;

/**
 * Created by Ricardo on 19/6/16.
 */
public class NFCUtils {

	static boolean isNfcCompatible(Context context) {
		return NfcAdapter.getDefaultAdapter(context) != null;
	}

	static boolean isNfcEnabled(Context context) {
		NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
		return nfcAdapter != null && nfcAdapter.isEnabled();
	}
}
