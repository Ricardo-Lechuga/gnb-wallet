package es.ujaen.rlc00008.gnbwallet.ui.views;

import android.view.View;
import android.widget.ImageView;

import com.google.common.base.Preconditions;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.ui.utils.BranchImages;
import es.ujaen.rlc00008.gnbwallet.ui.utils.CardImages;

/**
 * Created by Ricardo on 4/6/16.
 */
public class CardView {

	public static final void paint(View cardView, Card card) {
		Preconditions.checkNotNull(cardView);
		Preconditions.checkNotNull(card);

		ImageView cardImageView = (ImageView) cardView.findViewById(R.id.card_imageview);

		cardImageView.setImageResource(CardImages.getResId(card));
	}
}
