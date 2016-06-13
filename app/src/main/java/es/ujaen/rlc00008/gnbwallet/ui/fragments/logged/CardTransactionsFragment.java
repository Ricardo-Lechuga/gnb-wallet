package es.ujaen.rlc00008.gnbwallet.ui.fragments.logged;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.GetTransactionsInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.domain.model.CardTransaction;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.views.CardView;

/**
 * Created by Ricardo on 13/6/16.
 */
public class CardTransactionsFragment extends BaseFragment {

	public interface CardTransactionsListener {
		void cardTransactionsClose();
	}

	private CardTransactionsListener callback;

	private Card card;

	@BindView(R.id.card_small_view) View smallView;

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

		CardView.paintSmallView(smallView, card);

		//TODO!
		showLoading();
		getTransactionsInteractor.getTransactions(card, new GetTransactionsInteractor.GetTransactionsCallback() {
			@Override
			public void transactionsResponse(List<CardTransaction> cardTransactions) {
				hideLoading();
				Toast.makeText(getActivity(), "OK! Size: " + cardTransactions.size(), Toast.LENGTH_SHORT).show();
			}

			@Override
			public void operativeError(String message) {
				hideLoading();
				showErrorFragment(message);
			}
		});
	}
}
