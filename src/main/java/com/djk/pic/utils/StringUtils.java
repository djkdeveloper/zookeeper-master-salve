package com.djk.pic.utils;

/**
 * StringUtils
 *
 * @author djk
 * @date 2016/4/27
 */
public class StringUtils {

    /**
     * 判断是否是数字
     *
     * @param str 传入的字符
     * @return 如果是数字 返回true    否则返回false   注意传入如果为空 则返回false
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }


    /**
     * 判断是否为空
     *
     * @param str 传入的字符
     * @return 如果为空 返回true 否则返回false
     */
    public static boolean isEmpty(String str) {
        return org.springframework.util.StringUtils.isEmpty(str);
    }

}
