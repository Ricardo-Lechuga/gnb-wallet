package es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items;

import es.ujaen.rlc00008.gnbwallet.domain.model.CardTransaction;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.RecyclerViewItem;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.RecyclerViewType;

/**
 * Created by Ricardo on 14/6/16.
 */
public class TransactionItem implements RecyclerViewItem {

	private final CardTransaction cardTransaction;

	public TransactionItem(CardTransaction cardTransaction) {
		this.cardTransaction = cardTransaction;
	}

	public CardTransaction getCardTransaction() {
		return cardTransaction;
	}

	@Override
	public RecyclerViewType getRecyclerViewType() {
		return RecyclerViewType.TRANSACTION;
	}
}
