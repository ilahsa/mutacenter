package com.ifeng.ad.mutacenter.common.utils;


import com.ifeng.ad.mutacenter.Constant;

import java.util.Iterator;

/**
 * 项目描述: String工具类（对SpringUtils做一致处理）
 */
public class StringUtils {

    public static final String EMPTY = "";

    /**
     * 功能描述: 判断对象是否为空
     *
     * @param str 字符串
     * @return true：空，false：非空
     */
    public static boolean isEmpty(String str) {
        return org.springframework.util.StringUtils.isEmpty(str);
    }

    /**
     * 功能描述: 判断对象是否为空
     *
     * @param str 字符串
     * @return true：非空，false：空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 功能描述: 获得非空字符串
     *
     * @param str 字符串
     * @return 非空字符串
     */
    public static String getNoneNullString(String str) {
        return getNoneNullString(str, "");
    }

    /**
     * 功能描述: 获得非空字符串
     *
     * @param str        字符串
     * @param defaultVal 默认值（当为null时返回默认值）
     * @return 非空字符串
     */
    public static String getNoneNullString(String str, String defaultVal) {
        if (str == null) {
            return defaultVal;
        }

        return str;
    }

    /**
     * 功能描述: 字符串首字母转换为大写
     *
     * @param str 输入字符串
     * @return 首字母转大写后的字符串
     */
    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1))
                .toString();
    }

    /**
     * 功能描述: 将一个数组按照指定的连接符拼接成一个字符串
     *
     * @param array      数组
     * @param separator  连接符
     * @param startIndex 起始索引
     * @param endIndex   结束索引
     * @return
     */
    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        }
        int noOfItems = (endIndex - startIndex);
        if (noOfItems <= 0) {
            return EMPTY;
        }

        StringBuilder buf = new StringBuilder(noOfItems * 16);

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 功能描述: 将一个数组按照指定的连接符拼接成一个字符串(重载)
     *
     * @param array     数组
     * @param separator 连接符
     * @return
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        return join(array, separator, 0, array.length);
    }

    /**
     * 功能描述: 将集合按照指定的连接符拼接成一个字符串
     *
     * @param iterator
     * @param separator
     * @return
     */
    public static String join(Iterator<?> iterator, String separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return first == null ? "" : first.toString();
        }

        StringBuilder buf = new StringBuilder(256);
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            buf.append(separator);
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    /**
     * 功能描述: 将集合按照指定的连接符拼接成一个字符串(重载)
     *
     * @param iterable
     * @param separator
     * @return
     */
    public static String join(Iterable<?> iterable, String separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }


    /**
     * 功能描述: 截取最后一个/之后的字符串
     *
     * @param str
     * @return
     */
    public static String afterSlashString(String str) {
        return str.substring(str.lastIndexOf("/") + 1);
    }

    /**
     * 功能描述: 字段名加下划线
     *
     * @param s
     * @return
     */
    public static String toMapFieldName(String s) {
        char c;
        StringBuffer str = new StringBuffer(Constant.WORD_EMPTY);
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (Character.isLowerCase(c)) {
                str.append(c);
            } else {
                if (i != 0) {
                    str.append("_");
                }
                str.append(Character.toLowerCase(c));
            }
        }
        return str.toString();
    }
}
