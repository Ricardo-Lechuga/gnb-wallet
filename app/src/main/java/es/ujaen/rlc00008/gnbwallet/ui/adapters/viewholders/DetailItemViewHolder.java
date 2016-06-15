package es.ujaen.rlc00008.gnbwallet.ui.adapters.viewholders;

import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items.StringPairItem;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseViewHolder;

/**
 * Created by Ricardo on 15/6/16.
 */
public class DetailItemViewHolder extends BaseViewHolder {

	@BindView(R.id.detail_item_label) TextView keyTextView;
	@BindView(R.id.detail_item_textview) TextView valueTextView;

	public DetailItemViewHolder(BaseActivity context, View itemView) {
		super(context, itemView);
	}

	public void build(StringPairItem stringPairItem) {

		keyTextView.setText(stringPairItem.getKey());
		valueTextView.setText(stringPairItem.getValue());
	}
}
