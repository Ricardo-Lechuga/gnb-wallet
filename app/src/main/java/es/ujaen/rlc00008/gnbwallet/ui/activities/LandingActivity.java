package es.ujaen.rlc00008.gnbwallet.ui.activities;

import android.widget.FrameLayout;
import android.widget.Toast;

import butterknife.BindView;
import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseActivity;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.landing.LoginFragment;
import es.ujaen.rlc00008.gnbwallet.ui.fragments.landing.SplashFragment;

/**
 * Created by Ricardo on 22/5/16.
 */
public class LandingActivity extends BaseActivity implements SplashFragment.SplashListener {

	@BindView(R.id.landing_content_frame) FrameLayout contentFrame;

	@Override
	protected int getContentView() {
		return R.layout.activity_landing;
	}

	@Override
	protected void prepareInterface() {
		if (findFragmentById(contentFrame) == null) {
			replaceFragment(new SplashFragment(), contentFrame);
		}
	}

	@Override
	public void loadNoUser() {
		replaceFragment(new LoginFragment(), contentFrame);
	}

	@Override
	public void loadUserOk() {
		//TODO
		Toast.makeText(this, "Load User OK!", Toast.LENGTH_SHORT).show();
	}
}
