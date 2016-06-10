package es.ujaen.rlc00008.gnbwallet.ui.utils;

import com.google.common.base.Preconditions;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;

/**
 * Created by Ricardo on 4/6/16.
 */
public class CardImages {

	public static int getResId(Card card) {
		Preconditions.checkNotNull(card);

		int resId = 0;

		if(card.getVisualCode() == null){
			return resId;
		}

		switch (card.getVisualCode()) {
			case "1":
				resId = R.drawable.black_card;
				break;
			case "2":
				resId = R.drawable.black_green_card;
				break;
			case "3":
				resId = R.drawable.blue_card;
				break;
			case "4":
				resId = R.drawable.white_card;
				break;
		}

		return resId;
	}
}
