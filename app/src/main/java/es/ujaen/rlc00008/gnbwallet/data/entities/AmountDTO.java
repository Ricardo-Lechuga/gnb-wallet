package es.ujaen.rlc00008.gnbwallet.data.entities;

import java.math.BigDecimal;

/**
 * Created by Ricardo Lechuga on 16/5/16.
 */
public class AmountDTO {

	private BigDecimal amount;
	private String currency;

	public AmountDTO() {
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
