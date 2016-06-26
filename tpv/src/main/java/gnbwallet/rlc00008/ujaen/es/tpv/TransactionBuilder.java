package gnbwallet.rlc00008.ujaen.es.tpv;

import android.content.Context;
import android.content.SharedPreferences;

import java.math.BigDecimal;

import es.ujaen.rlc00008.transactions_library.NFCTransaction;

/**
 * Created by Ricardo on 24/6/16.
 */
public class TransactionBuilder {

	private static final String SP_NAME = "TPVData";
	private static final String SP_TRANSACTION_COUNTER_TAG = "TransactionCounter";

	public static NFCTransaction generateTransaction(Context context, BigDecimal amount) {

		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

		int counter = sp.getInt(SP_TRANSACTION_COUNTER_TAG, 0);
		SharedPreferences.Editor ed = sp.edit();
		ed.putInt(SP_TRANSACTION_COUNTER_TAG, counter + 1);
		ed.commit();

		return new NFCTransaction(TPVData.COMMERCE_NAME, amount, TPVData.getTransactionConcept(context, counter));
	}
}
