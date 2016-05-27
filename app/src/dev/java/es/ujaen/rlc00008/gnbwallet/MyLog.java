package es.ujaen.rlc00008.gnbwallet;

import android.util.Log;

/**
 * @author Ricardo Lechuga (ricardo.cion@gmail.com)
 */
public abstract class MyLog {

	public static boolean FLAG_DEBUG = true;
	public static boolean FLAG_WARN = true;
	public static boolean FLAG_INFO = true;
	public static boolean FLAG_VERBOSE = true;
	public static boolean FLAG_ERROR = true;
	public static boolean FLAG_WTF = true;
	public static boolean FLAG_STACKTRACE = true;

	/**
	 * Si FLAG_DEBUG está activado, llama a Log.d()
	 *
	 * @see Log
	 */
	public static int d(String tag, String msg) {
		if (FLAG_DEBUG) {
			return Log.d(tag, msg);
		}
		return -1;
	}

	/**
	 * Si FLAG_VERBOSE está activado, llama a Log.d()
	 *
	 * @see Log
	 */
	public static int v(String tag, String msg) {
		if (FLAG_VERBOSE) {
			return Log.v(tag, msg);
		}
		return -1;
	}

	/**
	 * Si FLAG_ERROR está activado, llama a Log.e()
	 *
	 * @see Log
	 */
	public static int e(String tag, String msg) {
		if (FLAG_ERROR) {
			return Log.e(tag, msg);
		}
		return -1;
	}

	/**
	 * Si FLAG_INFO está activado, llama a Log.i()
	 *
	 * @see Log
	 */
	public static int i(String tag, String msg) {
		if (FLAG_INFO) {
			return Log.i(tag, msg);
		}
		return -1;
	}

	/**
	 * Si FLAG_WARN está activado, llama a Log.w()
	 *
	 * @see Log
	 */
	public static int w(String tag, String msg) {
		if (FLAG_WARN) {
			return Log.w(tag, msg);
		}
		return -1;
	}

	/**
	 * Si FLAG_WTF está activado, llama a Log.wtf()
	 *
	 * @see Log
	 */
	public static int wtf(String tag, String msg) {
		if (FLAG_WTF) {
			return Log.wtf(tag, msg);
		}
		return -1;
	}

	/**
	 * Si FLAG_STACKTRACE está activado, llama a t.printstacktrace
	 *
	 * @param t throwable (Exception or Error)
	 */
	public static void printStackTrace(Throwable t) {
		if (FLAG_STACKTRACE) {
			t.printStackTrace();
		}
	}
}
