package es.ujaen.rlc00008.gnbwallet.data.source.persistence.db;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.utils.Installation;

/**
 * La clase GNBCipheredDatabase representa la base de datos de nuestro sistema. Los
 * DAOs del proyecto utilizan esta clase.
 *
 * @author Ricardo Lechuga (ricardo.cion@gmail.com)
 */
public class GNBCipheredDatabase extends SQLiteOpenHelper {

	// Constantes útiles SQL
	static final String TYPE_NULL = " NULL";
	static final String TYPE_INTEGER = " INTEGER";
	static final String TYPE_REAL = " REAL";
	static final String TYPE_TEXT = " TEXT";
	static final String TYPE_BLOB = " BLOB";
	static final String COMMA = ",";
	static final String PRIMARY_KEY = " PRIMARY KEY";
	static final String NOT_NULL = " NOT NULL";

	// Nombre de la base de datos de nuestra aplicación
	private static final String DATABASE_NAME = "gnb_ciphered.db";

	// Versión de la base de datos. CUIDADO EXTREMO AL CAMBIAR ESTO!
	private static final int DATABASE_VERSION = 1;

	private static final String LOG_TAG = GNBCipheredDatabase.class.getSimpleName();

	private static final String CREATE_TABLE_USERS =
			"CREATE TABLE " + UserPersistenceContract.Entry.TABLE_NAME + " (" +
					UserPersistenceContract.Entry._ID + TYPE_TEXT + " PRIMARY KEY," +
					UserPersistenceContract.Entry.COLUMN_NAME_USER_ID + TYPE_TEXT + COMMA +
					UserPersistenceContract.Entry.COLUMN_NAME_NIF + TYPE_TEXT + COMMA +
					UserPersistenceContract.Entry.COLUMN_NAME_NAME + TYPE_TEXT + COMMA +
					UserPersistenceContract.Entry.COLUMN_NAME_SURNAME + TYPE_TEXT +
					" )";

	private static final String CREATE_TABLE_CARDS =
			"CREATE TABLE " + CardPersistenceContract.Entry.TABLE_NAME + " (" +
					CardPersistenceContract.Entry._ID + TYPE_TEXT + " PRIMARY KEY," +
					CardPersistenceContract.Entry.COLUMN_NAME_USER_ID + TYPE_TEXT + COMMA +
					CardPersistenceContract.Entry.COLUMN_NAME_PAN + TYPE_TEXT + COMMA +
					CardPersistenceContract.Entry.COLUMN_NAME_NFC + TYPE_INTEGER + COMMA +
					CardPersistenceContract.Entry.COLUMN_NAME_ENABLED + TYPE_INTEGER + COMMA +
					CardPersistenceContract.Entry.COLUMN_NAME_BENEFICIARY + TYPE_TEXT + COMMA +
					CardPersistenceContract.Entry.COLUMN_NAME_VISUAL_CODE + TYPE_TEXT + COMMA +
					CardPersistenceContract.Entry.COLUMN_NAME_TYPE + TYPE_TEXT +
					" )";

	/**
	 * @param context Contexto
	 */
	public GNBCipheredDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	static String getDBPassword(Context context) {
		return Installation.id(context);
	}

	public static void deleteAllData(Context context) {
		MyLog.d(LOG_TAG, "Deleting all data...");
		SQLiteDatabase db = new GNBCipheredDatabase(context).getWritableDatabase(getDBPassword(context));
		db.delete(UserPersistenceContract.Entry.TABLE_NAME, "1", null);
		db.delete(CardPersistenceContract.Entry.TABLE_NAME, "1", null);
		db.close();
		MyLog.d(LOG_TAG, "All data deleted!");
	}

	public static boolean deleteDatabase(Context context) {
		return context.deleteDatabase(DATABASE_NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		MyLog.d(LOG_TAG, "onCreate");
		createAllTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Not required as at version 1
	}

	private void createAllTables(SQLiteDatabase db) {
		MyLog.d(LOG_TAG, "Creating all tables...");
		db.execSQL(CREATE_TABLE_USERS);
		db.execSQL(CREATE_TABLE_CARDS);
		MyLog.d(LOG_TAG, "All tables created!");
	}

	private void deleteAllTables(SQLiteDatabase db) {
		MyLog.d(LOG_TAG, "Deleting all tables...");
		db.execSQL("DROP TABLE IF EXISTS " + UserPersistenceContract.Entry.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + CardPersistenceContract.Entry.TABLE_NAME);
		MyLog.d(LOG_TAG, "All tables deleted!");
	}
}