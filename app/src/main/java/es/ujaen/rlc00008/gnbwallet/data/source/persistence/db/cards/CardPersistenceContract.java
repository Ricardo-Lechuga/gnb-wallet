package es.ujaen.rlc00008.gnbwallet.data.source.persistence.db.cards;

import android.provider.BaseColumns;

/**
 * Created by Ricardo on 16/5/16.
 */
public final class CardPersistenceContract {

	// To prevent someone from accidentally instantiating the contract class,
	// give it an empty constructor.
	public CardPersistenceContract() {}

	/* Inner class that defines the table contents */
	public static abstract class Entry implements BaseColumns {
		public static final String TABLE_NAME = "cards";
		public static final String COLUMN_NAME_USER_ID = "user_id";
		public static final String COLUMN_NAME_PAN = "pan";
		public static final String COLUMN_NAME_NFC = "nfc";
		public static final String COLUMN_NAME_ENABLED = "enabled";
		public static final String COLUMN_NAME_BENEFICIARY = "beneficiary";
		public static final String COLUMN_NAME_VISUAL_CODE = "visual_code";
		public static final String COLUMN_NAME_TYPE = "type";
	}
}
