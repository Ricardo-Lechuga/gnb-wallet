package es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items;

import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.RecyclerViewItem;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.RecyclerViewType;

/**
 * Created by Ricardo on 14/6/16.
 */
public class EndListItem implements RecyclerViewItem {

	public EndListItem() {
	}

	@Override
	public RecyclerViewType getRecyclerViewType() {
		return RecyclerViewType.END_LIST;
	}
}
