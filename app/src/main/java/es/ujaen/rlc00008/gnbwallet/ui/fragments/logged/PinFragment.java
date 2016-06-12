package es.ujaen.rlc00008.gnbwallet.ui.fragments.logged;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.domain.model.CreditCard;
import es.ujaen.rlc00008.gnbwallet.domain.model.DebitCard;
import es.ujaen.rlc00008.gnbwallet.domain.model.PrepaidCard;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.utils.AsyncTaskExecutionHelper;
import es.ujaen.rlc00008.gnbwallet.ui.utils.BranchImages;
import es.ujaen.rlc00008.gnbwallet.ui.views.CardView;

/**
 * Created by Ricardo on 12/6/16.
 */
public class PinFragment extends BaseFragment {

	public interface PinListener {

		void pinSeeDetail();

		void pinSeeCCV();

		void pinClose();

		void pinSeeAgain();
	}

	private PinListener callback;

	private Card card;
	private String pin;

	private boolean firstLoad;

	@BindView(R.id.toolbar_logout_imageview) ImageView logoutImageView;
	@BindView(R.id.pin_card_layout) View pinCardLayout;
	@BindView(R.id.pin_card_alias_textview) TextView aliasTextView;
	@BindView(R.id.pin_card_branch_imageview) ImageView branchImageView;
	@BindView(R.id.pin_debit_textview) TextView debitTextView;
	@BindView(R.id.pin_credit_balance_imageview) ImageView creditBalanceImageView;
	@BindView(R.id.pin_prepaid_view) View prepaidView;
	@BindView(R.id.pin_prepaid_textview) TextView prepaidTextView;
	@BindView(R.id.pin_info_textview) TextView pinInfoTextView;
	@BindView(R.id.pin_timer_textview) TextView timerTextView;
	@BindView(R.id.pin_buttons_view) View buttonsView;

	@OnClick(R.id.pin_detail_textview)
	void detailClick() {
		callback.pinSeeDetail();
	}

	@OnClick(R.id.pin_ccv_textview)
	void ccvClick() {
		callback.pinSeeCCV();
	}

	@OnClick(R.id.pin_close_button)
	void closeClick() {
		callback.pinClose();
	}

	@OnClick(R.id.pin_see_again_button)
	void seeAgainClick() {
		callback.pinSeeAgain();
	}

	public static PinFragment newInstance(Card card, String pin) {
		PinFragment fragment = new PinFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("card", card);
		bundle.putString("pin", pin);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		try {
			callback = (PinListener) context;
		} catch (ClassCastException e) {
			throw new RuntimeException(context + " must implement PinListener!");
		}
		super.onAttach(context);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		card = getArguments().getParcelable("card");
		pin = getArguments().getString("pin");
		if (savedInstanceState != null) {
			firstLoad = false;
		} else {
			firstLoad = true;
		}
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_pin;
	}

	@Override
	protected void prepareInterface(View mainView) {

		CardView.paint(pinCardLayout, card);

		aliasTextView.setText(card.getAlias());

		branchImageView.setImageResource(BranchImages.getResId(card));

		if (card instanceof DebitCard) {
			setDebitCardView((DebitCard) card);
		} else if (card instanceof CreditCard) {
			setCreditCardView((CreditCard) card);
		} else if (card instanceof PrepaidCard) {
			setPrepaidCardView((PrepaidCard) card);
		}

		if (firstLoad) {
			showPin();
		} else {
			hidePin();
		}
	}

	private void setDebitCardView(DebitCard debitCard) {
		creditBalanceImageView.setVisibility(View.GONE);
		prepaidView.setVisibility(View.GONE);
		debitTextView.setVisibility(View.VISIBLE);
	}

	private void setCreditCardView(CreditCard creditCard) {
		debitTextView.setVisibility(View.GONE);
		prepaidView.setVisibility(View.GONE);

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
	}

	private void setPrepaidCardView(PrepaidCard prepaidCard) {
		debitTextView.setVisibility(View.GONE);
		creditBalanceImageView.setVisibility(View.GONE);

		prepaidTextView.setText(prepaidCard.getBalance().getAmountFormatted());

		prepaidView.setVisibility(View.VISIBLE);
	}

	private void hidePin() {
		pinInfoTextView.setText(R.string.pin_asterisk);
		timerTextView.setText(R.string.timer_0_sec);

		// Prepare the View for the animation
		buttonsView.setVisibility(View.VISIBLE);
		buttonsView.setAlpha(0.0f);

		// Start the animation
		buttonsView.animate()
				.translationY(buttonsView.getHeight())
				.setDuration(2000)
				.alpha(1.0f);
	}

	private void showPin() {
		pinInfoTextView.setText(pin);
		TimerTask timerTask = new TimerTask();
		AsyncTaskExecutionHelper.executeParallel(timerTask);
	}

	private class TimerTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			try {
				for (int i = 5; i >= 0; i--) {
					publishProgress(i);
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				MyLog.printStackTrace(e);
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			if (PinFragment.this.isAdded()) {

				final String timerText;

				switch (values[0]) {
					case 5:
						timerText = getString(R.string.timer_5_sec);
						break;
					case 4:
						timerText = getString(R.string.timer_4_sec);
						break;
					case 3:
						timerText = getString(R.string.timer_3_sec);
						break;
					case 2:
						timerText = getString(R.string.timer_2_sec);
						break;
					case 1:
						timerText = getString(R.string.timer_1_sec);
						break;
					default:
						timerText = getString(R.string.timer_0_sec);
				}

				timerTextView.setText(timerText);
			}
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			if (PinFragment.this.isAdded()) {
				hidePin();
			}
		}
	}
}
