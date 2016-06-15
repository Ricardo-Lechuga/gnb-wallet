package es.ujaen.rlc00008.gnbwallet.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.RecyclerViewItem;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.RecyclerViewType;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items.StringPairItem;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items.TransactionItem;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseViewHolder;

/**
 * Created by Mobilers on 26/1/16. Modified by Toni on 08/5/16
 */
public abstract class MultiRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

	protected List<RecyclerViewItem> items = new ArrayList<>();

	public void set(Collection<? extends RecyclerViewItem> items) {
		this.items.clear();

		this.items.addAll(items);

		notifyDataSetChanged();
	}

	public void add(RecyclerViewItem item) {
		this.items.add(item);

		notifyDataSetChanged();
	}

	public void add(int position, RecyclerViewItem item) {
		this.items.add(position, item);

		notifyItemInserted(position);
	}

	public void remove(RecyclerViewItem item) {
		int position = this.items.indexOf(item);

		this.items.remove(position);

		notifyItemRemoved(position);
	}

	public void remove(int position) {
		this.items.remove(position);

		notifyItemRemoved(position);
	}

	public void clean() {
		this.items.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return items != null ? items.size() : 0;
	}

	@Override
	public int getItemViewType(int position) {
		return items.get(position).getRecyclerViewType().getValue();
	}

	@Override
	public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		final BaseViewHolder baseViewHolder;

		switch (RecyclerViewType.findByValue(viewType)) {

			case TRANSACTION:
				baseViewHolder = onCreateTransactionViewHolder(parent);
				break;
			case END_LIST:
				baseViewHolder = onCreateEndListViewHolder(parent);
				break;
			case EMPTY:
				baseViewHolder = onCreateEmptyHolder(parent);
				break;
			case STRING_PAIR:
				baseViewHolder = onCreateStringPairHolder(parent);
				break;
			default:
				throw new RuntimeException("value: " + viewType + " not supported in MultiRecyclerViewAdapter!");
		}

		return baseViewHolder;
	}

	@Override
	public final void onBindViewHolder(BaseViewHolder holder, int position) {

		RecyclerViewType type = items.get(position).getRecyclerViewType();

		switch (type) {

			case TRANSACTION:
				onBindTransactionViewHolder(holder, (TransactionItem) items.get(position), position);
				break;
			case END_LIST:
				onBindEndListViewHolder(holder, position);
				break;
			case EMPTY:
				onBindEmptyHolder(holder, position);
				break;
			case STRING_PAIR:
				onBindStringPairHolder(holder, (StringPairItem) items.get(position), position);
				break;
		}
	}

	protected BaseViewHolder onCreateTransactionViewHolder(ViewGroup parent) {
		throw new RuntimeException("RecyclerViewType.TRANSACTION not supported in current adapter! Did you forget to override onCreateTransactionViewHolder()?");
	}

	protected void onBindTransactionViewHolder(BaseViewHolder holder, TransactionItem transactionItem, int position) {
		throw new RuntimeException("RecyclerViewType.TRANSACTION not supported in current adapter! Did you forget to override onBindTransactionViewHolder()?");
	}

	protected BaseViewHolder onCreateEndListViewHolder(ViewGroup parent) {
		throw new RuntimeException("RecyclerViewType.END_LIST not supported in current adapter! Did you forget to override onCreateEndListViewHolder()?");
	}

	protected void onBindEndListViewHolder(BaseViewHolder holder, int position) {
		throw new RuntimeException("RecyclerViewType.END_LIST not supported in current adapter! Did you forget to override onBindEndListViewHolder()?");
	}

	protected BaseViewHolder onCreateEmptyHolder(ViewGroup parent) {
		throw new RuntimeException("RecyclerViewType.EMPTY not supported in current adapter! Did you forget to override onCreateEmptyHolder()?");
	}

	protected void onBindEmptyHolder(BaseViewHolder holder, int position) {
		throw new RuntimeException("RecyclerViewType.EMPTY not supported in current adapter! Did you forget to override onBindEmptyHolder()?");
	}

	protected BaseViewHolder onCreateStringPairHolder(ViewGroup parent) {
		throw new RuntimeException("RecyclerViewType.STRING_PAIR not supported in current adapter! Did you forget to override onCreateStringPairHolder()?");
	}

	protected void onBindStringPairHolder(BaseViewHolder holder, StringPairItem stringPairItem, int position) {
		throw new RuntimeException("RecyclerViewType.STRING_PAIR not supported in current adapter! Did you forget to override onBindStringPairHolder()?");
	}
}
