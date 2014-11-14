package hk.com.mtr.pcis.web.faces.util;

import java.util.Calendar;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ValidationUtil {
	private final static Log log = LogFactory.getLog(ValidationUtil.class);

	public static boolean validateHKID(String idNum) {
		boolean bResult = false;
		int SumValue = 0;
		int strLength = idNum.length();

		if (strLength == 8) {
			char char0 = idNum.charAt(0);
			char char1 = idNum.charAt(1);
			char char2 = idNum.charAt(2);
			char char3 = idNum.charAt(3);
			char char4 = idNum.charAt(4);
			char char5 = idNum.charAt(5);
			char char6 = idNum.charAt(6);
			char char7 = idNum.charAt(7);

			if ((Character.getNumericValue(char0) >= 10 && Character.getNumericValue(char0) <= 35)
					&& (Character.getNumericValue(char1) >= 0 && Character.getNumericValue(char1) <= 9)
					&& (Character.getNumericValue(char2) >= 0 && Character.getNumericValue(char2) <= 9)
					&& (Character.getNumericValue(char3) >= 0 && Character.getNumericValue(char3) <= 9)
					&& (Character.getNumericValue(char4) >= 0 && Character.getNumericValue(char4) <= 9)
					&& (Character.getNumericValue(char5) >= 0 && Character.getNumericValue(char5) <= 9)
					&& (Character.getNumericValue(char6) >= 0 && Character.getNumericValue(char6) <= 9)
					&& (Character.getNumericValue(char7) >= 0 && Character.getNumericValue(char7) <= 10))

			{
				SumValue = SumValue + 9 * 36 + 8 * (Character.getNumericValue(char0)) + 7 * (Character.getNumericValue(char1)) + 6 * (Character.getNumericValue(char2)) + 5
						* (Character.getNumericValue(char3)) + 4 * (Character.getNumericValue(char4)) + 3 * (Character.getNumericValue(char5)) + 2
						* (Character.getNumericValue(char6));

				int checkDigit = 11 - (SumValue % 11);
				char charCheckDigit;
				if (checkDigit == 10) {
					charCheckDigit = 'A';
					if (char7 == charCheckDigit) {
						bResult = true;
					}
				} else if (checkDigit == 11) {
					charCheckDigit = '0';
					if (char7 == charCheckDigit) {
						bResult = true;
					}
				} else {
					if (Character.getNumericValue(char7) == checkDigit) {
						bResult = true;
					}
				}

			}
		}

		else if (strLength == 9) {
			char char0 = idNum.charAt(0);
			char char1 = idNum.charAt(1);
			char char2 = idNum.charAt(2);
			char char3 = idNum.charAt(3);
			char char4 = idNum.charAt(4);
			char char5 = idNum.charAt(5);
			char char6 = idNum.charAt(6);
			char char7 = idNum.charAt(7);
			char char8 = idNum.charAt(8);

			if ((Character.getNumericValue(char0) >= 10 && Character.getNumericValue(char0) <= 35)
					&& (Character.getNumericValue(char1) >= 10 && Character.getNumericValue(char1) <= 35)
					&& (Character.getNumericValue(char2) >= 0 && Character.getNumericValue(char2) <= 9)
					&& (Character.getNumericValue(char3) >= 0 && Character.getNumericValue(char3) <= 9)
					&& (Character.getNumericValue(char4) >= 0 && Character.getNumericValue(char4) <= 9)
					&& (Character.getNumericValue(char5) >= 0 && Character.getNumericValue(char5) <= 9)
					&& (Character.getNumericValue(char6) >= 0 && Character.getNumericValue(char6) <= 9)
					&& (Character.getNumericValue(char7) >= 0 && Character.getNumericValue(char7) <= 9)
					&& (Character.getNumericValue(char8) >= 0 && Character.getNumericValue(char8) <= 10))

			{
				SumValue = SumValue + 9 * (Character.getNumericValue(char0)) + 8 * (Character.getNumericValue(char1)) + 7 * (Character.getNumericValue(char2)) + 6
						* (Character.getNumericValue(char3)) + 5 * (Character.getNumericValue(char4)) + 4 * (Character.getNumericValue(char5)) + 3
						* (Character.getNumericValue(char6)) + 2 * (Character.getNumericValue(char7));

				int checkDigit = 11 - (SumValue % 11);
				char charCheckDigit;
				if (checkDigit == 10) {
					charCheckDigit = 'A';
					if (char8 == charCheckDigit) {
						bResult = true;
					}
				} else if (checkDigit == 11) {
					charCheckDigit = '0';
					if (char8 == charCheckDigit) {
						bResult = true;
					}
				} else {
					if (Character.getNumericValue(char8) == checkDigit) {
						bResult = true;
					}
				}
			}
		}

		else {
			bResult = false;
		}
		return bResult;
	}

	public static boolean isBirthday(Date birthday) {

		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(birthday);
		dateCalendar.set(Calendar.HOUR, 0);
		dateCalendar.set(Calendar.MINUTE, 0);
		dateCalendar.set(Calendar.SECOND, 0);
		dateCalendar.set(Calendar.MILLISECOND, 0);
		dateCalendar.set(Calendar.AM_PM, Calendar.AM);

		Date currentDate = new Date();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);
		currentCalendar.set(Calendar.HOUR, 0);
		currentCalendar.set(Calendar.MINUTE, 0);
		currentCalendar.set(Calendar.SECOND, 0);
		currentCalendar.set(Calendar.MILLISECOND, 0);
		currentCalendar.set(Calendar.AM_PM, Calendar.AM);
		return dateCalendar.before(currentCalendar);
	}

	public static boolean overMaxAge(Date birthday, int maxAge) {
		Calendar maxAgeCalendar = Calendar.getInstance();
		maxAgeCalendar.setTime(birthday);

		maxAgeCalendar.add(Calendar.YEAR, maxAge);
		maxAgeCalendar.set(Calendar.HOUR, 0);
		maxAgeCalendar.set(Calendar.MINUTE, 0);
		maxAgeCalendar.set(Calendar.SECOND, 0);
		maxAgeCalendar.set(Calendar.MILLISECOND, 0);
		maxAgeCalendar.set(Calendar.AM_PM, Calendar.AM);

		Date currentDate = new Date();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);

		currentCalendar.set(Calendar.HOUR, 0);
		currentCalendar.set(Calendar.MINUTE, 0);
		currentCalendar.set(Calendar.SECOND, 0);
		currentCalendar.set(Calendar.MILLISECOND, 0);
		currentCalendar.set(Calendar.AM_PM, Calendar.AM);

		return currentCalendar.after(maxAgeCalendar);
	}

	public static boolean lessMinAge(Date birthday, int minAge) {
		Calendar minAgeCalendar = Calendar.getInstance();
		minAgeCalendar.setTime(birthday);

		minAgeCalendar.add(Calendar.YEAR, minAge);
		minAgeCalendar.set(Calendar.HOUR, 0);
		minAgeCalendar.set(Calendar.MINUTE, 0);
		minAgeCalendar.set(Calendar.SECOND, 0);
		minAgeCalendar.set(Calendar.MILLISECOND, 0);
		minAgeCalendar.set(Calendar.AM_PM, Calendar.AM);

		Date currentDate = new Date();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);

		currentCalendar.set(Calendar.HOUR, 0);
		currentCalendar.set(Calendar.MINUTE, 0);
		currentCalendar.set(Calendar.SECOND, 0);
		currentCalendar.set(Calendar.MILLISECOND, 0);
		currentCalendar.set(Calendar.AM_PM, Calendar.AM);

		return currentCalendar.before(minAgeCalendar);
	}

	

	
	



	
	
	// Validate Duplicate Application
	public static boolean isDuplicate(String cardType, String cardStatus, Date expiryDate) {
		boolean flag = false;
		if (cardType != null && cardStatus != null && expiryDate != null) {
			if (cardType.equals(Constant.PERSON_CARD_TYPE_SPO)) {
				if (cardStatus.equals(Constant.CARD_STATUS_VALID) || cardStatus.equals(Constant.CARD_STATUS_RENEWAPP) || cardStatus.equals(Constant.CARD_STATUS_RENEWED)) {
					if (validExpiryDate(expiryDate))
						flag = true;
				}
			}
		}
		return flag;
	}
	
	
	

	// Validate ExpiryDate > Today
	@SuppressWarnings("unused")
	private static boolean compareExpiryDate(Date expiryDate) {
		boolean flag = false;
		if (expiryDate != null) {
			Calendar currentCalendar = Calendar.getInstance();
			currentCalendar.setTime(new Date());
			currentCalendar.set(Calendar.HOUR, 0);
			currentCalendar.set(Calendar.MINUTE, 0);
			currentCalendar.set(Calendar.SECOND, 0);
			currentCalendar.set(Calendar.MILLISECOND, 0);

			Calendar expiryDateCalendar = Calendar.getInstance();
			expiryDateCalendar.setTime(expiryDate);
			expiryDateCalendar.set(Calendar.HOUR, 0);
			expiryDateCalendar.set(Calendar.MINUTE, 0);
			expiryDateCalendar.set(Calendar.SECOND, 0);
			expiryDateCalendar.set(Calendar.MILLISECOND, 0);
			if (!expiryDateCalendar.before(currentCalendar)) {
				log.debug("==========================ExpiryDate > Today");
				flag = true;
			}
		}
		return flag;
	}
	
	// Validate ExpiryDate > Today + 90
	private static boolean validExpiryDate(Date expiryDate) {
		boolean flag = false;
		if (expiryDate != null) {
			Calendar currentCalendar = Calendar.getInstance();
			currentCalendar.setTime(new Date());
			currentCalendar.add(Calendar.DAY_OF_YEAR, 90);
			currentCalendar.set(Calendar.HOUR, 0);
			currentCalendar.set(Calendar.MINUTE, 0);
			currentCalendar.set(Calendar.SECOND, 0);
			currentCalendar.set(Calendar.MILLISECOND, 0);

			Calendar expiryDateCalendar = Calendar.getInstance();
			expiryDateCalendar.setTime(expiryDate);
			expiryDateCalendar.set(Calendar.HOUR, 0);
			expiryDateCalendar.set(Calendar.MINUTE, 0);
			expiryDateCalendar.set(Calendar.SECOND, 0);
			expiryDateCalendar.set(Calendar.MILLISECOND, 0);
			if (!expiryDateCalendar.before(currentCalendar)) {
				flag = true;
			}
		}
		return flag;
	}
	@SuppressWarnings("unused")
	// Get Activate Date
	private static String getActivateDate(Date applyDate) {
		String activateDate = "";
		if (applyDate != null) {
			Calendar calendarApplyDate = Calendar.getInstance();
			calendarApplyDate.setTime(applyDate);
			int currentDay = calendarApplyDate.get(Calendar.DAY_OF_MONTH);
			int currentMonth = calendarApplyDate.get(Calendar.MONTH) + 1;
			int currentYear = calendarApplyDate.get(Calendar.YEAR);

			if (currentDay == 25 || currentDay > 25) {
				activateDate = String.valueOf(currentYear) + "-" + String.valueOf(currentMonth) + "-" + String.valueOf(25);
			} else {
				if (currentMonth == 1)
					activateDate = String.valueOf(currentYear - 1) + "-" + String.valueOf(12) + "-" + String.valueOf(25);
				else
					activateDate = String.valueOf(currentYear) + "-" + String.valueOf(currentMonth - 1) + "-" + String.valueOf(25);
			}
		}
		return activateDate;
	}
	@SuppressWarnings("unused")
	// Get End Date
	private static String getEndDate(Date applyDate) {
		String endDate = "";
		if (applyDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(applyDate);
			int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
			int currentMonth = calendar.get(Calendar.MONTH) + 1;
			int currentYear = calendar.get(Calendar.YEAR);

			if (currentDay == 25 || currentDay > 25) {
				if (currentMonth == 12)
					endDate = String.valueOf(currentYear + 1) + "-" + String.valueOf(1) + "-" + String.valueOf(24);
				else
					endDate = String.valueOf(currentYear) + "-" + String.valueOf(currentMonth + 1) + "-" + String.valueOf(24);
			} else {
				endDate = String.valueOf(currentYear) + "-" + String.valueOf(currentMonth) + "-" + String.valueOf(24);
			}
		}
		return endDate;
	}
	@SuppressWarnings("unused")
	// Get Valid Month
	private static String getValidMonth(Date applyDate) {
		String validMonth = "";
		if (applyDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(applyDate);
			int currentMonth = calendar.get(Calendar.MONTH) + 1;
			int currentYear = calendar.get(Calendar.YEAR);
			validMonth = String.valueOf(currentYear) + "-" + String.valueOf(currentMonth) + "-" + String.valueOf(1);
		}
		return validMonth;
	}
	
	
}
