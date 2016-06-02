package es.ujaen.rlc00008.gnbwallet.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;

import butterknife.BindView;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.dialogs.GenericDialogFragment;

public class MainActivity extends BaseActivity implements
		GenericDialogFragment.GenericDialogListener {

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
}
