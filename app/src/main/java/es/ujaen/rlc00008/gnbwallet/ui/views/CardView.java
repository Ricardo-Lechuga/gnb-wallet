package es.ujaen.rlc00008.gnbwallet.ui.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.domain.model.CreditCard;
import es.ujaen.rlc00008.gnbwallet.domain.model.DebitCard;
import es.ujaen.rlc00008.gnbwallet.domain.model.PrepaidCard;
import es.ujaen.rlc00008.gnbwallet.ui.utils.CardImages;

/**
 * Created by Ricardo on 4/6/16.
 */
public class CardView {

	public static void paint(View cardView, Card card) {
		Preconditions.checkNotNull(cardView);
		Preconditions.checkNotNull(card);

		ImageView cardImageView = (ImageView) cardView.findViewById(R.id.card_imageview);

		cardImageView.setImageResource(CardImages.getResId(card));
	}

	public static void paintSmallView(View smallView, Card card) {
		Preconditions.checkNotNull(smallView);
		Preconditions.checkNotNull(card);

		ImageView cardImageView = (ImageView) smallView.findViewById(R.id.card_imageview);
		TextView aliasTextView = (TextView) smallView.findViewById(R.id.card_alias_textview);
		TextView panTextView = (TextView) smallView.findViewById(R.id.card_pan_textview);
		ImageView creditBalanceImageView = (ImageView) smallView.findViewById(R.id.card_credit_balance_imageview);
		TextView prepaidTextView = (TextView) smallView.findViewById(R.id.card_prepaid_textview);
		TextView debitTextView = (TextView) smallView.findViewById(R.id.card_debit_textview);

		cardImageView.setImageResource(CardImages.getResId(card));

		aliasTextView.setText(card.getAlias());

		panTextView.setText(card.getFormattedPan());

		if (card instanceof DebitCard) {
			creditBalanceImageView.setVisibility(View.GONE);
			prepaidTextView.setVisibility(View.GONE);
			debitTextView.setVisibility(View.VISIBLE);
		} else if (card instanceof CreditCard) {
			debitTextView.setVisibility(View.GONE);
			prepaidTextView.setVisibility(View.GONE);

			CreditCard creditCard = (CreditCard) card;

			double ratio = creditCard.getCurrentBalance().getAmountValue() / creditCard.getCreditLimit().getAmountValue();

			final int imageResId;

			if (ratio < 0.2) {
				imageResId = R.drawable.bground_progress_0;
			} else if (ratio < 0.4) {
				imageResId = R.drawable.bground_progress_1;
			} else if (ratio < 0.6) {
				imageResId = R.drawable.bground_progress_2;
			} else if (ratio < 0.8) {
				imageResId = R.drawable.bground_progress_3;
			} else {
				imageResId = R.drawable.bground_progress_4;
			}

			creditBalanceImageView.setImageResource(imageResId);

			creditBalanceImageView.setVisibility(View.VISIBLE);
		} else if (card instanceof PrepaidCard) {
			debitTextView.setVisibility(View.GONE);
			creditBalanceImageView.setVisibility(View.GONE);

			PrepaidCard prepaidCard = (PrepaidCard) card;

			prepaidTextView.setText(prepaidCard.getBalance().getAmountFormatted());

			prepaidTextView.setVisibility(View.VISIBLE);
		}
	}
}
