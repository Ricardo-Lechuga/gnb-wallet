package es.ujaen.rlc00008.gnbwallet.ui.fragments.logged;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.pagers.CardsPagerAdapter;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.views.ZoomOutPageTransformer;

/**
 * Created by Ricardo on 4/6/16.
 */
public class HomeFragment extends BaseFragment {

	public interface HomeListener {

		void homeLogout();

		void homeDetailSelected(Card card);

		void homeSeeCCV(Card card);

		void homeSeePin(Card card);

		void homeActivateCard(Card card);

		void homeDeactivateCard(Card card);

		void homeTransactionsSelected(Card card);

		void homePay(Card card);
	}

	private HomeListener callback;

	private List<Card> cards;
	private int selectedIndex;

	private CardsPagerAdapter mAdapter;

	@BindView(R.id.toolbar_logout_imageview) ImageView logoutImageView;
	@BindView(R.id.home_cards_viewpager) ViewPager viewPager;
	@BindView(R.id.home_card_alias_textview) TextView aliasTextView;
	@BindView(R.id.home_card_branch_imageview) ImageView branchImageView;

	@Override
	public void onAttach(Context context) {
		try {
			callback = (HomeListener) context;
		} catch (ClassCastException e) {
			throw new RuntimeException(context + " must implement HomeListener!");
		}
		super.onAttach(context);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cards = loggedDataInteractor.getCards();
		if(savedInstanceState != null) {
			selectedIndex = savedInstanceState.getInt("selectedIndex");
		} else {
			selectedIndex = 0;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt("selectedIndex", selectedIndex);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_home;
	}

	@Override
	protected void prepareInterface(View mainView) {
		logoutImageView.setVisibility(View.VISIBLE);

		mAdapter = new CardsPagerAdapter(cards, getChildFragmentManager());
		viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

		viewPager.setOffscreenPageLimit(2);
		int margin = (int) getResources().getDimension(R.dimen.cards_view_box_padding) * 2;
		viewPager.setPageMargin(-margin);

		viewPager.setAdapter(mAdapter);
		viewPager.setCurrentItem(selectedIndex, false);
		selectCard(getSelectedCard());
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				//
			}

			@Override
			public void onPageSelected(int position) {
				selectCard(getSelectedCard());
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				//
			}
		});

	}

	@OnClick(R.id.toolbar_logout_imageview)
	void logoutClick() {
		callback.homeLogout();
	}

	@OnClick(R.id.home_detail_textview)
	void detailClick() {
		callback.homeDetailSelected(getSelectedCard());
	}

	@OnClick(R.id.home_ccv_textview)
	void ccvClick() {
		callback.homeSeeCCV(getSelectedCard());
	}

	@OnClick(R.id.home_pin_textview)
	void pinClick() {
		callback.homeSeePin(getSelectedCard());
	}

	private Card getSelectedCard() {
		return cards.get(selectedIndex);
	}

	private void selectCard(Card card) {

		aliasTextView.setText(card.getAlias());
	}

}