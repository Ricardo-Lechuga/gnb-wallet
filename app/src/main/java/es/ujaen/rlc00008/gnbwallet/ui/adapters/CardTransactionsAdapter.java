package es.ujaen.rlc00008.gnbwallet.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items.TransactionItem;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.viewholders.CardTransactionViewHolder;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.viewholders.EmptyViewHolder;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.viewholders.EndListViewHolder;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseViewHolder;

/**
 * Created by Ricardo on 14/6/16.
 */
public class CardTransactionsAdapter extends MultiRecyclerViewAdapter {

	private BaseActivity context;

	public CardTransactionsAdapter(BaseActivity context) {
		this.context = context;
	}

	@Override
	protected BaseViewHolder onCreateTransactionViewHolder(ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.view_transaction, parent, false);
		return new CardTransactionViewHolder(context, view);
	}

	@Override
	protected void onBindTransactionViewHolder(BaseViewHolder holder, TransactionItem transactionItem, int position) {
		CardTransactionViewHolder viewHolder = (CardTransactionViewHolder) holder;
		viewHolder.build(transactionItem.getCardTransaction());
	}

	@Override
	protected BaseViewHolder onCreateEndListViewHolder(ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.view_transactions_end_list, parent, false);
		return new EndListViewHolder(context, view);
	}

	@Override
	protected void onBindEndListViewHolder(BaseViewHolder holder, int position) {
		EndListViewHolder viewHolder = (EndListViewHolder) holder;
		viewHolder.build();
	}

	@Override
	protected BaseViewHolder onCreateEmptyHolder(ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.view_transactions_empty, parent, false);
		return new EmptyViewHolder(context, view);
	}

	@Override
	protected void onBindEmptyHolder(BaseViewHolder holder, int position) {
		EmptyViewHolder viewHolder = (EmptyViewHolder) holder;
		viewHolder.build();
	}
}
