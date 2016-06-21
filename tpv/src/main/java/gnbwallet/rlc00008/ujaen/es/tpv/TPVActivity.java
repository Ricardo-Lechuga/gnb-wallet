package gnbwallet.rlc00008.ujaen.es.tpv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TPVActivity extends AppCompatActivity {

	private String amountString;

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
					sendNFCMessage();
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
				amountString = amountEditText.getText().toString().replace(".", "").replace(",", ".");
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

	private void sendNFCMessage() {

		Toast.makeText(this, amountString, Toast.LENGTH_SHORT).show();
	}
}
