package es.ujaen.rlc00008.gnbwallet.data.source.net.responses;

import java.util.List;

import es.ujaen.rlc00008.gnbwallet.data.entities.CardTransactionDTO;

/**
 * Created by Ricardo on 5/6/16.
 */
public class CardTransactionsResponse {

	private boolean endList;
	private List<CardTransactionDTO> transactions;

	public CardTransactionsResponse() {
	}

	public boolean isEndList() {
		return endList;
	}

	public void setEndList(boolean endList) {
		this.endList = endList;
	}

	public List<CardTransactionDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<CardTransactionDTO> transactions) {
		this.transactions = transactions;
	}
}
