package es.ujaen.rlc00008.gnbwallet.data.entities;

import java.util.Objects;

/**
 * Created by Ricardo on 16/5/16.
 */
public class CardDTO {

	public static final String TYPE_CREDIT = "credit";
	public static final String TYPE_DEBIT = "debit";
	public static final String TYPE_PREPAID = "prepaid";

	private String userId;
	private String pan;
	private boolean nfc;
	private boolean enabled;
	private String expirationDate;
	private String beneficiary;
	private String visualCode;
	private String type;
	private AmountDTO currentBalance;
	private AmountDTO availableCredit;
	private AmountDTO creditLimit;
	private AmountDTO balance;

	public CardDTO() {
	}

	public CardDTO(CardDTO cardDTO) {
		this.userId = cardDTO.userId;
		this.pan = cardDTO.pan;
		this.nfc = cardDTO.nfc;
		this.enabled = cardDTO.enabled;
		this.expirationDate = cardDTO.expirationDate;
		this.beneficiary = cardDTO.beneficiary;
		this.visualCode = cardDTO.visualCode;
		this.type = cardDTO.type;
		this.currentBalance = cardDTO.currentBalance;
		this.availableCredit = cardDTO.availableCredit;
		this.creditLimit = cardDTO.creditLimit;
		this.balance = cardDTO.balance;
	}

	public CardDTO(String userId, String pan, boolean nfc, boolean enabled, String beneficiary, String visualCode, String type) {
		this.userId = userId;
		this.pan = pan;
		this.nfc = nfc;
		this.enabled = enabled;
		this.expirationDate = null;
		this.beneficiary = beneficiary;
		this.visualCode = visualCode;
		this.type = type;
		this.currentBalance = null;
		this.availableCredit = null;
		this.creditLimit = null;
		this.balance = null;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public boolean isNfc() {
		return nfc;
	}

	public void setNfc(boolean nfc) {
		this.nfc = nfc;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public String getVisualCode() {
		return visualCode;
	}

	public void setVisualCode(String visualCode) {
		this.visualCode = visualCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AmountDTO getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(AmountDTO currentBalance) {
		this.currentBalance = currentBalance;
	}

	public AmountDTO getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(AmountDTO availableCredit) {
		this.availableCredit = availableCredit;
	}

	public AmountDTO getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(AmountDTO creditLimit) {
		this.creditLimit = creditLimit;
	}

	public AmountDTO getBalance() {
		return balance;
	}

	public void setBalance(AmountDTO balance) {
		this.balance = balance;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CardDTO other = (CardDTO) o;
		return Objects.equals(pan, other.pan);
	}
}
