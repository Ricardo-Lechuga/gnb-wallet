package es.ujaen.rlc00008.gnbwallet.ui.fragments;

import es.ujaen.rlc00008.gnbwallet.ui.base.BaseFragment;

/**
 * Created by Ricardo on 22/5/16.
 */
public class SplashFragment extends BaseFragment {

	public interface SplashListener {
		void loadNoUser();

		void loadUserOk();
	}
}
