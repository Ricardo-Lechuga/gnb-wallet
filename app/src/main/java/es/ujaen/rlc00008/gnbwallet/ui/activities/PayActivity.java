package es.ujaen.rlc00008.gnbwallet.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import es.ujaen.rlc00008.gnbwallet.MyLog;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.data.entities.AmountDTO;
import es.ujaen.rlc00008.gnbwallet.domain.model.Amount;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.domain.model.CreditCard;
import es.ujaen.rlc00008.gnbwallet.domain.model.DebitCard;
import es.ujaen.rlc00008.gnbwallet.domain.model.PrepaidCard;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.pagers.CardsPagerAdapter;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.utils.BranchImages;
import es.ujaen.rlc00008.gnbwallet.ui.utils.MyCalendar;
import es.ujaen.rlc00008.gnbwallet.ui.views.ZoomOutPageTransformer;
import es.ujaen.rlc00008.transactions_library.NFCTransaction;
import es.ujaen.rlc00008.transactions_library.NFCTransactionUtils;

/**
 * Created by Ricardo on 24/6/16.
 */
public class PayActivity extends BaseActivity {

	private CardsPagerAdapter mAdapter;

	@BindView(R.id.pay_viewpager) ViewPager viewPager;
	@BindView(R.id.pay_alias_textview) TextView aliasTextView;
	@BindView(R.id.pay_branch_imageview) ImageView branchImageView;
	@BindView(R.id.pay_debit_textview) TextView debitTextView;
	@BindView(R.id.pay_credit_balance_imageview) ImageView creditBalanceImageView;
	@BindView(R.id.pay_prepaid_view) View prepaidView;
	@BindView(R.id.pay_prepaid_textview) TextView prepaidTextView;
	@BindView(R.id.pay_operation_date_textview) TextView operationDateTextView;
	@BindView(R.id.pay_commerce_name_textview) TextView commerceNameTextView;
	@BindView(R.id.pay_concept_textview) TextView conceptTextView;
	@BindView(R.id.pay_amount_textview) TextView amountTextView;

	public static void startActivity(Context context) {
		Intent intent = new Intent(context, PayActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected int getContentView() {
		return R.layout.activity_pay;
	}

	@Override
	protected void prepareInterface() {

		List<Card> cards = loggedDataInteractor.getCards();

		mAdapter = new CardsPagerAdapter(cards, getSupportFragmentManager());
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

		viewPager.setOffscreenPageLimit(2);
		int margin = (int) getResources().getDimension(R.dimen.cards_view_box_padding) * 2;
		viewPager.setPageMargin(-margin);

		viewPager.setAdapter(mAdapter);
		viewPager.setCurrentItem(0, false);
		selectCard(cards.get(0));
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				//
			}

			@Override
			public void onPageSelected(int position) {
				//
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				//
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		super.onNewIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
			processIntent(getIntent());
		}
	}

	private void processIntent(Intent intent) {

		if ((intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0) {
			return;
		}

		Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
				NfcAdapter.EXTRA_NDEF_MESSAGES);
		// only one message sent during the beam
		NdefMessage msg = (NdefMessage) rawMsgs[0];
		// record 0 contains the MIME type, record 1 is the AAR, if present
		NFCTransaction nfcTransaction = NFCTransactionUtils.deserialize(msg.getRecords()[0].getPayload());
		paintTransaction(nfcTransaction);
	}

	private void selectCard(Card card) {
		aliasTextView.setText(card.getAlias());

		branchImageView.setImageResource(BranchImages.getResId(card));

		if (card instanceof DebitCard) {
			setDebitCardView((DebitCard) card);
		} else if (card instanceof CreditCard) {
			setCreditCardView((CreditCard) card);
		} else if (card instanceof PrepaidCard) {
			setPrepaidCardView((PrepaidCard) card);
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

	private void paintTransaction(NFCTransaction nfcTransaction) {

		try {
			Date transactionDate = NFCTransaction.OPERATION_DATE_FORMAT.parse(nfcTransaction.getOperationDate());
			String operationDateString = MyCalendar.getDateTimeFormatted(transactionDate);
			operationDateTextView.setText(operationDateString);
		} catch (ParseException e) {
			MyLog.printStackTrace(e);
			operationDateTextView.setText("");
		}

		commerceNameTextView.setText(nfcTransaction.getCommerceName());

		conceptTextView.setText(nfcTransaction.getConcept());

		Amount transactionAmount = new Amount(new AmountDTO(nfcTransaction.getAmount(), nfcTransaction.getCurrency()));
		amountTextView.setText(transactionAmount.getAmountFormatted());
	}
}
