package es.ujaen.rlc00008.gnbwallet.ui.fragments;

import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.domain.interactors.InitInteractor;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;

/**
 * Created by Ricardo on 22/5/16.
 */
public class SplashFragment extends BaseFragment implements
		InitInteractor.InitCallback {

	public interface SplashListener {
		void loadNoUser();

		void loadUserOk();
	}

	@Override
	protected int getContentView() {
		return R.layout.fragment_splash;
	}

	@Override
	protected void prepareInterface(View mainView) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				initInteractor.loadInitialData(SplashFragment.this);
			}
		}, 1000);
	}

	@Override
	public void noUser() {
		//TODO
		Toast.makeText(context, "No user!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void userOk() {
		//TODO
		Toast.makeText(context, "User OK!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void operativeError(String message) {
		//TODO
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

}
