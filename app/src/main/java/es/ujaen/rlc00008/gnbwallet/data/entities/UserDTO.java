package es.ujaen.rlc00008.gnbwallet.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by Ricardo on 16/5/16.
 */
public class UserDTO implements Parcelable {

	private String name;
	private String surname;
	private String userId;
	private String nif;

	public UserDTO() {
	}

	public UserDTO(UserDTO userDTO) {
		this.name = userDTO.name;
		this.surname = userDTO.surname;
		this.userId = userDTO.userId;
		this.nif = userDTO.nif;
	}

	public UserDTO(String name, String surname, String userId, String nif) {
		this.name = name;
		this.surname = surname;
		this.userId = userId;
		this.nif = nif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDTO userDTO = (UserDTO) o;
		return Objects.equals(userId, userDTO.userId);
	}

	/*
	 * Parcelable
	 */

	protected UserDTO(Parcel in) {
		name = in.readString();
		surname = in.readString();
		userId = in.readString();
		nif = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(surname);
		dest.writeString(userId);
		dest.writeString(nif);
	}

	@SuppressWarnings("unused")
	public static final Parcelable.Creator<UserDTO> CREATOR = new Parcelable.Creator<UserDTO>() {
		@Override
		public UserDTO createFromParcel(Parcel in) {
			return new UserDTO(in);
		}

		@Override
		public UserDTO[] newArray(int size) {
			return new UserDTO[size];
		}
	};
}
