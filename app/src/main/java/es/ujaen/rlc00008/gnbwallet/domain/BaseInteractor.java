package es.ujaen.rlc00008.gnbwallet.domain;

import android.content.Context;
import android.os.Handler;

import javax.inject.Inject;

import es.ujaen.rlc00008.gnbwallet.data.GNBRepository;

/**
 * Created by Ricardo on 22/5/16.
 */
public abstract class BaseInteractor {

	protected interface BaseInteractorCallback {
		void operativeError(String message);
	}

	@Inject protected Context context;
	@Inject protected GNBRepository repository;

	protected Thread mUiThread;
	protected Handler mHandler;

	public BaseInteractor() {
		mHandler = new Handler();
		mUiThread = Thread.currentThread();
	}

	/**
	 * Runs the specified action on the UI thread. If the current thread is the UI
	 * thread, then the action is executed immediately. If the current thread is
	 * not the UI thread, the action is posted to the event queue of the UI thread.
	 *
	 * @param action the action to run on the UI thread
	 */
	protected final void runOnUiThread(Runnable action) {
		if (Thread.currentThread() != mUiThread) {
			mHandler.post(action);
		} else {
			action.run();
		}
	}
}
