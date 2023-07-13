package com.ifeng.ad.mutacenter.common.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public final class DateTimeUtils {

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String DATE_FLAG_FORMAT = "yyMMdd";

    /**
     * 日期和时间的格式化方法
     *
     * @param dateTime 要被格式化的{@link Date}
     * @return 格式化为{@link #DEFAULT_DATETIME_FORMAT}的{@link String}
     */
    public static final String formatDateTime(final Date dateTime) {
        if (dateTime == null) {
            return "";
        }

        return new SimpleDateFormat(DEFAULT_DATETIME_FORMAT).format(dateTime);
    }

    /**
     * 拿到当前时间戳，精确到毫秒
     *
     * @return 返回精确到毫秒秒的时间戳
     */
    public static Long getCurrentMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 拿到当前时间戳，精确到秒
     *
     * @return 返回精确到秒的时间戳
     */
    public static Long getCurrentSecond() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间，时间格式是星期的前三位英文字母加当前小时
     *
     * @return 返回星期的前三位英文字母加当前小时的时间
     */
    public static int getTime() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (dayOfWeek - 1) * 24 + hour;
    }

    /**
     * 获取明日凌晨1点的unix时间
     */
    public static long getTomorrow1amUnixTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() / 1000l;
    }

    /**
     * 获取时间标识
     *
     * @return 返回时间标识，例如{@code yyMMdd}
     */
    public static String getDateFlag() {
        return new SimpleDateFormat(DATE_FLAG_FORMAT).format(new Date());
    }

    public static String getDateFlag(Date date) {
        return new SimpleDateFormat(DATE_FLAG_FORMAT).format(date);
    }
    /**
     * 获取当前一天剩下的秒数
     *
     * @return
     */
    public static int getLastSecondsOfTheDay() {
        return (24 * 60 * 60 - LocalDateTime.now().toLocalTime().toSecondOfDay());
    }

}
