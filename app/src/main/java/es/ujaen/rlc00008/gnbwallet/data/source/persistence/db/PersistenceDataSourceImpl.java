package es.ujaen.rlc00008.gnbwallet.data.source.persistence.db;

import android.content.Context;

import java.util.List;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.data.entities.UserDTO;
import es.ujaen.rlc00008.gnbwallet.data.source.persistence.PersistenceDataSource;

/**
 * Created by Ricardo on 22/5/16.
 */
public class PersistenceDataSourceImpl implements PersistenceDataSource {

	private GNBCipheredDatabase gnbCipheredDatabase;

	public PersistenceDataSourceImpl(Context context) {
		gnbCipheredDatabase = new GNBCipheredDatabase(context);
	}

	@Override
	public UserDTO getStoredUser() {
		return null;
	}

	@Override
	public void cleanStoredUser() {

	}

	@Override
	public void setStoredUser(UserDTO userDTO) {

	}

	@Override
	public List<CardDTO> getUserCards(String userId) {
		return null;
	}

	@Override
	public void setUserCards(List<CardDTO> userCards) {

	}

	@Override
	public void deleteAllData() {

	}
}
