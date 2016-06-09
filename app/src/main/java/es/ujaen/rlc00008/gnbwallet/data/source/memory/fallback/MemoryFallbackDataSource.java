package es.ujaen.rlc00008.gnbwallet.data.source.memory.fallback;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.ChallengeDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;
import es.ujaen.rlc00008.gnbwallet.data.source.memory.MemoryDataSource;

/**
 * Created by Ricardo on 21/5/16.
 */
public class MemoryFallbackDataSource implements MemoryDataSource {

	private static final String GNB_FALLBACK_SP_NAME = "fallback_preferences";

	private static final String TAG_TOKEN = "TAG_TOKEN";
	private static final String TAG_LOGIN = "TAG_LOGIN";
	private static final String TAG_USER = "TAG_USER";
	private static final String TAG_CARDS = "TAG_CARDS";
	private static final String TAG_FAVORITE_CARD = "TAG_FAVORITE_CARD";
	private static final String TAG_CHALLENGE = "TAG_CHALLENGE";

	private SharedPreferences sharedPreferences;

	@Inject
	public MemoryFallbackDataSource(Context context) {
		Preconditions.checkNotNull(context);
		this.sharedPreferences = context.getSharedPreferences(GNB_FALLBACK_SP_NAME, Context.MODE_PRIVATE);
	}

	@Override
	public String getUserToken() {
		return sharedPreferences.getString(TAG_TOKEN, "");
	}

	@Override
	public void setUserToken(String userToken) {
		sharedPreferences.edit().putString(TAG_TOKEN, userToken).apply();
	}

	@Override
	public String getUserLogin() {
		return sharedPreferences.getString(TAG_LOGIN, "");
	}

	@Override
	public void setUserLogin(String login) {
		sharedPreferences.edit().putString(TAG_LOGIN, login).apply();
	}

	@Override
	public UserDTO getUserData() {
		return new Gson().fromJson(sharedPreferences.getString(TAG_USER, ""), UserDTO.class);
	}

	@Override
	public void setUserData(UserDTO userDTO) {
		sharedPreferences.edit().putString(TAG_USER, new Gson().toJson(userDTO)).apply();
	}

	@Override
	public List<CardDTO> getUserCards(String userId) {
		String cardsString = sharedPreferences.getString(TAG_CARDS, "");
		CardDTO[] videoArray = new Gson().fromJson(cardsString, CardDTO[].class);
		return Arrays.asList(videoArray);
	}

	@Override
	public void setUserCards(List<CardDTO> userCards) {
		sharedPreferences.edit().putString(TAG_CARDS, new Gson().toJson(userCards)).apply();
	}

	@Override
	public void updateUserCard(CardDTO cardDTO) {
		String cardsString = sharedPreferences.getString(TAG_CARDS, "");
		CardDTO[] videoArray = new Gson().fromJson(cardsString, CardDTO[].class);
		List<CardDTO> cardDTOList = Arrays.asList(videoArray);
		int position = cardDTOList.indexOf(cardDTO);
		if (position >= 0) {
			cardDTOList.set(position, cardDTO);
		}
		setUserCards(cardDTOList);
	}

	@Override
	public String getFavoriteCard() {
		return sharedPreferences.getString(TAG_FAVORITE_CARD, "");
	}

	@Override
	public void setFavoriteCard(String favoriteCard) {
		sharedPreferences.edit().putString(TAG_FAVORITE_CARD, favoriteCard).apply();
	}

	@Override
	public void cleanSessionData() {
		sharedPreferences.edit().clear().apply();
	}

	@Override
	public ChallengeDTO getChallengeDTO() {
		return new Gson().fromJson(sharedPreferences.getString(TAG_CHALLENGE, ""), ChallengeDTO.class);
	}

	@Override
	public void setChallengeDTO(ChallengeDTO challengeDTO) {
		sharedPreferences.edit().putString(TAG_CHALLENGE, new Gson().toJson(challengeDTO)).apply();
	}
}
