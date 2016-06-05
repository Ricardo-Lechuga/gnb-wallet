package es.ujaen.rlc00008.gnbwallet.ui.fragments.logged;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.common.base.Preconditions;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.views.CardView;

/**
 * Created by Ricardo on 22/6/15.
 */
public class CardPageFragment extends BaseFragment {

	private Card mCard;
	private View mView;

	public static CardPageFragment newInstance(Card card) {
		Preconditions.checkNotNull(card);
		CardPageFragment fragment = new CardPageFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("card", card);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCard = getArguments().getParcelable("card");
	}

	@Override
	protected int getContentView() {
		return R.layout.view_card_pager;
	}

	@Override
	protected void prepareInterface(View mainView) {
		CardView.paint(mainView, mCard);
	}
}
