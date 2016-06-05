package es.ujaen.rlc00008.gnbwallet.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;

/**
 * Created by Ricardo on 3/6/16.
 */
public class CreditCard extends Card implements Parcelable {

	public CreditCard(CardDTO cardDTO, boolean isFavorite) {
		super(cardDTO, isFavorite);
	}

	public Amount getCurrentBalance() {
		return new Amount(cardDTO.getCurrentBalance());
	}

	public Amount getAvailableCredit() {
		return new Amount(cardDTO.getAvailableCredit());
	}

	public Amount getCreditLimit() {
		return new Amount(cardDTO.getCreditLimit());
	}

	/*
	 * Parcelable
	 */

	protected CreditCard(Parcel in) {
		super((CardDTO) in.readValue(CardDTO.class.getClassLoader()), in.readInt() == 1);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(cardDTO);
		dest.writeInt(isFavorite ? 1 : 0);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<CreditCard> CREATOR = new Parcelable.Creator<CreditCard>() {
		@Override
		public CreditCard createFromParcel(Parcel in) {
			return new CreditCard(in);
		}

		@Override
		public CreditCard[] newArray(int size) {
			return new CreditCard[size];
		}
	};
}
