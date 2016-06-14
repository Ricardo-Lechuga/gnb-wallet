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
import es.ujaen.rlc00008.gnbwallet.domain.interactors.GetTransactionsInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.domain.model.CardTransaction;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.CardTransactionsAdapter;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.RecyclerViewItem;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items.EmptyItem;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items.EndListItem;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.types.items.TransactionItem;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.views.CardView;

/**
 * Created by Ricardo on 13/6/16.
 */
public class CardTransactionsFragment extends BaseFragment {

	public interface CardTransactionsListener {
		void cardTransactionsClose();

		void cardTransactionsLogout();
	}

	private CardTransactionsListener callback;

	private Card card;

	private CardTransactionsAdapter adapter;

	@BindView(R.id.toolbar_logout_imageview) ImageView logoutImageView;
	@BindView(R.id.toolbar_back_imageview) ImageView backImageView;
	@BindView(R.id.card_small_view) View smallView;
	@BindView(R.id.card_transactions_recyclerview) RecyclerView recyclerView;

	public static CardTransactionsFragment newInstance(Card card) {
		CardTransactionsFragment fragment = new CardTransactionsFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("card", card);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		try {
			callback = (CardTransactionsListener) context;
		} catch (ClassCastException e) {
			throw new RuntimeException(context + " must implement CardTransactionsListener!");
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
		return R.layout.fragment_card_transactions;
	}

	@Override
	protected void prepareInterface(View mainView) {

		logoutImageView.setVisibility(View.VISIBLE);
		backImageView.setVisibility(View.VISIBLE);

		CardView.paintSmallView(smallView, card);

		adapter = new CardTransactionsAdapter((BaseActivity) getActivity());
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
		recyclerView.setAdapter(adapter);

		showLoading();
		getTransactionsInteractor.getTransactions(card, new GetTransactionsInteractor.GetTransactionsCallback() {
			@Override
			public void transactionsResponse(List<CardTransaction> cardTransactions) {
				setTransactions(cardTransactions);
				hideLoading();
			}

			@Override
			public void operativeError(String message) {
				setTransactions(null);
				hideLoading();
				showErrorFragment(message);
			}
		});
	}

	private void setTransactions(List<CardTransaction> cardTransactions) {

		List<RecyclerViewItem> items = new ArrayList<>();
		if (cardTransactions == null || cardTransactions.isEmpty()) {
			items.add(new EmptyItem());
		} else {
			for (CardTransaction cardTransaction : cardTransactions) {
				items.add(new TransactionItem(cardTransaction));
			}
			items.add(new EndListItem());
		}

		adapter.set(items);
	}

	@OnClick(R.id.toolbar_back_imageview)
	void backClick() {
		callback.cardTransactionsClose();
	}

	@OnClick(R.id.toolbar_logout_imageview)
	void logoutClick() {
		callback.cardTransactionsLogout();
	}
}
