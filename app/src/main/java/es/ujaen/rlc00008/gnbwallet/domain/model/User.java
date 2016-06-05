package es.ujaen.rlc00008.gnbwallet.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Preconditions;

import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;

/**
 * Created by Ricardo on 4/6/16.
 */
public class User implements Parcelable {

	private UserDTO userDTO;

	public User(UserDTO userDTO) {
		Preconditions.checkNotNull(userDTO);
		this.userDTO = userDTO;
	}

	public String getName() {
		return userDTO.getName();
	}

	public String getSurname() {
		return userDTO.getSurname();
	}

	public String getFullName() {
		return userDTO.getName() + " " + userDTO.getSurname();
	}

	public String getNif() {
		return userDTO.getNif();
	}

	/*
	 * Parcelable
	 */

	protected User(Parcel in) {
		userDTO = (UserDTO) in.readValue(UserDTO.class.getClassLoader());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(userDTO);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
}
