package es.ujaen.rlc00008.gnbwallet.domain.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.common.base.Preconditions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.data.entities.CardDTO;
import es.ujaen.rlc00008.gnbwallet.domain.model.factories.GNBLocale;

/**
 * Created by Ricardo on 3/6/16.
 */
public abstract class Card implements Parcelable {

	protected final CardDTO cardDTO;
	protected final boolean isFavorite;
	protected String type;

	public Card(CardDTO cardDTO, boolean isFavorite) {
		Preconditions.checkNotNull(cardDTO);
		this.cardDTO = cardDTO;
		this.isFavorite = isFavorite;
		this.type = cardDTO.getType();
	}

	public CardDTO getCardDTO() {
		return cardDTO;
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

	public boolean isFavorite() {
		return isFavorite;
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

	@Nullable
	public Calendar getExpirationDateCalendar() {
		Calendar expirationDateCalendar = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-yy", GNBLocale.get());
		try {
			Date expirationDate = sdf.parse(cardDTO.getExpirationDate());
			expirationDateCalendar = Calendar.getInstance(GNBLocale.get());
			expirationDateCalendar.setTime(expirationDate);
			expirationDateCalendar.set(Calendar.DAY_OF_MONTH, expirationDateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			MyLog.printStackTrace(e);
		}
		return expirationDateCalendar;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Card other = (Card) o;
		return Objects.equals(cardDTO, other.cardDTO);
	}
}
