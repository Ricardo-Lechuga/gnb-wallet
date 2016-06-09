package es.ujaen.rlc00008.gnbwallet.data.source.memory;

import java.util.ArrayList;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.ChallengeDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;

/**
 * Created by Ricardo on 16/5/16.
 */
public class SessionData {

	private String userToken;
	private String userLogin;
	private UserDTO currentUser;
	private ArrayList<CardDTO> userCards;
	private String favoriteCard;
	private ChallengeDTO challengeDTO;

	public SessionData() {
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
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

	public String getFavoriteCard() {
		return favoriteCard;
	}

	public void setFavoriteCard(String favoriteCard) {
		this.favoriteCard = favoriteCard;
	}

	public ChallengeDTO getChallengeDTO() {
		return challengeDTO;
	}

	public void setChallengeDTO(ChallengeDTO challengeDTO) {
		this.challengeDTO = challengeDTO;
	}
}
