package gnbwallet.rlc00008.ujaen.es.tpv;

import android.text.Editable;
import android.text.TextWatcher;

import java.math.BigInteger;

public class CurrencyTextWatcher implements TextWatcher {

	boolean mEditing;
	int decimals = 2;

	public CurrencyTextWatcher() {
		mEditing = false;
	}

	public CurrencyTextWatcher(int decimals) {
		mEditing = false;
		this.decimals = decimals;
	}

	public synchronized void afterTextChanged(Editable s) {

		if (!mEditing) {
			mEditing = true;
			try {
				if (".".equals(s.toString()) && s.length() == 1) {
					s.replace(0, 1, "0,");
				} else if (s.length() >= 1) {
					if (s.toString().lastIndexOf(".") == (s.length() - 1) && s.toString().lastIndexOf(",") <= -1) {
						s.append(",");
					}
				}

				if (s.toString().lastIndexOf(".") == (s.length() - 1)) {
					s.replace(0, s.length(), s.toString().substring(0, s.length() - 1));
				}

				String[] separamos = s.toString().split(",");
				String salida;

				if (separamos[0].length() <= 13) {
					BigInteger Pentera = new BigInteger(separamos[0].replace(".", ""));
					salida = String.format("%,d", Pentera).replace(",", ".");
					try {
						if (separamos[1].length() >= 1 && separamos[1].length() <= decimals) {
							salida = salida + "," + separamos[1];
						} else {
							salida = salida + "," + separamos[1].substring(0, decimals);
						}
					} catch (Exception e) {
						if (s.toString().indexOf(",") > 0) {
							salida = salida + ",";
						}
					}
				} else {
					salida = s.toString().substring(0, s.length() - 1);
				}

				s.replace(0, s.length(), salida);
			} catch (Exception e) {
				s.clear();
			}
			mEditing = false;
		}
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}
}
