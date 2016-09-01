package gnbwallet.rlc00008.ujaen.es.tpv;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

import es.ujaen.rlc00008.transactions_library.NFCTransaction;
import es.ujaen.rlc00008.transactions_library.NFCTransactionUtils;

public class TPVActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

	private BigDecimal amount;

	private EditText amountEditText;
	private Button okButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tpv);

		prepareInterface();
	}

	private void prepareInterface() {
		amountEditText = (EditText) findViewById(R.id.tpv_amount_edittext);
		okButton = (Button) findViewById(R.id.tpv_ok_button);

		amountEditText.addTextChangedListener(new CurrencyTextWatcher());

		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (localValidations()) {
					NfcAdapter.getDefaultAdapter(TPVActivity.this).setNdefPushMessageCallback(TPVActivity.this, TPVActivity.this);
				}
			}
		});
	}

	private boolean localValidations() {
		boolean validated = true;
		if (TextUtils.isEmpty(amountEditText.getText())) {
			validated = false;
			Toast.makeText(this, R.string._validate_amount_empty, Toast.LENGTH_SHORT).show();
		} else if (amountEditText.getText().toString().equals("0") || amountEditText.getText().toString().equals("0,0")
				|| amountEditText.getText().toString().equals("0,00")) {
			validated = false;
			Toast.makeText(this, R.string._validate_amount_zero, Toast.LENGTH_SHORT).show();
		}
		if (validated) {
			try {
				String amountString = amountEditText.getText().toString().replace(".", "").replace(",", ".");
				amount = new BigDecimal(amountString);
			} catch (Exception e) {
				validated = false;
				Toast.makeText(this, R.string._validate_amount_invalid, Toast.LENGTH_SHORT).show();
			}
		}
		if (validated) {
			if (!NFCUtils.isNfcCompatible(this)) {
				validated = false;
				Toast.makeText(this, R.string.nfc_not_compatible, Toast.LENGTH_SHORT).show();
			} else if (!NFCUtils.isNfcEnabled(this)) {
				validated = false;
				Toast.makeText(this, R.string.nfc_not_enabled, Toast.LENGTH_SHORT).show();
			}
		}

		return validated;
	}

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {

		NFCTransaction nfcTransaction = TransactionBuilder.generateTransaction(this, amount, "EUR");

		return new NdefMessage(
				new NdefRecord(NdefRecord.TNF_EXTERNAL_TYPE,
						"es.ujaen.rlc00008.tpv-transaction".getBytes(),
						new byte[] {},
						NFCTransactionUtils.serialize(nfcTransaction)));
	}
}
