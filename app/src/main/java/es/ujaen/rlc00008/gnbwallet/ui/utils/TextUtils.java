package es.ujaen.rlc00008.gnbwallet.ui.utils;

import es.ujaen.rlc00008.gnbwallet.domain.model.factories.GNBLocale;

/**
 * Created by Ricardo on 15/6/16.
 */
public class TextUtils {

	public static String toAliasCase(String value) {
		if (android.text.TextUtils.isEmpty(value)) {
			return value;
		}
		String stringPreFormatted = value.toLowerCase(GNBLocale.get()).trim();
		char[] stringCharArray = stringPreFormatted.toCharArray();
		for (int i = 0; i < stringPreFormatted.length() - 1; i++) {
			if (stringCharArray[i] == ' ' || stringCharArray[i] == '.' || stringCharArray[i] == ',') {
				stringCharArray[i + 1] = Character.toUpperCase(stringCharArray[i + 1]);
			}
		}
		stringCharArray[0] = Character.toUpperCase(stringCharArray[0]);
		return new String(stringCharArray);
	}
}
