package es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items;

import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.RecyclerViewItem;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.RecyclerViewType;

/**
 * Created by Ricardo on 14/6/16.
 */
public class StringPairItem implements RecyclerViewItem {

	private final String key;
	private final String value;

	public StringPairItem(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@Override
	public RecyclerViewType getRecyclerViewType() {
		return RecyclerViewType.STRING_PAIR;
	}
}
