package es.ujaen.rlc00008.gnbwallet.data.source.persistence.db.cards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.db.GNBCipheredDatabase;

/**
 * Created by Ricardo on 22/5/16.
 */
public class DAOCards {

	private static final String[] allColumns = {
			CardPersistenceContract.Entry.COLUMN_NAME_USER_ID,
			CardPersistenceContract.Entry.COLUMN_NAME_PAN,
			CardPersistenceContract.Entry.COLUMN_NAME_NFC,
			CardPersistenceContract.Entry.COLUMN_NAME_ENABLED,
			CardPersistenceContract.Entry.COLUMN_NAME_BENEFICIARY,
			CardPersistenceContract.Entry.COLUMN_NAME_VISUAL_CODE,
			CardPersistenceContract.Entry.COLUMN_NAME_TYPE
	};

	private static CardDTO cursorToCardDTO(Cursor cursor) {

		String userId = cursor.getString(cursor.getColumnIndex(CardPersistenceContract.Entry.COLUMN_NAME_USER_ID));
		String pan = cursor.getString(cursor.getColumnIndex(CardPersistenceContract.Entry.COLUMN_NAME_PAN));
		boolean nfc = cursor.getInt(cursor.getColumnIndex(CardPersistenceContract.Entry.COLUMN_NAME_NFC)) == 1;
		boolean enabled = cursor.getInt(cursor.getColumnIndex(CardPersistenceContract.Entry.COLUMN_NAME_ENABLED)) == 1;
		String beneficiary = cursor.getString(cursor.getColumnIndex(CardPersistenceContract.Entry.COLUMN_NAME_BENEFICIARY));
		String visualCode = cursor.getString(cursor.getColumnIndex(CardPersistenceContract.Entry.COLUMN_NAME_VISUAL_CODE));
		String type = cursor.getString(cursor.getColumnIndex(CardPersistenceContract.Entry.COLUMN_NAME_TYPE));

		return new CardDTO(userId, pan, nfc, enabled, beneficiary, visualCode, type);
	}

	private static ContentValues cardDTOToContentValues(CardDTO cardDTO) {

		ContentValues values = new ContentValues();

		values.put(CardPersistenceContract.Entry.COLUMN_NAME_USER_ID, cardDTO.getUserId());
		values.put(CardPersistenceContract.Entry.COLUMN_NAME_PAN, cardDTO.getPan());
		values.put(CardPersistenceContract.Entry.COLUMN_NAME_NFC, cardDTO.isNfc() ? 1 : 0);
		values.put(CardPersistenceContract.Entry.COLUMN_NAME_ENABLED, cardDTO.isEnabled() ? 1 : 0);
		values.put(CardPersistenceContract.Entry.COLUMN_NAME_BENEFICIARY, cardDTO.getBeneficiary());
		values.put(CardPersistenceContract.Entry.COLUMN_NAME_VISUAL_CODE, cardDTO.getVisualCode());
		values.put(CardPersistenceContract.Entry.COLUMN_NAME_TYPE, cardDTO.getType());

		return values;
	}

	public static void setCards(Context context, GNBCipheredDatabase gnbCipheredDatabase, List<CardDTO> cards) {

	}

	public static List<CardDTO> getUserCards(Context context, GNBCipheredDatabase gnbCipheredDatabase, String userId) {
		return null;
	}
}
