package es.ujaen.rlc00008.gnbwallet.domain.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.common.base.Preconditions;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;

/**
 * Created by Ricardo on 3/6/16.
 */
public abstract class Card implements Parcelable {

	protected final CardDTO cardDTO;
	protected String type;

	public Card(CardDTO cardDTO) {
		Preconditions.checkNotNull(cardDTO);
		this.cardDTO = cardDTO;
		this.type = cardDTO.getType();
	}

	public String getPan() {
		return cardDTO.getPan();
	}

	public String getAlias() {
		return cardDTO.getAlias();
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

	@Nullable
	public BrandType getBrandType() {

		BrandType brandType = null;

		switch (getPan().substring(0, 6)) {
			case "961255":
				brandType = BrandType.YAM;
				break;
			case "818222":
				brandType = BrandType.WISA;
				break;
			case "819222":
				brandType = BrandType.WISA;
				break;
			case "636619":
				brandType = BrandType.WISA;
				break;
		}

		return brandType;
	}
}
