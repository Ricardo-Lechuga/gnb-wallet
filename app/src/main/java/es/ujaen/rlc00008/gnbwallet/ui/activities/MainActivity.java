package es.ujaen.rlc00008.gnbwallet.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.ActivateInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.ChallengeInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.DeactivateInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.GetCVVInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.GetPinInteractor;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.dialogs.GenericDialogFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.logged.CVVFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.logged.CardDetailFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.logged.CardTransactionsFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.logged.HomeFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.logged.OperationSignatureFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.logged.PinFragment;

public class MainActivity extends BaseActivity implements
		GenericDialogFragment.GenericDialogListener,
		HomeFragment.HomeListener,
		OperationSignatureFragment.OperationSignatureListener,
		PinFragment.PinListener,
		CVVFragment.CVVListener,
		CardTransactionsFragment.CardTransactionsListener,
		CardDetailFragment.CardDetailListener {

	public static void startActivity(Context context) {
		context.startActivity(getStartIntent(context));
	}

	private static Intent getStartIntent(Context context) {
		return new Intent(context, MainActivity.class);
	}

	private static final int SIGNATURE_PURPOSE_ACTIVATE = 1;
	private static final int SIGNATURE_PURPOSE_DEACTIVATE = 2;
	private static final int SIGNATURE_PURPOSE_CCV = 3;
	private static final int SIGNATURE_PURPOSE_PIN = 4;
	private static final int SIGNATURE_PURPOSE_PAY_NOW = 5;

	private static final int DIALOG_PURPOSE_NONE = 1;
	private static final int DIALOG_PURPOSE_LOGOUT = 2;
	private static final int DIALOG_PURPOSE_CONFIRM_DEACTIVATE = 3;

	private Card selectedCard;
	private int dialogPurpose;
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
			dialogPurpose = DIALOG_PURPOSE_NONE;
			signaturePurpose = -1;
		} else {
			selectedCard = savedInstanceState.getParcelable("selectedCard");
			dialogPurpose = savedInstanceState.getInt("dialogPurpose");
			signaturePurpose = savedInstanceState.getInt("signaturePurpose");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable("selectedCard", selectedCard);
		outState.putInt("dialogPurpose", dialogPurpose);
		outState.putInt("signaturePurpose", signaturePurpose);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void prepareInterface() {
		if (findFragmentById(contentFrame) == null) {
			replaceFragment(HomeFragment.newInstance(null), contentFrame);
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
			removeFragment(contentFrame);
			getSupportFragmentManager().popBackStack();
			return;
		}

		askForLogout();
	}

	private void askForLogout() {
		dialogPurpose = DIALOG_PURPOSE_LOGOUT;
		GenericDialogFragment logoutFragment =
				GenericDialogFragment.newInstance(0, getString(R.string.logout_question), getString(R.string.logout_cancel), getString(R.string.logout_exit));
		showPopUpFragment(logoutFragment);
	}

	@Override
	public void genericDialogCancel() {
		dialogPurpose = DIALOG_PURPOSE_NONE;
		hidePopUpFragment();
	}

	@Override
	public void genericDialogLeftClick() {
		dialogPurpose = DIALOG_PURPOSE_NONE;
		hidePopUpFragment();
	}

	@Override
	public void genericDialogRightClick() {
		switch (dialogPurpose) {
			case DIALOG_PURPOSE_NONE: {
				hidePopUpFragment();
				break;
			}
			case DIALOG_PURPOSE_LOGOUT: {
				logoutInteractor.logout();
				finish();
				break;
			}
			case DIALOG_PURPOSE_CONFIRM_DEACTIVATE: {
				hidePopUpFragment();
				generateChallenge(SIGNATURE_PURPOSE_DEACTIVATE);
				break;
			}
		}
		dialogPurpose = DIALOG_PURPOSE_NONE;
	}

	@Override
	public void homeLogout() {
		askForLogout();
	}

	@Override
	public void homeDetailSelected(Card card) {
		selectedCard = card;
		replaceFragment(CardDetailFragment.newInstance(selectedCard), contentFrame, "");
	}

	@Override
	public void homeSeeCCV(Card card) {
		selectedCard = card;
		generateChallenge(SIGNATURE_PURPOSE_CCV);
	}

	@Override
	public void homeSeePin(Card card) {
		selectedCard = card;
		generateChallenge(SIGNATURE_PURPOSE_PIN);
	}

	@Override
	public void homeActivateCard(Card card) {
		selectedCard = card;
		generateChallenge(SIGNATURE_PURPOSE_ACTIVATE);
	}

	@Override
	public void homeDeactivateCard(Card card) {
		selectedCard = card;
		dialogPurpose = DIALOG_PURPOSE_CONFIRM_DEACTIVATE;
		String message = card.isFavorite() ? getString(R.string.deactivate_favorite_question) : getString(R.string.deactivate_question);
		GenericDialogFragment confirmDeactivateFragment =
				GenericDialogFragment.newInstance(0, message, getString(R.string._cancel), getString(R.string.deactivate_confirm));
		showPopUpFragment(confirmDeactivateFragment);
	}

	@Override
	public void homeTransactionsSelected(Card card) {
		selectedCard = card;
		replaceFragment(CardTransactionsFragment.newInstance(selectedCard), contentFrame, "");
	}

	@Override
	public void homePay(Card card) {
		selectedCard = card;
		//TODO
		Toast.makeText(this, "Operativa no disponible!", Toast.LENGTH_SHORT).show();
		//generateChallenge(SIGNATURE_PURPOSE_PAY_NOW);
	}

	@Override
	public void operationSignatureEntered(String operationSignature) {
		switch (signaturePurpose) {
			case SIGNATURE_PURPOSE_ACTIVATE: {
				activateSelectedCard(operationSignature);
				break;
			}
			case SIGNATURE_PURPOSE_DEACTIVATE: {
				deactivateSelectedCard(operationSignature);
				break;
			}
			case SIGNATURE_PURPOSE_CCV: {
				seeSelectedCardCCV(operationSignature);
				break;
			}
			case SIGNATURE_PURPOSE_PIN: {
				seeSelectedCardPIN(operationSignature);
				break;
			}
			case SIGNATURE_PURPOSE_PAY_NOW: {
				//TODO ?
				break;
			}
		}
	}

	@Override
	public void pinSeeDetail() {
		replaceFragment(CardDetailFragment.newInstance(selectedCard), contentFrame);
	}

	@Override
	public void pinSeeCCV() {
		removeFragment(contentFrame);
		getSupportFragmentManager().popBackStack();
		generateChallenge(SIGNATURE_PURPOSE_CCV);
	}

	@Override
	public void pinClose() {
		removeFragment(contentFrame);
		getSupportFragmentManager().popBackStack();
	}

	@Override
	public void pinSeeAgain() {
		removeFragment(contentFrame);
		getSupportFragmentManager().popBackStack();
		generateChallenge(SIGNATURE_PURPOSE_PIN);
	}

	@Override
	public void cvvSeeDetail() {
		replaceFragment(CardDetailFragment.newInstance(selectedCard), contentFrame);
	}

	@Override
	public void cvvSeePin() {
		removeFragment(contentFrame);
		getSupportFragmentManager().popBackStack();
		generateChallenge(SIGNATURE_PURPOSE_PIN);
	}

	@Override
	public void cvvClose() {
		removeFragment(contentFrame);
		getSupportFragmentManager().popBackStack();
	}

	@Override
	public void cvvSeeAgain() {
		removeFragment(contentFrame);
		getSupportFragmentManager().popBackStack();
		generateChallenge(SIGNATURE_PURPOSE_CCV);
	}

	@Override
	public void cardTransactionsClose() {
		removeFragment(contentFrame);
		getSupportFragmentManager().popBackStack();
	}

	@Override
	public void cardDetailClose() {
		removeFragment(contentFrame);
		getSupportFragmentManager().popBackStack();
	}

	@Override
	public void cardDetailLogout() {
		askForLogout();
	}

	@Override
	public void cardTransactionsLogout() {
		askForLogout();
	}

	void generateChallenge(int signaturePurpose) {
		this.signaturePurpose = signaturePurpose;
		showLoading();
		challengeInteractor.generateChallenge(new ChallengeInteractor.ChallengeCallback() {
			@Override
			public void challengePresented(String question) {
				hideLoading();
				replaceFragment(OperationSignatureFragment.newInstance(question), contentFrame, "");
			}

			@Override
			public void operativeError(String message) {
				hideLoading();
				showErrorFragment(message);
			}
		});
	}

	private void activateSelectedCard(String operationSignature) {
		showLoading();
		activateInteractor.activateCard(selectedCard, operationSignature, new ActivateInteractor.ActivateCallback() {
			@Override
			public void activationOk(Card card) {
				selectedCard = card;
				hideLoading();
				getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				replaceFragment(HomeFragment.newInstance(card), contentFrame);
				showOkFragment(getString(R.string.activate_ok));
			}

			@Override
			public void operativeError(String message) {
				hideLoading();
				removeFragment(contentFrame);
				getSupportFragmentManager().popBackStack();
				showErrorFragment(message);
			}
		});
	}

	private void deactivateSelectedCard(String operationSignature) {
		showLoading();
		deactivateInteractor.deactivateCard(selectedCard, operationSignature, new DeactivateInteractor.DeactivateCallback() {
			@Override
			public void deactivationOk(Card card) {
				selectedCard = card;
				hideLoading();
				getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				replaceFragment(HomeFragment.newInstance(card), contentFrame);
				showOkFragment(getString(R.string.deactivate_ok));
			}

			@Override
			public void operativeError(String message) {
				hideLoading();
				removeFragment(contentFrame);
				getSupportFragmentManager().popBackStack();
				showErrorFragment(message);
			}
		});
	}

	private void seeSelectedCardCCV(String operationSignature) {
		showLoading();
		getCVVInteractor.seeCCV(selectedCard, operationSignature, new GetCVVInteractor.GetCVVCallback() {
			@Override
			public void cvvResponse(String cvv) {
				hideLoading();
				replaceFragment(CVVFragment.newInstance(selectedCard, cvv), contentFrame);
			}

			@Override
			public void operativeError(String message) {
				hideLoading();
				removeFragment(contentFrame);
				getSupportFragmentManager().popBackStack();
				showErrorFragment(message);
			}
		});
	}

	private void seeSelectedCardPIN(String operationSignature) {
		showLoading();
		getPinInteractor.seePin(selectedCard, operationSignature, new GetPinInteractor.GetPinCallback() {
			@Override
			public void pinResponse(String pin) {
				hideLoading();
				replaceFragment(PinFragment.newInstance(selectedCard, pin), contentFrame);
			}

			@Override
			public void operativeError(String message) {
				hideLoading();
				removeFragment(contentFrame);
				getSupportFragmentManager().popBackStack();
				showErrorFragment(message);
			}
		});
	}
}
