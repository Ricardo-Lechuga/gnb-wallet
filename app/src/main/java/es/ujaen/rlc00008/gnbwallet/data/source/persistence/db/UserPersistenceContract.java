package es.ujaen.rlc00008.gnbwallet.data.source.persistence.db;

import android.provider.BaseColumns;


/**
 * Created by Ricardo on 16/5/16.
 */
public final class UserPersistenceContract {

	// To prevent someone from accidentally instantiating the contract class,
	// give it an empty constructor.
	public UserPersistenceContract() {}

	/* Inner class that defines the table contents */
	public static abstract class Entry implements BaseColumns {
		public static final String TABLE_NAME = "users";
		public static final String COLUMN_NAME_USER_ID = "user_id";
		public static final String COLUMN_NAME_NIF = "nif";
		public static final String COLUMN_NAME_NAME = "name";
		public static final String COLUMN_NAME_SURNAME = "surname";
	}
}
