package es.ujaen.rlc00008.transactions_library;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.UnsupportedEncodingException;

/**
 * Created by Ricardo on 23/6/16.
 */
public class NFCTransactionUtils {

	private static final String ENCODING = "UTF-8";

	public static byte[] serialize(NFCTransaction nfcTransaction) {
		try {
			return new Gson().toJson(nfcTransaction).getBytes(ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported encoding...");
		} catch (JsonParseException jsonParseException) {
			throw new RuntimeException("Error encoding NFCTransaction!");
		}
	}

	public static NFCTransaction deserialize(byte[] payload) {
		try {
			return new Gson().fromJson(new String(payload, ENCODING), NFCTransaction.class);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("unsupported encoding...");
		} catch (JsonParseException jsonParseException) {
			throw new RuntimeException("Error decoding NFCTransaction!");
		}
	}
}
