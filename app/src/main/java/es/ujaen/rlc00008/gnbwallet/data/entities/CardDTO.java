package es.ujaen.rlc00008.gnbwallet.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by Ricardo on 16/5/16.
 */
public class CardDTO implements Parcelable {

	public static final String TYPE_CREDIT = "credit";
	public static final String TYPE_DEBIT = "debit";
	public static final String TYPE_PREPAID = "prepaid";

	private String userId;
	private String pan;
	private String alias;
	private boolean nfc;
	private boolean enabled;
	private String expirationDate;
	private String beneficiary;
	private String visualCode;
	private String type;
	private AmountDTO currentBalance;
	private AmountDTO availableCredit;
	private AmountDTO creditLimit;
	private AmountDTO balance;

	// Only for persistence - Temp init TS
	private long tempTS;

	public CardDTO() {
		tempTS = -1;
	}

	public CardDTO(CardDTO cardDTO) {
		this.userId = cardDTO.userId;
		this.pan = cardDTO.pan;
		this.alias = cardDTO.alias;
		this.nfc = cardDTO.nfc;
		this.enabled = cardDTO.enabled;
		this.expirationDate = cardDTO.expirationDate;
		this.beneficiary = cardDTO.beneficiary;
		this.visualCode = cardDTO.visualCode;
		this.type = cardDTO.type;
		this.currentBalance = cardDTO.currentBalance;
		this.availableCredit = cardDTO.availableCredit;
		this.creditLimit = cardDTO.creditLimit;
		this.balance = cardDTO.balance;
		this.tempTS = cardDTO.tempTS;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isNfc() {
		return nfc;
	}

	public void setNfc(boolean nfc) {
		this.nfc = nfc;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getVisualCode() {
		return visualCode;
	}

	public void setVisualCode(String visualCode) {
		this.visualCode = visualCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AmountDTO getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(AmountDTO currentBalance) {
		this.currentBalance = currentBalance;
	}

	public AmountDTO getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(AmountDTO availableCredit) {
		this.availableCredit = availableCredit;
	}

	public AmountDTO getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(AmountDTO creditLimit) {
		this.creditLimit = creditLimit;
	}

	public AmountDTO getBalance() {
		return balance;
	}

	public void setBalance(AmountDTO balance) {
		this.balance = balance;
	}

	public long getTempTS() {
		return tempTS;
	}

	public void setTempTS(long tempTS) {
		this.tempTS = tempTS;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CardDTO other = (CardDTO) o;
		return Objects.equals(pan, other.pan);
	}

	/*
	 * Parcelable
	 */

	protected CardDTO(Parcel in) {
		userId = in.readString();
		pan = in.readString();
		alias = in.readString();
		nfc = in.readByte() != 0x00;
		enabled = in.readByte() != 0x00;
		expirationDate = in.readString();
		beneficiary = in.readString();
		visualCode = in.readString();
		type = in.readString();
		currentBalance = (AmountDTO) in.readValue(AmountDTO.class.getClassLoader());
		availableCredit = (AmountDTO) in.readValue(AmountDTO.class.getClassLoader());
		creditLimit = (AmountDTO) in.readValue(AmountDTO.class.getClassLoader());
		balance = (AmountDTO) in.readValue(AmountDTO.class.getClassLoader());
		tempTS = in.readLong();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(userId);
		dest.writeString(pan);
		dest.writeString(alias);
		dest.writeByte((byte) (nfc ? 0x01 : 0x00));
		dest.writeByte((byte) (enabled ? 0x01 : 0x00));
		dest.writeString(expirationDate);
		dest.writeString(beneficiary);
		dest.writeString(visualCode);
		dest.writeString(type);
		dest.writeValue(currentBalance);
		dest.writeValue(availableCredit);
		dest.writeValue(creditLimit);
		dest.writeValue(balance);
		dest.writeLong(tempTS);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<CardDTO> CREATOR = new Parcelable.Creator<CardDTO>() {
		@Override
		public CardDTO createFromParcel(Parcel in) {
			return new CardDTO(in);
		}

		@Override
		public CardDTO[] newArray(int size) {
			return new CardDTO[size];
		}
	};
}