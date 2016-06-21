package gnbwallet.rlc00008.ujaen.es.tpv;

import android.content.Context;
import android.nfc.NfcAdapter;

/**
 * Created by Ricardo on 21/6/16.
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
