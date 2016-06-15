package es.ujaen.rlc00008.gnbwallet.data.source.persistence.db.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.db.GNBCipheredDatabase;

/**
 * Created by Ricardo on 22/5/16.
 */
public class DAOUser {

	private static final String[] allColumns = {
			UserPersistenceContract.Entry.COLUMN_NAME_USER_ID,
			UserPersistenceContract.Entry.COLUMN_NAME_NIF,
			UserPersistenceContract.Entry.COLUMN_NAME_NAME,
			UserPersistenceContract.Entry.COLUMN_NAME_SURNAME
	};

	private static UserDTO cursorToUserDTO(Cursor cursor) {

		String userId = cursor.getString(cursor.getColumnIndex(UserPersistenceContract.Entry.COLUMN_NAME_USER_ID));
		String nif = cursor.getString(cursor.getColumnIndex(UserPersistenceContract.Entry.COLUMN_NAME_NIF));
		String name = cursor.getString(cursor.getColumnIndex(UserPersistenceContract.Entry.COLUMN_NAME_NAME));
		String surname = cursor.getString(cursor.getColumnIndex(UserPersistenceContract.Entry.COLUMN_NAME_SURNAME));

		return new UserDTO(userId, nif, name, surname);
	}

	private static ContentValues userDTOToContentValues(UserDTO user) {
		ContentValues values = new ContentValues();

		values.put(UserPersistenceContract.Entry.COLUMN_NAME_USER_ID, user.getUserId());
		values.put(UserPersistenceContract.Entry.COLUMN_NAME_NIF, user.getNif());
		values.put(UserPersistenceContract.Entry.COLUMN_NAME_NAME, user.getName());
		values.put(UserPersistenceContract.Entry.COLUMN_NAME_SURNAME, user.getSurname());

		return values;
	}

	public static boolean setUser(Context context, GNBCipheredDatabase gnbCipheredDatabase, UserDTO userDTO) {
		ContentValues values = userDTOToContentValues(userDTO);
		SQLiteDatabase database = gnbCipheredDatabase.getWritableDatabase();
		Cursor cursor = database.query(UserPersistenceContract.Entry.TABLE_NAME, allColumns, UserPersistenceContract.Entry.COLUMN_NAME_USER_ID
				+ "='" + userDTO.getUserId() + "'", null, null, null, null);
		boolean isInsert = true;
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			isInsert = false;
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		if (isInsert) {
			database.insert(UserPersistenceContract.Entry.TABLE_NAME, null, values);
		} else {
			database.update(UserPersistenceContract.Entry.TABLE_NAME, values,
					UserPersistenceContract.Entry.COLUMN_NAME_USER_ID + "='" + userDTO.getUserId() + "'", null);
		}
		database.close();
		return isInsert;
	}

	public static UserDTO getUser(Context context, GNBCipheredDatabase gnbCipheredDatabase) {
		SQLiteDatabase database = gnbCipheredDatabase.getReadableDatabase();
		Cursor cursor = database.query(UserPersistenceContract.Entry.TABLE_NAME, allColumns, null, null, null, null, null);
		UserDTO userDTO = null;
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			userDTO = cursorToUserDTO(cursor);
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		database.close();
		return userDTO;
	}
}
