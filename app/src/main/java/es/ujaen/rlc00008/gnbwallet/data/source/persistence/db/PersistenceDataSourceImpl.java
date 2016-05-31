package es.ujaen.rlc00008.gnbwallet.data.source.persistence.db;

import android.content.Context;

import java.util.List;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.PersistenceDataSource;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.db.cards.DAOCards;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.db.user.DAOUser;

/**
 * Created by Ricardo on 22/5/16.
 */
public class PersistenceDataSourceImpl implements PersistenceDataSource {

	private GNBCipheredDatabase gnbCipheredDatabase;
	private Context context;

	@Inject
	public PersistenceDataSourceImpl(Context context) {
		this.context = context;
		gnbCipheredDatabase = new GNBCipheredDatabase(context);
	}

	@Override
	public UserDTO getStoredUser() {
		return DAOUser.getUser(context, gnbCipheredDatabase);
	}

	@Override
	public void setStoredUser(UserDTO userDTO) {
		DAOUser.setUser(context, gnbCipheredDatabase, userDTO);
	}

	@Override
	public List<CardDTO> getUserCards(String userId) {
		return DAOCards.getUserCards(context, gnbCipheredDatabase, userId);
	}

	@Override
	public void setUserCards(List<CardDTO> userCards) {

	}

	@Override
	public void deleteAllData() {
		GNBCipheredDatabase.deleteAllData(context);
	}
}
