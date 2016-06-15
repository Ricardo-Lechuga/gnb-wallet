package es.ujaen.rlc00008.gnbwallet.ui.fragments.logged;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.domain.model.CreditCard;
import es.ujaen.rlc00008.gnbwallet.domain.model.DebitCard;
import es.ujaen.rlc00008.gnbwallet.domain.model.PrepaidCard;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.DetailItemAdapter;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.RecyclerViewItem;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items.StringPairItem;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.utils.MyCalendar;
import es.ujaen.rlc00008.gnbwallet.ui.utils.TextUtils;
import es.ujaen.rlc00008.gnbwallet.ui.views.CardView;

/**
 * Created by Ricardo on 15/6/16.
 */
public class CardDetailFragment extends BaseFragment {

	public interface CardDetailListener {
		void cardDetailClose();

		void cardDetailLogout();
	}

	private CardDetailListener callback;

	private Card card;

	private DetailItemAdapter adapter;

	@BindView(R.id.toolbar_logout_imageview) ImageView logoutImageView;
	@BindView(R.id.toolbar_back_imageview) ImageView backImageView;
	@BindView(R.id.card_small_view) View smallView;
	@BindView(R.id.card_detail_recyclerview) RecyclerView recyclerView;

	public static CardDetailFragment newInstance(Card card) {
		CardDetailFragment fragment = new CardDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("card", card);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		try {
			callback = (CardDetailListener) context;
		} catch (ClassCastException e) {
			throw new RuntimeException(context + " must implement CardDetailListener!");
		}
		super.onAttach(context);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		card = getArguments().getParcelable("card");
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_card_detail;
	}

	@Override
	protected void prepareInterface(View mainView) {

		logoutImageView.setVisibility(View.VISIBLE);
		backImageView.setVisibility(View.VISIBLE);

		CardView.paintSmallView(smallView, card);

		adapter = new DetailItemAdapter((BaseActivity) getActivity());
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(adapter);

		setAdapterItems();
	}

	private void setAdapterItems() {
		List<RecyclerViewItem> items = new ArrayList<>();

		String expirationDateKey = getString(R.string.card_detail_expiration_date);
		String expirationDateValue = MyCalendar.getCalendarFormatted(card.getExpirationDateCalendar());
		items.add(new StringPairItem(expirationDateKey, expirationDateValue));

		String beneficiaryKey = getString(R.string.card_detail_beneficiary);
		String beneficiaryValue = TextUtils.toAliasCase(card.getBeneficiary());
		items.add(new StringPairItem(beneficiaryKey, beneficiaryValue));

		String statusKey = getString(R.string.card_detail_status);
		String statusValue = card.isEnabled() ? getString(R.string.card_detail_status_activated) : getString(R.string.card_detail_status_deactivated);
		items.add(new StringPairItem(statusKey, statusValue));

		String nfcKey = getString(R.string.card_detail_nfc);
		String nfcValue = card.isNfc() ? getString(R.string._yes) : getString(R.string._no);
		items.add(new StringPairItem(nfcKey, nfcValue));

		String typeKey = getString(R.string.card_detail_type);

		if (card instanceof DebitCard) {

			String typeValue = getString(R.string._debit);
			items.add(new StringPairItem(typeKey, typeValue));
		} else if (card instanceof CreditCard) {

			CreditCard creditCard = (CreditCard) card;

			String typeValue = getString(R.string._credit);
			items.add(new StringPairItem(typeKey, typeValue));

			String currentBalanceKey = getString(R.string.card_detail_current_balance);
			String currentBalanceValue = creditCard.getCurrentBalance().getAmountFormatted();
			items.add(new StringPairItem(currentBalanceKey, currentBalanceValue));

			String availableKey = getString(R.string.card_detail_available_amount);
			String availableValue = creditCard.getAvailableCredit().getAmountFormatted();
			items.add(new StringPairItem(availableKey, availableValue));

			String creditLimitKey = getString(R.string.card_detail_credit_limit);
			String creditLimitValue = creditCard.getCreditLimit().getAmountFormatted();
			items.add(new StringPairItem(creditLimitKey, creditLimitValue));
		} else if (card instanceof PrepaidCard) {

			PrepaidCard prepaidCard = (PrepaidCard) card;

			String typeValue = getString(R.string._prepaid);
			items.add(new StringPairItem(typeKey, typeValue));

			String balanceKey = getString(R.string.card_detail_balance);
			String balanceValue = prepaidCard.getBalance().getAmountFormatted();
			items.add(new StringPairItem(balanceKey, balanceValue));
		}

		//@BindView(R.id.card_detail_current_balance_layout) View currentBalanceLayout;
		//@BindView(R.id.card_detail_available_amount_layout) View availableAmountLayout;
		//@BindView(R.id.card_detail_credit_limit_layout) View creditLimitLayout;

		adapter.set(items);
	}

	@OnClick(R.id.toolbar_back_imageview)
	void backClick() {
		callback.cardDetailClose();
	}

	@OnClick(R.id.toolbar_logout_imageview)
	void logoutClick() {
		callback.cardDetailLogout();
	}
}
