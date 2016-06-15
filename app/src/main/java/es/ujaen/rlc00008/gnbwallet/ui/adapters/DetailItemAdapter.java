package es.ujaen.rlc00008.gnbwallet.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items.StringPairItem;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.viewholders.DetailItemViewHolder;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseViewHolder;

/**
 * Created by Ricardo on 14/6/16.
 */
public class DetailItemAdapter extends MultiRecyclerViewAdapter {

	private BaseActivity context;

	public DetailItemAdapter(BaseActivity context) {
		this.context = context;
	}

	@Override
	protected BaseViewHolder onCreateStringPairHolder(ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.view_detail_item, parent, false);
		return new DetailItemViewHolder(context, view);
	}

	@Override
	protected void onBindStringPairHolder(BaseViewHolder holder, StringPairItem stringPairItem, int position) {
		DetailItemViewHolder viewHolder = (DetailItemViewHolder) holder;
		viewHolder.build(stringPairItem);
	}
}
