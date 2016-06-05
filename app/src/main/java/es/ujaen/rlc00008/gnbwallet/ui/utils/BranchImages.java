package es.ujaen.rlc00008.gnbwallet.ui.utils;

import com.google.common.base.Preconditions;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;

/**
 * Created by Ricardo on 4/6/16.
 */
public class BranchImages {

	public static final int getResId(Card card) {
		Preconditions.checkNotNull(card);

		int resId = 0;

		if(card.getBrandType() == null) {
			return resId;
		}

		switch (card.getBrandType()) {
			case WISA:
				resId = R.drawable.logo_wisa;
				break;
			case YAM:
				resId = R.drawable.logo_yam;
				break;
		}

		return resId;
	}
}
