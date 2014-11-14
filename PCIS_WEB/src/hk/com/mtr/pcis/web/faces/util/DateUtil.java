package hk.com.mtr.pcis.web.faces.util;

import hk.com.mtr.pcis.util.StringUtil;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

public class DateUtil {
	public static java.util.Date stringToDate(String strDate, String pattern) {
		if (strDate != null && strDate.length() > 0 && pattern != null && pattern.length() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			ParsePosition pos = new ParsePosition(0);
			sdf.setLenient(false);
			return sdf.parse(strDate, pos);
		}
		return null;

	}

	public static String datetoString(java.util.Date date, String pattern) {
		if (date != null) {
			if (pattern != null && pattern.length() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				String dateString = sdf.format(date);

				return dateString;
			} else
				return date.toString();
		}
		return null;
	}

	public static Timestamp stringToTimestamp(String strDate, String pattern) {
		if (strDate != null && strDate.length() > 0 && pattern != null && pattern.length() > 0) {
			SimpleDateFormat smf = new SimpleDateFormat(pattern);
			ParsePosition pos = new ParsePosition(0);
			smf.setLenient(false);
			return new Timestamp(smf.parse(strDate, pos).getTime());
		}
		return null;
	}

	public static String timestampToString(Timestamp date, String pattern) {
		if (date != null && pattern != null) {
			SimpleDateFormat smf = new SimpleDateFormat(pattern);
			return smf.format(date);
		}
		return null;
	}

	public static boolean isEmpty(java.util.Date date) {
		if (date == null || StringUtil.isEmpty(date.toString()))
			return true;

		return false;
	}

}
