package es.ujaen.rlc00008.gnbwallet.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Preconditions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import es.ujaen.rlc00008.gnbwallet.data.entities.AmountDTO;

/**
 * Created by Ricardo on 3/6/16.
 */
public class Amount implements Parcelable {

	private final AmountDTO amountDTO;

	public Amount(AmountDTO amountDTO) {
		Preconditions.checkNotNull(amountDTO);
		this.amountDTO = amountDTO;
	}

	public AmountDTO getAmountDTO() {
		return amountDTO;
	}

	public double getAmountValue() {
		return amountDTO.getAmount().doubleValue();
	}

	public String getValueFormatted() {
		try {
			DecimalFormat formato = new DecimalFormat("#,##0.00");
			DecimalFormatSymbols s = new DecimalFormatSymbols();
			s.setDecimalSeparator(',');
			s.setGroupingSeparator('.');
			formato.setDecimalFormatSymbols(s);
			return formato.format(amountDTO.getAmount());
		} catch (Exception e) {
			return "0";
		}
	}

	public String getCurrency() {
		return amountDTO.getCurrency();
	}

	public String getCurrencySymbol() {
		String currencyCode = amountDTO.getCurrency();

		final String currencySymbol;

		if ("EUR".equalsIgnoreCase(currencyCode)) {
			currencySymbol = "€";
		} else if ("USD".equalsIgnoreCase(currencyCode)) {
			currencySymbol = "$";
		} else if ("GBP".equalsIgnoreCase(currencyCode)) {
			currencySymbol = "£";
		} else if (currencyCode != null) {
			currencySymbol = currencyCode;
		} else {
			currencySymbol = "";
		}

		return currencySymbol;
	}

	public String getAmountFormatted() {
		return getValueFormatted() + getCurrencySymbol();
	}

	/*
	 * Parcelable
	 */

	protected Amount(Parcel in) {
		amountDTO = (AmountDTO) in.readValue(AmountDTO.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(amountDTO);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<Amount> CREATOR = new Parcelable.Creator<Amount>() {
		@Override
		public Amount createFromParcel(Parcel in) {
			return new Amount(in);
		}

		@Override
		public Amount[] newArray(int size) {
			return new Amount[size];
		}
	};
}
