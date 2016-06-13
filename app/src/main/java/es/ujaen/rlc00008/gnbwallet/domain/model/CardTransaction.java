package es.ujaen.rlc00008.gnbwallet.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.data.entities.CardTransactionDTO;
import es.ujaen.rlc00008.gnbwallet.domain.model.factories.GNBLocale;

/**
 * Created by Ricardo on 13/6/16.
 */
public class CardTransaction implements Parcelable {

	private final CardTransactionDTO cardTransactionDTO;

	public CardTransaction(CardTransactionDTO cardTransactionDTO) {
		this.cardTransactionDTO = cardTransactionDTO;
	}

	public CardTransactionDTO getCardTransactionDTO() {
		return cardTransactionDTO;
	}

	public Amount getAmount() {
		return new Amount(cardTransactionDTO.getAmount());
	}

	public String getDescription() {
		return cardTransactionDTO.getDescription() != null ? cardTransactionDTO.getDescription().trim() : "";
	}

	@Nullable
	public Calendar getOperationCalendar() {
		Calendar operationCalendar = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", GNBLocale.get());
		try {
			Date operationDate = sdf.parse(cardTransactionDTO.getOperationDate());
			operationCalendar = Calendar.getInstance(GNBLocale.get());
			operationCalendar.setTime(operationDate);
		} catch (ParseException e) {
			MyLog.printStackTrace(e);
		}
		return operationCalendar;
	}

	@Nullable
	public Calendar getValueCalendar() {
		Calendar valueCalendar = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", GNBLocale.get());
		try {
			Date valueDate = sdf.parse(cardTransactionDTO.getValueDate());
			valueCalendar = Calendar.getInstance(GNBLocale.get());
			valueCalendar.setTime(valueDate);
		} catch (ParseException e) {
			MyLog.printStackTrace(e);
		}
		return valueCalendar;
	}

	/*
	 * Parcelable
	 */

	protected CardTransaction(Parcel in) {
		cardTransactionDTO = (CardTransactionDTO) in.readValue(CardTransactionDTO.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(cardTransactionDTO);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<CardTransaction> CREATOR = new Parcelable.Creator<CardTransaction>() {
		@Override
		public CardTransaction createFromParcel(Parcel in) {
			return new CardTransaction(in);
		}

		@Override
		public CardTransaction[] newArray(int size) {
			return new CardTransaction[size];
		}
	};
}