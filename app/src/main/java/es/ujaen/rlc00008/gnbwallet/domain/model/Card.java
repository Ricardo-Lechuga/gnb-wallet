package es.ujaen.rlc00008.gnbwallet.domain.model;

import com.google.common.base.Preconditions;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;

/**
 * Created by Ricardo on 3/6/16.
 */
public abstract class Card {

	protected final CardDTO cardDTO;

	public Card(CardDTO cardDTO) {
		Preconditions.checkNotNull(cardDTO);
		this.cardDTO = cardDTO;
	}

	public String getPan() {
		return cardDTO.getPan();
	}

	public String getFormattedPan() {

		String unformattedPAN = cardDTO.getPan();

		return unformattedPAN.substring(0, 4) + " "
				+ unformattedPAN.substring(4, 8) + " "
				+ unformattedPAN.substring(8, 12) + " "
				+ unformattedPAN.substring(12, 16);
	}

	public boolean isNfc() {
		return cardDTO.isNfc();
	}

	public boolean isEnabled() {
		return cardDTO.isEnabled();
	}

	public String getExpirationDate() {
		return cardDTO.getExpirationDate();
	}

	public String getBeneficiary() {
		return cardDTO.getBeneficiary();
	}

	public String getVisualCode() {
		return cardDTO.getVisualCode();
	}
}
