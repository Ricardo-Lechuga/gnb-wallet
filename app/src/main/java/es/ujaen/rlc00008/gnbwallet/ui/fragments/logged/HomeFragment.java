package es.ujaen.rlc00008.gnbwallet.ui.fragments.logged;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.SetFavoriteInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.UnsetFavoriteInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.ui.adapters.pagers.CardsPagerAdapter;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.dialogs.GenericDialogFragment;
import es.ujaen.rlc00008.gnbwallet.ui.utils.BranchImages;
import es.ujaen.rlc00008.gnbwallet.ui.views.ZoomOutPageTransformer;

/**
 * Created by Ricardo on 4/6/16.
 */
public class HomeFragment extends BaseFragment implements
		GenericDialogFragment.GenericDialogListener {

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
	@BindView(R.id.home_activation_clickable_view) View activationClickableView;
	@BindView(R.id.home_activation_icon_imageview) ImageView activationIconImageView;
	@BindView(R.id.home_activation_textview) TextView activationTextView;
	@BindView(R.id.home_transactions_clickable_view) View transactionsClickableView;
	@BindView(R.id.home_transactions_icon_imageview) ImageView transactionsIconImageView;
	@BindView(R.id.home_transactions_textview) TextView transactionsTextView;
	@BindView(R.id.home_favorite_clickable_view) View favoriteClickableView;
	@BindView(R.id.home_favorite_icon_imageview) ImageView favoriteIconImageView;
	@BindView(R.id.home_favorite_textview) TextView favoriteTextView;
	@BindView(R.id.home_activate_button) Button activateButton;
	@BindView(R.id.home_pay_button) Button payButton;

	public static HomeFragment newInstance(Card initialCard) {
		Bundle bundle = new Bundle();
		bundle.putParcelable("initialCard", initialCard);
		HomeFragment fragment = new HomeFragment();
		fragment.setArguments(bundle);
		return fragment;
	}

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
		if (savedInstanceState != null) {
			selectedIndex = savedInstanceState.getInt("selectedIndex");
		} else {
			Card initialCard = getArguments().getParcelable("initialCard");
			if (initialCard != null) {
				selectedIndex = cards.indexOf(initialCard);
			} else {
				selectedIndex = 0;
			}
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
				selectedIndex = position;
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

	@OnClick(R.id.home_activation_clickable_view)
	void activationClick() {
		Card selectedCard = getSelectedCard();
		if (getSelectedCard().isEnabled()) {
			callback.homeDeactivateCard(selectedCard);
		} else {
			callback.homeActivateCard(selectedCard);
		}
	}

	@OnClick(R.id.home_transactions_clickable_view)
	void transactionsClick() {
		callback.homeTransactionsSelected(getSelectedCard());
	}

	@OnClick(R.id.home_activate_button)
	void activateClick() {
		callback.homeActivateCard(getSelectedCard());
	}

	@OnClick(R.id.home_favorite_clickable_view)
	void favoriteClick() {
		Card selectedCard = getSelectedCard();
		if (!selectedCard.isEnabled()) {
			showErrorFragment(getString(R.string.home_error_activate_before_favorite));
		} else if (selectedCard.isFavorite()) {
			unsetFavorite();
		} else {
			setFavorite();
		}
	}

	@OnClick(R.id.home_pay_button)
	void payClick() {
		callback.homePay(getSelectedCard());
	}

	private Card getSelectedCard() {
		return cards.get(selectedIndex);
	}

	private void selectCard(Card card) {

		aliasTextView.setText(card.getAlias());

		branchImageView.setImageResource(BranchImages.getResId(card));

		if (card.isEnabled()) {
			activationIconImageView.setImageResource(R.drawable.icn_deactivate);
			activationTextView.setText(R.string.home_deactivate);
			activateButton.setVisibility(View.GONE);

			if (card.isNfc()) {
				payButton.setVisibility(View.VISIBLE);
			} else {
				payButton.setVisibility(View.GONE);
			}
		} else {
			activationIconImageView.setImageResource(R.drawable.icn_activate);
			activationTextView.setText(R.string.home_activate);
			activateButton.setVisibility(View.VISIBLE);
			payButton.setVisibility(View.GONE);
		}

		if (card.isNfc()) {
			favoriteClickableView.setVisibility(View.VISIBLE);
		} else {
			favoriteClickableView.setVisibility(View.GONE);
		}

		if (card.isFavorite()) {
			favoriteIconImageView.setImageResource(R.drawable.icn_favs_on);
		} else {
			favoriteIconImageView.setImageResource(R.drawable.icn_favs_off);
		}
	}

	private void setFavorite() {
		showLoading();
		setFavoriteInteractor.setFavorite(getSelectedCard(), new SetFavoriteInteractor.SetFavoriteCallback() {
			@Override
			public void setFavoriteOk(Card card) {
				cards = loggedDataInteractor.getCards();
				mAdapter.refreshList(cards);
				selectCard(getSelectedCard());
				hideLoading();
			}

			@Override
			public void operativeError(String message) {
				hideLoading();
				showErrorFragment(message);
			}
		});
	}

	private void unsetFavorite() {
		showLoading();
		unsetFavoriteInteractor.unsetFavorite(getSelectedCard(), new UnsetFavoriteInteractor.UnsetFavoriteCallback() {
			@Override
			public void unsetFavoriteOk(Card card) {
				cards = loggedDataInteractor.getCards();
				mAdapter.refreshList(cards);
				selectCard(getSelectedCard());
				hideLoading();
			}

			@Override
			public void operativeError(String message) {
				hideLoading();
				showErrorFragment(message);
			}
		});
	}
}
