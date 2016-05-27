package es.ujaen.rlc00008.gnbwallet.data.entities;

import java.util.Objects;

/**
 * Created by Ricardo on 16/5/16.
 */
public class UserDTO {

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

}
