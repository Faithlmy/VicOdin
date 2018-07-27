
package com.vic.rest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>Titile:DateTimeUtil</p>
 * <p>ProjectName:odin</p>
 * <p>Description:TODO </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 * @author  
 * @date: -10
 * @version 1.0
 */
public class DateTimeUtil {
	
	/**
	 * @method:toDateTime
	 * @description:String轉換成Date
	 * @author:   
	 * @date: -10
	 * @param strDate
	 * @return Date
	 */
	public static Date toDateTime(String strDate) {
		Date datReturn = null;
	if (strDate == null) {
		return datReturn;
	}
	try {
		String strForm = "yyyy-MM-dd";
	 	if (strDate.indexOf(":") > 0) {
		 	strForm = "yyyy-MM-dd HH:mm";
	 	}
	 	if ((strDate.indexOf(":") > 0) && (strDate.lastIndexOf(":") > strDate.indexOf(":"))) {
		 	strForm = "yyyy-MM-dd HH:mm:ss";
	 	}
 			SimpleDateFormat tmpsdf = new SimpleDateFormat(strForm);
 			datReturn = tmpsdf.parse(strDate);
	}
	catch (Exception ex) {}
	return datReturn;
	}
	
	/**
	 * @method:get
	 * @description:獲取年月日時分秒的值
	 * @author:   
	 * @date: -10
	 * @param mydate
	 * @param interval
	 * @return int
	 */
	public static int get(Date mydate, String interval){
		int intReturn = -1;int intField = 0;
		if ((mydate == null) || (interval == null)) {
			return intReturn;
		}
		try {
			Calendar cldr = Calendar.getInstance();
			cldr.setTime(mydate);
		if (interval.equals("Y")) {
			intField = 1;
		} else if (interval.equals("M")) {
			intField = 2;
		} else if (interval.equals("D")) {
			intField = 5;
		} else if (interval.equals("W")) {
			intField = 7;
		} else if (interval.equals("H")) {
			intField = 11;
		} else if (interval.equals("m")) {
			intField = 12;
		} else if (interval.equals("S")) {
			intField = 13;
		}
		intReturn = cldr.get(intField);
		} catch (Exception ex) {}
		return intReturn;
	}
	
	/**
	 * @method:get
	 * @description:獲取年月日時分秒的值
	 * @author:   
	 * @date: -10
	 * @param strDate
	 * @param interval
	 * @return int
	 */
	public static int get(String strDate, String interval) {
		return get(toDateTime(strDate), interval);
	}
	
	/**
	 * @method:getMaxday
	 * @description:獲取本月天數
	 * @author:   
	 * @date: -10
	 * @param mydate
	 * @return int
	 */
	public static int getMaxday(Date mydate) {
		int intReturn = -1;
		if (mydate == null) {
			return intReturn;
		}
		try {
			Calendar cldr = Calendar.getInstance();
			cldr.setTime(mydate);
			intReturn = cldr.getActualMaximum(5);
		} catch (Exception ex) {}
		return intReturn;
	}
	
	/**
	 * @method:getMaxday
	 * @description:獲取本月天數
	 * @author:   
	 * @date: -10
	 * @param strDate
	 * @return int
	 */
	public static int getMaxday(String strDate) {
		return getMaxday(toDateTime(strDate));
	}
	
	/**
	 * @method:formatDateTime
	 * @description:格式化時間
	 * @author:   
	 * @date: -10
	 * @param mydate
	 * @param strFormat
	 * @return String
	 */
	public static String formatDateTime(Date mydate, String strFormat) {
		String strReturn = "";
		if (mydate == null) {
			return strReturn;
		}
		if ((strFormat == null) || (strFormat.equals(""))) {
			strFormat = "yyyy-MM-dd";
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
			strReturn = sdf.format(mydate);
		}
		catch (Exception ex) {}
		return strReturn;
	}
	
	/**
	 * @method:formatDateTime
	 * @description:格式化時間
	 * @author:   
	 * @date: -10
	 * @param strDate
	 * @param strFormat
	 * @return String
	 */
	public static String formatDateTime(String strDate, String strFormat) {
		return formatDateTime(toDateTime(strDate), strFormat);
	}
	
	/**
	 * @method:getCurDateTime
	 * @description:格式化當前時間
	 * @author:   
	 * @date: -10
	 * @param strFormat
	 * @return String
	 */
	public static String getCurDateTime(String strFormat) {
		String strReturn = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
			Calendar cldr = Calendar.getInstance();
			strReturn = sdf.format(cldr.getTime());
		} catch (Exception ex) {}
		return strReturn;
	}
	
	/**
	 * @method:getCurDate
	 * @description:獲取當前日期
	 * @author:   
	 * @date: -10
	 * @return String
	 */
	public static String getCurDate() {
		String strReturn = "";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cldr = Calendar.getInstance();
			strReturn = sdf.format(cldr.getTime());
		} catch (Exception ex) {}
		return strReturn;
	}
	
	/**
	 * @method:getCurTime
	 * @description:獲取當前時間（時:分）
	 * @author:   
	 * @date: -10
	 * @return String
	 */
	public static String getCurTime() {
		String strReturn = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			Calendar cldr = Calendar.getInstance();
			strReturn = sdf.format(cldr.getTime());
		} catch (Exception ex) {}
		return strReturn;
	}
	
	/**
	 * @method:dateAdd
	 * @description:修改時間
	 * @author:   
	 * @date: -10
	 * @param mydate
	 * @param interval
	 * @param number
	 * @return Date
	 */
	public static Date dateAdd(Date mydate, String interval, int number) {
		Date datReturn = null;
		int intInterval = 6;
		if (interval.equals("Y")) {
			intInterval = 1;
		} else if (interval.equals("M")) {
			intInterval = 2;
		} else if (interval.equals("D")) {
			intInterval = 6;
		} else if (interval.equals("H")) {
			intInterval = 11;
		} else if (interval.equals("m")) {
			intInterval = 12;
		} else if (interval.equals("S")) {
			intInterval = 13;
		}
		GregorianCalendar cal = new GregorianCalendar();
		try {
			cal.setTime(mydate);
			cal.add(intInterval, number);
			datReturn = cal.getTime();
		}catch (Exception ex) {}
		return datReturn;
	}
	
	/**
	 * @method:dateAdd
	 * @description:修改時間
	 * @author:   
	 * @date: -10
	 * @param strDate
	 * @param interval
	 * @param number
	 * @return Date
	 */
	public static Date dateAdd(String strDate, String interval, int number) {
		return dateAdd(toDateTime(strDate), interval, number);
	}
	
	/**
	 * @method:dateDiff
	 * @description:時間比較差值
	 * @author:   
	 * @date: -10
	 * @param interval
	 * @param date1
	 * @param date2
	 * @return int
	 */
	public static int dateDiff(String interval, Date date1, Date date2) {
		int intReturn = -1000000000;
		if ((date1 == null) || (date2 == null) || (interval == null)) {
			return intReturn;
		} try {
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
	

			cal1.setTime(date1);
			long ldate1 = date1.getTime() + cal1.get(15) + cal1.get(16);
	
			cal2.setTime(date2);
			long ldate2 = date2.getTime() + cal2.get(15) + cal2.get(16);
	

			int hr1 = (int)(ldate1 / 3600000L);
			int hr2 = (int)(ldate2 / 3600000L);
	
			int days1 = hr1 / 24;
			int days2 = hr2 / 24;
	
			int yearDiff = cal2.get(1) - cal1.get(1);
			int monthDiff = yearDiff * 12 + cal2.get(2) - cal1.get(2);
			int dateDiff = days2 - days1;
			int hourDiff = hr2 - hr1;
			int minuteDiff = (int)(ldate2 / 60000L) - (int)(ldate1 / 60000L);
			int secondDiff = (int)(ldate2 / 1000L) - (int)(ldate1 / 1000L);
			if (interval.equals("Y")) {
				intReturn = yearDiff;
			} else if (interval.equals("M")) {
				intReturn = monthDiff;
			} else if (interval.equals("D")) {
				intReturn = dateDiff;
			} else if (interval.equals("H")) {
				intReturn = hourDiff;
			} else if (interval.equals("m")) {
				intReturn = minuteDiff;
			} else if (interval.equals("S")) {
				intReturn = secondDiff;
			}
	} catch (Exception ex) {}
		return intReturn;
	}
	
	/**
	 * @method:dateDiff
	 * @description:時間比較差值
	 * @author:   
	 * @date: -10
	 * @param interval
	 * @param strDate1
	 * @param strDate2
	 * @return
	 */
	public static int dateDiff(String interval, String strDate1, String strDate2) {
		return dateDiff(interval, toDateTime(strDate1), toDateTime(strDate2));
	}
	
	/**
	 * @method:getWeekCN
	 * @description:獲取週幾
	 * @author:   
	 * @date: -10
	 * @param iWeek
	 * @return String
	 */
	public static String getWeekCN(int iWeek) {
		String strRtn = "";
		switch (iWeek) {
		case 1: 
			strRtn = "日";
			break;
		case 2: 
			strRtn = "一";
			break;
		case 3: 
			strRtn = "二";
			break;
		case 4: 
			strRtn = "三";
			break;
		case 5: 
			strRtn = "四";
			break;
		case 6: 
			strRtn = "五";
			break;
		case 7: 
			strRtn = "六";
			break;
		default: 
			strRtn = "";
		}
		return strRtn;
	}
}
