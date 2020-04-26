/**
 * 
 */
/**
 * @author DELL
 *
 */
package com.arvato.core.utils;

/**
 * String工具类
 * @author DELL
 *
 */
public class StrUtil{
    /**
	 * 空格检测
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
        if(StrUtil.isEmpty(str)) {
            return true;
        }
        for(int i = 0; i < str.length(); i++ ) {
            if(!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
	}
	/**
	 * 空值检测
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.length() == 0;
	}
	
    /**
     * 判断字符串是否为空 如果为空返回true 如果不为空返回false
     * 
     * @param str
     *            传入要判断的字符串
     * @return [true | false]
     */
    public static boolean isNull(String str) {
        // System.out.println("!!!");
        if (null == str || str.length() <= 0 || str.equals("null")) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否不为空,如果不为空返回true 如果为空false
     * 
     * @param str
     *            传入要判断的字符串
     * @return [true | false]
     */
    public static boolean isNotNull(String str) {
        // System.out.println("!!!");
        if (null == str || str.length() <= 0 || str.equals("null")) {
            return false;
        }
        return true;
    }

    /**
     * 检测字符串,如果字符串为null的话，将null替换成 ""
     * 
     * @param str
     *            要替换的字符串
     * @return ["" | str]
     */
    public static String isCheckNull(String str) {
        // System.out.println("!!!");
        if (null == str || str.length() <= 0 || str.equals("null") || str.equals("NULL")) {
            return "";
        }
        str = str.replaceAll("NULL", "").replaceAll("null", "");
        return str;
    }
}