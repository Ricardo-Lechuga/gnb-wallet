package es.ujaen.rlc00008.gnbwallet.data.source.net.responses;

import java.util.List;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;

/**
 * Created by Ricardo on 2/6/16.
 */
public class GlobalPositionResponse {

	private UserDTO userData;
	private List<CardDTO> cards;
	private String favoriteCard;

	public GlobalPositionResponse() {
	}

	public UserDTO getUserData() {
		return userData;
	}

	public void setUserData(UserDTO userData) {
		this.userData = userData;
	}

	public List<CardDTO> getCards() {
		return cards;
	}

	public void setCards(List<CardDTO> cards) {
		this.cards = cards;
	}

	public String getFavoriteCard() {
		return favoriteCard;
	}

	public void setFavoriteCard(String favoriteCard) {
		this.favoriteCard = favoriteCard;
	}
}
