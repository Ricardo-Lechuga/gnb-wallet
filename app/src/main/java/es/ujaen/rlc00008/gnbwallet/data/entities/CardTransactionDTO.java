package es.ujaen.rlc00008.gnbwallet.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ricardo on 5/6/16.
 */
public class CardTransactionDTO implements Parcelable {

	private String operationDate;
	private String valueDate;
	private int transactionNumber;
	private AmountDTO amount;
	private String description;

	public CardTransactionDTO() {
	}

	public CardTransactionDTO(CardTransactionDTO cardTransactionDTO) {
		this.operationDate = cardTransactionDTO.operationDate;
		this.valueDate = cardTransactionDTO.valueDate;
		this.transactionNumber = cardTransactionDTO.transactionNumber;
		this.amount = cardTransactionDTO.amount;
		this.description = cardTransactionDTO.description;
	}

	public CardTransactionDTO(String operationDate, String valueDate, int transactionNumber, AmountDTO amount, String description) {
		this.operationDate = operationDate;
		this.valueDate = valueDate;
		this.transactionNumber = transactionNumber;
		this.amount = amount;
		this.description = description;
	}

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public int getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(int transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public AmountDTO getAmount() {
		return amount;
	}

	public void setAmount(AmountDTO amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * Parcelable
	 */

	protected CardTransactionDTO(Parcel in) {
		operationDate = in.readString();
		valueDate = in.readString();
		transactionNumber = in.readInt();
		amount = (AmountDTO) in.readValue(AmountDTO.class.getClassLoader());
		description = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(operationDate);
		dest.writeString(valueDate);
		dest.writeInt(transactionNumber);
		dest.writeValue(amount);
		dest.writeString(description);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<CardTransactionDTO> CREATOR = new Parcelable.Creator<CardTransactionDTO>() {
		@Override
		public CardTransactionDTO createFromParcel(Parcel in) {
			return new CardTransactionDTO(in);
		}

		@Override
		public CardTransactionDTO[] newArray(int size) {
			return new CardTransactionDTO[size];
		}
	};
}
