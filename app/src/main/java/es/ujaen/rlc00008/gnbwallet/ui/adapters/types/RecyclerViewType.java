package es.ujaen.rlc00008.gnbwallet.ui.adapters.types;

/**
 * Created by Ricardo on 18/2/16.
 */
public enum RecyclerViewType {

	/*
	NOTE: Add new items in MultiRecyclerViewAdapter
	 */

	TRANSACTION(0),
	END_LIST(1),
	EMPTY(2),
	STRING_PAIR(3);

	private final int value;

	RecyclerViewType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static RecyclerViewType findByValue(int value) {

		RecyclerViewType candidateType = null;

		for (RecyclerViewType type : RecyclerViewType.values()) {
			if (type.getValue() == value) {
				candidateType = type;
			}
		}

		return candidateType;
	}

}
