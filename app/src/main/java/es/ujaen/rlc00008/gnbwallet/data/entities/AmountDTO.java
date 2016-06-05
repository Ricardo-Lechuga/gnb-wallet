package es.ujaen.rlc00008.gnbwallet.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * Created by Ricardo Lechuga on 16/5/16.
 */
public class AmountDTO implements Parcelable {

	private BigDecimal amount;
	private String currency;

	public AmountDTO() {
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/*
	 * Parcelable
	 */

	protected AmountDTO(Parcel in) {
		amount = (BigDecimal) in.readValue(BigDecimal.class.getClassLoader());
		currency = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(amount);
		dest.writeString(currency);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<AmountDTO> CREATOR = new Parcelable.Creator<AmountDTO>() {
		@Override
		public AmountDTO createFromParcel(Parcel in) {
			return new AmountDTO(in);
		}

		@Override
		public AmountDTO[] newArray(int size) {
			return new AmountDTO[size];
		}
	};
}