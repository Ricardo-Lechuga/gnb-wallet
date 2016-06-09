package es.ujaen.rlc00008.gnbwallet.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.ChallengeInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.dialogs.GenericDialogFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.logged.HomeFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.logged.OperationSignatureFragment;

public class MainActivity extends BaseActivity implements
		GenericDialogFragment.GenericDialogListener,
		HomeFragment.HomeListener,
		OperationSignatureFragment.OperationSignatureListener {

	public static void startActivity(Context context) {
		context.startActivity(getStartIntent(context));
	}

	private static Intent getStartIntent(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		return intent;
	}

	private static final int SIGNATURE_PURPOSE_ACTIVATE = 1;
	private static final int SIGNATURE_PURPOSE_DEACTIVATE = 2;
	private static final int SIGNATURE_PURPOSE_CCV = 3;
	private static final int SIGNATURE_PURPOSE_PIN = 4;
	private static final int SIGNATURE_PURPOSE_PAY_NOW = 5;

	private Card selectedCard;
	private boolean isShowingLogoutMessage;
	private int signaturePurpose;

	@BindView(R.id.main_content_frame) FrameLayout contentFrame;

	@Override
	protected int getContentView() {
		return R.layout.activity_main;
	}

	@Override
	protected void initState(Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			selectedCard = null;
			isShowingLogoutMessage = false;
			signaturePurpose = -1;
		} else {
			selectedCard = savedInstanceState.getParcelable("selectedCard");
			isShowingLogoutMessage = savedInstanceState.getBoolean("isShowingLogoutMessage");
			signaturePurpose = savedInstanceState.getInt("signaturePurpose");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable("selectedCard", selectedCard);
		outState.putBoolean("isShowingLogoutMessage", isShowingLogoutMessage);
		outState.putInt("signaturePurpose", signaturePurpose);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void prepareInterface() {
		if (findFragmentById(contentFrame) == null) {
			replaceFragment(new HomeFragment(), contentFrame);
		}
	}

	@Override
	public void onBackPressed() {
		BaseFragment currentFragment = findFragmentById(contentFrame);

		if (currentFragment != null && currentFragment.backPressed()) {
			// inner fragment manages back pressed
			return;
		}
		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			// At least one fragment in back stack
			getSupportFragmentManager().popBackStack();
			return;
		}

		askForLogout();
	}

	private void askForLogout() {
		isShowingLogoutMessage = true;
		GenericDialogFragment logoutFragment =
				GenericDialogFragment.newInstance(0, getString(R.string.logout_question), getString(R.string.logout_cancel), getString(R.string.logout_exit));
		showPopUpFragment(logoutFragment);
	}

	@Override
	public void genericDialogCancel() {
		isShowingLogoutMessage = false;
		hidePopUpFragment();
	}

	@Override
	public void genericDialogLeftClick() {
		isShowingLogoutMessage = false;
		hidePopUpFragment();
	}

	@Override
	public void genericDialogRightClick() {
		if (isShowingLogoutMessage) {
			isShowingLogoutMessage = false;
			logoutInteractor.logout();
			finish();
		} else {
			hidePopUpFragment();
		}
	}

	@Override
	public void homeLogout() {
		askForLogout();
	}

	@Override
	public void homeDetailSelected(Card card) {
		selectedCard = card;
		//TODO!
		Toast.makeText(this, "Go Detail!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void homeSeeCCV(Card card) {
		selectedCard = card;
		//TODO!
		Toast.makeText(this, "Go CCV!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void homeSeePin(Card card) {
		selectedCard = card;
		//TODO!
		Toast.makeText(this, "Go PIN!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void homeActivateCard(Card card) {
		selectedCard = card;
		showLoading();
		challengeInteractor.generateChallenge(new ChallengeInteractor.ChallengeCallback() {
			@Override
			public void challengePresented(String question) {
				hideLoading();
				signaturePurpose = SIGNATURE_PURPOSE_ACTIVATE;
				replaceFragment(OperationSignatureFragment.newInstance(question), contentFrame, null);
			}

			@Override
			public void operativeError(String message) {
				hideLoading();
				showErrorFragment(message);
			}
		});
	}

	@Override
	public void homeDeactivateCard(Card card) {
		selectedCard = card;
		//TODO!
		Toast.makeText(this, "Deactivate card!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void homeTransactionsSelected(Card card) {
		selectedCard = card;
		//TODO!
		Toast.makeText(this, "Transactions!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void homePay(Card card) {
		selectedCard = card;
		//TODO!
		Toast.makeText(this, "Pay now!!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void operationSignatureEntered(String operationSignature) {
		switch (signaturePurpose) {
			case SIGNATURE_PURPOSE_ACTIVATE: {
				//TODO Call activate!
				break;
			}
			case SIGNATURE_PURPOSE_DEACTIVATE: {

				break;
			}
			case SIGNATURE_PURPOSE_CCV: {

				break;
			}
			case SIGNATURE_PURPOSE_PIN: {

				break;
			}
			case SIGNATURE_PURPOSE_PAY_NOW: {

				break;
			}
		}
	}
}
