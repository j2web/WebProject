package hk.com.mtr.pcis.util;

public class StringUtil {
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().equals(""))
			return true;

		return false;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static final boolean isFuzzyQuery(String searchCriteria) {

		return isNotEmpty(searchCriteria) && searchCriteria.indexOf("%") != -1;
	}

	public static String toString(Object o) {
		if (o == null)
			return "";
		else
			return o.toString();
	}
	
	public static String toCSVContent(Object o) {
		if (o == null)
			return "\"\"";
		else
			return new StringBuilder("\"").append(o.toString().replaceAll("\"", "\"\"")).append("\"").toString();
	}
}
