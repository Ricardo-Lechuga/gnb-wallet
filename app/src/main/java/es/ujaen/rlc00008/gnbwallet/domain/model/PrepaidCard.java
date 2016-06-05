package es.ujaen.rlc00008.gnbwallet.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;

/**
 * Created by Ricardo on 3/6/16.
 */
public class PrepaidCard extends Card implements Parcelable {

	public PrepaidCard(CardDTO cardDTO, boolean isFavorite) {
		super(cardDTO, isFavorite);
	}

	public Amount getBalance() {
		return new Amount(cardDTO.getBalance());
	}

	/*
	 * Parcelable
	 */

	protected PrepaidCard(Parcel in) {
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
	public static final Parcelable.Creator<PrepaidCard> CREATOR = new Parcelable.Creator<PrepaidCard>() {
		@Override
		public PrepaidCard createFromParcel(Parcel in) {
			return new PrepaidCard(in);
		}

		@Override
		public PrepaidCard[] newArray(int size) {
			return new PrepaidCard[size];
		}
	};
}
