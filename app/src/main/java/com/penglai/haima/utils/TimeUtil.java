package com.penglai.haima.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 作者：flyjiang
 * 说明: 
 */
public class TimeUtil {
    /**
     * 获取当前手机时间
     *
     * @return 当前手机时间
     */
    public static String getPhoneTime() {
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault())
                .format(new Date());
    }

    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     *
     * @param timeStr 时间戳
     * @return
     */
    public static String getDistanceNow(Long timeStr) {
        StringBuffer sb = new StringBuffer();
        long time = System.currentTimeMillis() - (timeStr);
        long mill = (long) Math.ceil(time / 1000);//秒前
        long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前
        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时
        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前
        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }

    /**
     * 将中文时间计算为距离现在多久
     *
     * @param timeStr 处理前的时间,如:2015-5-28-11:30:46
     * @return 处理后的时间
     */
    public static String getDistanceNow(String timeStr) {
        if (TextUtils.isEmpty(timeStr) || strToLong(timeStr) == 0) {
            return timeStr;
        }
        return getDistanceNow(strToLong(timeStr));
    }

    /**
     * 字符串时间转long
     *
     * @param time 需要转换的时间 如:2015-5-28 11:57:15
     * @return 转换后的时间
     */
    public static long strToLong(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt2 = sdf.parse(time);
            //继续转换得到long型
            return dt2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * long类型时间转字符串
     *
     * @param time long类型的时间
     * @return 处理后的字符串
     */
    public static String longToStr(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
    }
    /**
     * 将毫秒数换算成x天x时x分x秒x毫秒
     *
     * @param ms
     * @return
     */
    public static String[] msToFormat(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day;
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        String[] strs = new String[5];
        strs[0] = strDay;
        strs[1] = strHour;
        strs[2] = strMinute;
        strs[3] = strSecond;
        strs[4] = strMilliSecond;
        // return strDay + " " + strHour + ":" + strMinute + ":" + strSecond +
        // " " + strMilliSecond;
        return strs;
    }
}
