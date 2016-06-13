package es.ujaen.rlc00008.gnbwallet.domain.model.factories;

import android.os.Build;

import java.util.Locale;

/**
 * Created by Ricardo on 13/6/16.
 */
public class GNBLocale {

	public static Locale get() {
		if (Build.VERSION.SDK_INT >= 21) {
			return new Locale("es", "ES");
		}
		return new Locale("es_ES");
	}
}
