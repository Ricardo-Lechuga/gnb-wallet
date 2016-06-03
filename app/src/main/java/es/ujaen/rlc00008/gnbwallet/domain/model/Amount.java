package es.ujaen.rlc00008.gnbwallet.domain.model;

import com.google.common.base.Preconditions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import es.ujaen.rlc00008.gnbwallet.data.entities.AmountDTO;

/**
 * Created by Ricardo on 3/6/16.
 */
public class Amount {

	private final AmountDTO amountDTO;

	public Amount(AmountDTO amountDTO) {
		Preconditions.checkNotNull(amountDTO);
		this.amountDTO = amountDTO;
	}

	public double getAmountValue() {
		return amountDTO.getAmount().doubleValue();
	}

	public String getAmountValueAsString() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		NumberFormat formatter = new DecimalFormat("###########0.00", symbols);
		return formatter.format(amountDTO.getAmount());
	}

	public String getCurrency() {
		return amountDTO.getCurrency();
	}

	public String getCurrencySymbol() {
		String currencyCode = amountDTO.getCurrency();

		final String currencySymbol;

		if("EUR".equalsIgnoreCase(currencyCode)) {
			currencySymbol = "€";
		} else if("USD".equalsIgnoreCase(currencyCode)) {
			currencySymbol = "$";
		} else if("GBP".equalsIgnoreCase(currencyCode)) {
			currencySymbol = "£";
		} else if(currencyCode != null){
			currencySymbol = currencyCode;
		} else {
			currencySymbol = "";
		}

		return currencySymbol;
	}

	public String getAmountFormatted() {
		return getAmountValueAsString() + getCurrencySymbol();
	}
}
