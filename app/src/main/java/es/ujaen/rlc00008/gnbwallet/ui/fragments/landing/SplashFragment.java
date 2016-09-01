package es.ujaen.rlc00008.gnbwallet.ui.fragments.landing;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import es.ujaen.rlc00008.gnbwallet.R;
import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;

/**
 * Created by Ricardo on 22/5/16.
 */
public class SplashFragment extends BaseFragment {

	private static final long SPLASH_MIN_TIME = 3000;

	public interface SplashListener {
		void splashOk();
	}

	private SplashListener mCallback;

	@Override
	public void onAttach(Context context) {
		try {
			mCallback = (SplashListener) context;
		} catch (ClassCastException e) {
			throw new RuntimeException(context + " must implement SplashListener!");
		}
		super.onAttach(context);
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
				mCallback.splashOk();
			}
		}, SPLASH_MIN_TIME);
	}
}
