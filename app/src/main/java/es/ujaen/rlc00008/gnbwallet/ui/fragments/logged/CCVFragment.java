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
public class CCVFragment extends BaseFragment {

	public interface CCVListener {

		void ccvSeeDetail();

		void ccvSeePin();

		void ccvClose();

		void ccvSeeAgain();
	}

	private CCVListener callback;

	private Card card;
	private String ccv;

	private boolean firstLoad;

	@BindView(R.id.toolbar_logout_imageview) ImageView logoutImageView;
	@BindView(R.id.ccv_card_layout) View ccvCardLayout;
	@BindView(R.id.ccv_card_alias_textview) TextView aliasTextView;
	@BindView(R.id.ccv_card_branch_imageview) ImageView branchImageView;
	@BindView(R.id.ccv_debit_textview) TextView debitTextView;
	@BindView(R.id.ccv_credit_balance_imageview) ImageView creditBalanceImageView;
	@BindView(R.id.ccv_prepaid_view) View prepaidView;
	@BindView(R.id.ccv_prepaid_textview) TextView prepaidTextView;
	@BindView(R.id.ccv_info_textview) TextView ccvInfoTextView;
	@BindView(R.id.ccv_timer_textview) TextView timerTextView;
	@BindView(R.id.ccv_buttons_view) View buttonsView;

	@OnClick(R.id.ccv_detail_textview)
	void detailClick() {
		callback.ccvSeeDetail();
	}

	@OnClick(R.id.ccv_pin_textview)
	void ccvClick() {
		callback.ccvSeePin();
	}

	@OnClick(R.id.ccv_close_button)
	void closeClick() {
		callback.ccvClose();
	}

	@OnClick(R.id.ccv_see_again_button)
	void seeAgainClick() {
		callback.ccvSeeAgain();
	}

	public static CCVFragment newInstance(Card card, String ccv) {
		CCVFragment fragment = new CCVFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("card", card);
		bundle.putString("ccv", ccv);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		try {
			callback = (CCVListener) context;
		} catch (ClassCastException e) {
			throw new RuntimeException(context + " must implement CCVListener!");
		}
		super.onAttach(context);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		card = getArguments().getParcelable("card");
		ccv = getArguments().getString("ccv");
		if (savedInstanceState != null) {
			firstLoad = false;
		} else {
			firstLoad = true;
		}
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_ccv;
	}

	@Override
	protected void prepareInterface(View mainView) {

		CardView.paint(ccvCardLayout, card);

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
			showCCV();
		} else {
			hideCCV();
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

	private void hideCCV() {
		ccvInfoTextView.setText(R.string.ccv_asterisk);
		timerTextView.setText(R.string.timer_0_sec);

		// Prepare the View for the animation
		buttonsView.setVisibility(View.VISIBLE);
		buttonsView.setAlpha(0.0f);

		// Start the animation
		buttonsView.animate()
				.translationY(buttonsView.getHeight())
				.setDuration(1000)
				.alpha(1.0f);
	}

	private void showCCV() {
		ccvInfoTextView.setText(ccv);
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
			if (CCVFragment.this.isAdded()) {

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
			if (CCVFragment.this.isAdded()) {
				hideCCV();
			}
		}
	}
}
