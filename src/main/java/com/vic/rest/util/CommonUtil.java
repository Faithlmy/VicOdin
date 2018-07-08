/**
 * ======================================================================
 * 版權：富士康工業互聯網股份有限公司 平臺解決方案事業處 版權所有 (c) 2018
 * 文件：util
 * 所含類：CommonUtil
 * 修改記錄
 * 日期				作者					版本				內容
 * ======================================================================
 * 2018-06-30		zn.xie(F1334993)	V1.0			新建
 * ======================================================================
 */
package com.vic.rest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Titile:CommonUtil</p>
 * <p>ProjectName:odin</p>
 * <p>Description:公用工具類 </p>
 * <p>Copyright:Copyright (c) 2018</p>
 * <p>Company:Foxconn</p>
 *
 * @author zn.xie(F1334993)
 * @version 1.0
 * @date:2018-06-30
 */
public class CommonUtil {

    /**
     * @param request
     * @return
     * @method:getIP
     * @description:獲取用戶ip地址
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static String getIP(HttpServletRequest request) {
        String ip = "";
        ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * @param date
     * @return
     * @method:getDateStr
     * @description:獲取格式化日期
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static String getDateStr(Date date) {
        String dateStr = "";
        if (null != date) {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            dateStr = sdf.format(date);
        }
        return dateStr;
    }

    /**
     * @return
     * @method:getDateStr
     * @description:獲取格式化日期
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static String getDateStr() {
        return getDateStr(new Date());
    }

    /**
     * @param date
     * @return
     * @method:getDateTimeStr
     * @description:獲取格式化時間
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static String getDateTimeStr(Date date) {
        String dateStr = "";
        if (null != date) {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            dateStr = sdf.format(date);
        }
        return dateStr;
    }

    /**
     * @return
     * @method:getDateTimeStr
     * @description:獲取格式化時間
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static String getDateTimeStr() {
        return getDateTimeStr(new Date());
    }

    /**
     * @param date
     * @return
     * @method:getYear
     * @description:獲取年
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static int getYear(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        return cld.get(Calendar.YEAR);
    }

    /**
     * @param date
     * @return
     * @method:getMonth
     * @description:獲取月份
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static int getMonth(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        return cld.get(Calendar.MONTH) + 1;
    }

    /**
     * @param date
     * @return
     * @method:getDay
     * @description:獲取星期幾
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static int getDay(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        return cld.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @param date
     * @return
     * @method:getHour
     * @description:獲取小時
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static int getHour(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        return cld.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @param date
     * @return
     * @method:getMinute
     * @description:獲取分
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static int getMinute(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        return cld.get(Calendar.MINUTE);
    }

    /**
     * @param date
     * @return
     * @method:getSecond
     * @description:獲取秒
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static int getSecond(Date date) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        return cld.get(Calendar.SECOND);
    }

    /**
     * @param list
     * @return
     * @method:isNotEmpty
     * @description:判斷list非空
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static boolean isNotEmpty(List list) {
        return null != list && !list.isEmpty() ? true : false;
    }

    /**
     * @param list
     * @return
     * @method:isEmpty
     * @description:判斷list為空
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static boolean isEmpty(List list) {
        return null == list || list.isEmpty() ? true : false;
    }

    /**
     * @param objs
     * @return
     * @method:isNotEmpty
     * @description:判斷數據非空
     * @author:zn.xie(F1334993)
     * @date:2018-07-04
     */
    public static boolean isNotEmpty(Object[] objs) {
        return null != objs && objs.length != 0 ? true : false;
    }

    /**
     * @param objs
     * @return
     * @method:isEmpty
     * @description:判斷數據為空
     * @author:zn.xie(F1334993)
     * @date:2018-07-04
     */
    public static boolean isEmpty(Object[] objs) {
        return null == objs || objs.length == 0 ? true : false;
    }
    
    /**
     * @method:isNotEmptyMapValue
     * @description:判斷map值非空
     * @author:zn.xie(F1334993)  
     * @date:2018-11-22
     * @param map
     * @param key
     * @return boolean
     */
    public static boolean isNotEmptyMapValue(Map map, String key){
    	return null != map && map.containsKey(key) && null != map.get(key) && StringUtils.isNotEmpty(map.get(key).toString());
    }
    
    /**
     * @method:isEmptyMapValue
     * @description:判斷map值為空
     * @author:zn.xie(F1334993)  
     * @date:2018-11-22
     * @param map
     * @param key
     * @return boolean
     */
    public static boolean isEmptyMapValue(Map map, String key){
    	return !isNotEmptyMapValue(map, key);
    }

    /**
     * @param code
     * @return
     * @method:checkCode
     * @description:檢查編碼是否只包含大小寫字母、下劃線
     * @author:zn.xie(F1334993)
     * @date:2018-06-30
     */
    public static boolean checkCode(String code) {
        if (StringUtils.isEmpty(code)) return false;
        String pattern = "^[A-Za-z_]+$*";
        return Pattern.matches(pattern, code);
    }

    /**
     * @method:ListToString
     * @description:将数组转化为sql查询条件
     * @author:yq.xiao(F1333250)
     * @date:2018/7/25
     * @param:[list]
     * @return:java.lang.String
     */
    public static String ListToString(String[] list) {
        StringBuffer condition = new StringBuffer();
        for (int i = 0; i < list.length; i++) {
            if (i != list.length - 1) {
                condition.append("'" + list[i] + "',");
            } else {
                condition.append("'" + list[i] + "'");
            }
        }
        return condition.toString();
    }

    public static String ListToString(List<String> list) {
        StringBuffer condition = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                condition.append("'" + list.get(i) + "',");
            } else {
                condition.append("'" + list.get(i) + "'");
            }
        }
        return condition.toString();
    }

    /**
     * @method:isNumber
     * @description:判断字符串是否为数字,包括浮点数
     * @author:yq.xiao(F1333250)
     * @date:2018/8/3
     * @param:[str]
     * @return:boolean
     */
    public static boolean isNumber(String str) {
        //采用正则表达式的方式来判断一个字符串是否为数字，这种方式判断面比较全
        //可以判断正负、整数小数

        boolean isInt = Pattern.compile("^-?[1-9]\\d*$").matcher(str).find();
        boolean isDouble = Pattern.compile("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$").matcher(str).find();

        return isInt || isDouble;

    }

    /**
     * @param a
     * @return String
     * @method:ArrayToString
     * @description:數組轉sql字符串
     * @author:zn.xie(F1334993)
     * @date:2018-07-24
     */
    public static String ArrayToString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "()";

        StringBuilder b = new StringBuilder();
        b.append("(");
        for (int i = 0; ; i++) {
            b.append("'").append(String.valueOf(a[i]));
            if (i == iMax)
                return b.append("')").toString();
            b.append("', ");
        }
    }
    
    /**
     * @param a
     * @return String
     * @method:ArrayToString
     * @description:list轉sql字符串
     * @author:zn.xie(F1334993)
     * @date:2018-07-24
     */
    public static String ArrayToString(List a) {
    	if (a == null)
    		return "null";
    	
    	int iMax = a.size() - 1;
    	if (iMax == -1)
    		return "()";
    	
    	StringBuilder b = new StringBuilder();
    	b.append("(");
    	for (int i = 0; ; i++) {
    		b.append("'").append(String.valueOf(a.get(i)));
    		if (i == iMax)
    			return b.append("')").toString();
    		b.append("', ");
    	}
    }
    
    /**
     * @method:escapeSQLLike
     * @description:與sql防注入相關
     * @author:zn.xie(F1334993)  
     * @date:2018-09-21
     * @param likeStr
     * @param escapeStr
     * @return String
     */
    public static String escapeSQLLike(String likeStr, String escapeStr)
    {
      String str = StringUtils.replace(likeStr, escapeStr, escapeStr + escapeStr);
      str = StringUtils.replace(str, "_", escapeStr + "_");
      str = StringUtils.replace(str, "%", escapeStr + "%");
      str = StringUtils.replace(str, "[", escapeStr + "[");
      str = StringUtils.replace(str, "]", escapeStr + "]");
      str = StringUtils.replace(str, "'", "''");
      str = StringUtils.replace(str, "-", escapeStr + "-");
      return str;
    }
    
    /**
	 * @method:changeSpecialCharacter
	 * @description:轉化特殊字符
	 * @author:zn.xie(F1334993)  
	 * @date:2018-11-29
	 * @param str
	 * @return
	 */
	public static String changeSpecialCharacter(String str){
		String result = str;
		if (StringUtils.isEmpty(str)) return null;
		result = result.replace("'", "\\'");
		result = result.replace("\"", "\\\"");
		return result;
	}
	
	/**
	 * @method:changeSpecialCharacter
	 * @description:轉化特殊字符
	 * @author:zn.xie(F1334993)  
	 * @date:2018-11-29
	 * @param str
	 * @return
	 */
	public static String changeSpecialCharacter(Object obj){
		if (null == obj) return null;
		String result = String.valueOf(obj);
		result = result.replace("'", "\\'");
		result = result.replace("\"", "\\\"");
		return result;
	}
}
