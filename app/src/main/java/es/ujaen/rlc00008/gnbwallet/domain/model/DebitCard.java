package es.ujaen.rlc00008.gnbwallet.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;

/**
 * Created by Ricardo on 3/6/16.
 */
public class DebitCard extends Card implements Parcelable {

	public DebitCard(CardDTO cardDTO, boolean isFavorite) {
		super(cardDTO, isFavorite);
	}

	/*
	 * Parcelable
	 */

	protected DebitCard(Parcel in) {
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
	public static final Parcelable.Creator<DebitCard> CREATOR = new Parcelable.Creator<DebitCard>() {
		@Override
		public DebitCard createFromParcel(Parcel in) {
			return new DebitCard(in);
		}

		@Override
		public DebitCard[] newArray(int size) {
			return new DebitCard[size];
		}
	};
}
