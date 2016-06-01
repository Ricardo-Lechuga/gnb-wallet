package es.ujaen.rlc00008.gnbwallet.ui.utils;

import com.aeat.valida.Validador;

/**
 * Created by Ricardo on 31/5/16.
 */
public class Validator {

	public static String formatDocument(String document) {
		if (document == null || document.length() < 2) {
			return document;
		}
		if (document.length() >= 9) {
			return document.toUpperCase();
		}
		if ("0123456789".contains(document.substring(0, 1).toUpperCase())) {
			while (document.length() < 9) {
				document = "0" + document;
			}
			return document.toUpperCase();
		}
		String subdoc = document.substring(1);
		while (subdoc.length() < 8) {
			subdoc = "0" + subdoc;
		}
		return (document.substring(0, 1) + subdoc).toUpperCase();
	}

	public static boolean checkDocument(String document) {
		if (document == null || document.length() < 9) {
			return false;
		}
		Validador val = new Validador();
		return (val.checkNif(document) > 0);
	}
}
