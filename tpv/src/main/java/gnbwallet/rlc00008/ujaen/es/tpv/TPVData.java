package gnbwallet.rlc00008.ujaen.es.tpv;

import android.content.Context;

/**
 * Created by Ricardo on 23/6/16.
 */
public class TPVData {

	public static final String COMMERCE_NAME = "GNB TPV Test";

	public static final String getTransactionConcept(Context context, int transactionNumber) {
		boolean isFirstCheck = false;

		return context.getString(R.string.transaction_concept, transactionNumber);
	}
}
