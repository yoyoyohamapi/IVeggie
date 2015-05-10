package com.iveggie.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * StringUtils
 * Desc: 字符串工具类
 * Date: 2015/5/10
 * Time: 19:32
 * Created by: Wooxxx
 */
public class StringUtils {
    /**
     *  将字符串转移成整数.
     *
     * @param num the num
     * @return the int
     */
    public static int toInt(String num) {
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     *  判断字符串是否为null或者为空.
     *
     * @param str the str
     * @return true, if is empty
     */
    public static boolean isEmpty(String str) {
        if (str == null || str == "" || str.trim().equals(""))
            return true;
        return false;
    }

    /**
     *  返回一个StringBuffer对象.
     *
     * @return the buffer
     */
    public static StringBuffer getBuffer() {
        return new StringBuffer(50);
    }

    /**
     *  返回一个StringBuffer对象.
     *
     * @param length the length
     * @return the buffer
     */
    public static StringBuffer getBuffer(int length) {
        return new StringBuffer(length);
    }

    /**
     * 格式一个日期.
     *
     * @param longDate            需要格式日期的长整数的字符串形式
     * @param format            格式化参数
     * @return 格式化后的日期
     */
    public static String getStrDate(String longDate, String format) {
        if (isEmpty(longDate))
            return "";
        long time = Long.parseLong(longDate);
        Date date = new Date(time);
        return getStrDate(date, format);
    }

    /**
     * 格式一个日期.
     *
     * @param time the time
     * @param format            格式化参数
     * @return 格式化后的日期
     */
    public static String getStrDate(long time, String format) {
        Date date = new Date(time);
        return getStrDate(date, format);
    }

    /**
     *  返回当前日期的格式化（yyyy-MM-dd）表示.
     *
     * @return the str date
     */
    public static String getStrDate() {
        SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
        return dd.format(new Date());
    }

    /**
     * 返回当前日期的格式化表示.
     *
     * @param date            指定格式化的日期
     * @param formate            格式化参数
     * @return the str date
     */
    public static String getStrDate(Date date, String formate) {
        SimpleDateFormat dd = new SimpleDateFormat(formate);
        return dd.format(date);
    }

    /**
     * sql特殊字符转义.
     *
     * @param keyWord            关键字
     * @return the string
     */
    public static String sqliteEscape(String keyWord) {
        keyWord = keyWord.replace("/", "//");
        keyWord = keyWord.replace("'", "''");
        keyWord = keyWord.replace("[", "/[");
        keyWord = keyWord.replace("]", "/]");
        keyWord = keyWord.replace("%", "/%");
        keyWord = keyWord.replace("&", "/&");
        keyWord = keyWord.replace("_", "/_");
        keyWord = keyWord.replace("(", "/(");
        keyWord = keyWord.replace(")", "/)");
        return keyWord;
    }

    /**
     * sql特殊字符反转义.
     *
     * @param keyWord            关键字
     * @return the string
     */
    public static String sqliteUnEscape(String keyWord) {
        keyWord = keyWord.replace("//", "/");
        keyWord = keyWord.replace("''", "'");
        keyWord = keyWord.replace("/[", "[");
        keyWord = keyWord.replace("/]", "]");
        keyWord = keyWord.replace("/%", "%");
        keyWord = keyWord.replace("/&", "&");
        keyWord = keyWord.replace("/_", "_");
        keyWord = keyWord.replace("/(", "(");
        keyWord = keyWord.replace("/)", ")");
        return keyWord;
    }

    /**
     * 保留字符数
     *
     * @param str
     *            原始字符串
     * @param length
     *            保留字符数
     * @param isPoints
     *            是否加省略号
     * @return 格式化后的日期
     */
    public static String getStrFomat(String str, int length, boolean isPoints) {
        String result = "";

        if (str.length() > length) {
            result=str.substring(0, length);
            if (isPoints) {
                result = result + "...";
            }
        } else {
            result = str;
        }

        return result;

    }
}
