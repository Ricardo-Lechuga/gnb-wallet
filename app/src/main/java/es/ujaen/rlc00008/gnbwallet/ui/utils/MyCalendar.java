package es.ujaen.rlc00008.gnbwallet.ui.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.ujaen.rlc00008.gnbwallet.domain.model.factories.GNBLocale;

/**
 * Created by Ricardo on 14/6/16.
 */
public class MyCalendar {

	public static String getCalendarFormatted(Calendar calendar) {
		if (calendar == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", GNBLocale.get());
		return format.format(calendar.getTime());
	}
}
