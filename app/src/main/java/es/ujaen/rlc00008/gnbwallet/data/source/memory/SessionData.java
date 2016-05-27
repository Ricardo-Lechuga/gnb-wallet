package es.ujaen.rlc00008.gnbwallet.data.source.memory;

import java.util.ArrayList;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;

/**
 * Created by Ricardo on 16/5/16.
 */
public class SessionData {

	private UserDTO currentUser;
	private ArrayList<CardDTO> userCards;

	public SessionData() {
	}

	public UserDTO getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserDTO currentUser) {
		this.currentUser = currentUser;
	}

	public ArrayList<CardDTO> getUserCards() {
		return userCards;
	}

	public void setUserCards(ArrayList<CardDTO> userCards) {
		this.userCards = userCards;
	}
}
