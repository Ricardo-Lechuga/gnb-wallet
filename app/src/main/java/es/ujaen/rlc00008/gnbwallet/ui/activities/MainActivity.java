package es.ujaen.rlc00008.gnbwallet.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.model.Card;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.dialogs.GenericDialogFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.logged.HomeFragment;

public class MainActivity extends BaseActivity implements
		GenericDialogFragment.GenericDialogListener,
		HomeFragment.HomeListener {

	@BindView(R.id.main_content_frame) FrameLayout contentFrame;

	public static void startActivity(Context context) {
		context.startActivity(getStartIntent(context));
	}

	private static Intent getStartIntent(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		return intent;
	}

	@Override
	protected int getContentView() {
		return R.layout.activity_main;
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
		askForLogout();
	}

	private void askForLogout() {
		GenericDialogFragment logoutFragment = GenericDialogFragment.newInstance(0, getString(R.string.logout_question), getString(R.string.logout_cancel), getString(R.string.logout_exit));
		showPopUpFragment(logoutFragment);
	}

	@Override
	public void genericDialogCancel() {
		hidePopUpFragment();
	}

	@Override
	public void genericDialogLeftClick() {
		hidePopUpFragment();
	}

	@Override
	public void genericDialogRightClick() {
		logoutInteractor.logout();
		finish();
	}

	@Override
	public void homeLogout() {
		askForLogout();
	}

	@Override
	public void homeDetailSelected(Card card) {
		//TODO!
		Toast.makeText(this, "Go Detail!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void homeSeeCCV(Card card) {
		//TODO!
		Toast.makeText(this, "Go CCV!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void homeSeePin(Card card) {
		//TODO!
		Toast.makeText(this, "Go PIN!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void homeActivateCard(Card card) {
		//TODO!
		Toast.makeText(this, "Activate card!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void homeDeactivateCard(Card card) {
		//TODO!
		Toast.makeText(this, "Deactivate card!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void homeTransactionsSelected(Card card) {
		//TODO!
		Toast.makeText(this, "Transactions!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void homePay(Card card) {
		//TODO!
		Toast.makeText(this, "Pay now!!", Toast.LENGTH_SHORT).show();
	}
}
