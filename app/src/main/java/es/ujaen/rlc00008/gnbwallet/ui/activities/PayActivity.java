package es.ujaen.rlc00008.gnbwallet.ui.activities;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.transactions_library.NFCTransaction;
import es.ujaen.rlc00008.transactions_library.NFCTransactionUtils;

/**
 * Created by Ricardo on 24/6/16.
 */
public class PayActivity extends BaseActivity {

	@BindView(R.id.pay_content_frame) FrameLayout contentFrame;

	@Override
	protected int getContentView() {
		return R.layout.activity_pay;
	}

	@Override
	protected void prepareInterface() {
		//TODO
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		super.onNewIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processIntent(getIntent());
		}
	}

	private void processIntent(Intent intent) {
		Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
				NfcAdapter.EXTRA_NDEF_MESSAGES);
		// only one message sent during the beam
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		// record 0 contains the MIME type, record 1 is the AAR, if present
		NFCTransaction nfcTransaction = NFCTransactionUtils.deserialize(msg.getRecords()[0].getPayload());
		Toast.makeText(this, "Transaction: Amount: " + nfcTransaction.getAmount(), Toast.LENGTH_SHORT).show();
	}
}
