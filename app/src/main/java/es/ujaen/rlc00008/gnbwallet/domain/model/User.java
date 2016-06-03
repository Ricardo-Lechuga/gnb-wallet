package es.ujaen.rlc00008.gnbwallet.domain.model;

import com.google.common.base.Preconditions;

import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;

/**
 * Created by Ricardo on 4/6/16.
 */
public class User {

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
}
