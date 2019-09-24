package com.penglai.haima.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * about the time util.
 */

public class CustomDateUtil {
    private static SimpleDateFormat formatHM = new SimpleDateFormat("HH:mm");
    private static DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat dateFormatl = new SimpleDateFormat("yyyy/MM/dd");
    private static DateFormat dateTimeFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public CustomDateUtil() {
    }

    public static final DateFormat getDateTimeFormat2() {
        return dateTimeFormat2;
    }

    public static final DateFormat getDateTimeFormat() {
        return dateTimeFormat;
    }

    public static final Date addDate(Date oldDate, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    public static String formatDateTime(Date date) {
        if (date == null)
            return "";
        return dateTimeFormat.format(date);
    }

    /**
     * 返回的形式yyyy-MM-dd
     *
     * @param date Date 格式的数据
     * @return
     */
    public static String getCurrentDate(Date date) {
        if (null == date)
            return "";
        String time = dateFormat.format(date);
        return time;
    }

    /**
     * return the current time . the format is: HH:MM
     *
     * @return
     */
    public static String getNowTimeHM() {
        Date date = new Date();
        String time = formatHM.format(date);
        return time;
    }

    /**
     * return the current time . the format is: HH:MM
     *
     * @param date : a sample of Date object.
     * @return
     */
    public static String getNowTimeHM(Date date) {
        if (null == date)
            return "";
        String time = formatHM.format(date);
        return time;
    }

    public static String getCurrentTimeYMDHMS(Date date) {
        String time = dateTimeFormat.format(date);
        return time;
    }

    public static final Date addTime(Date oldDate, int year, int month, int day, int hour,
                                     int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static String formatDate(Date date) {
        if (date == null)
            return "";
        return dateFormat.format(date);
    }

    /**
     * return type is: yyyy-MM-dd HH:mm
     *  String转Date
     *
     * @param dateStr
     * @return
     */
    public static final Date parseDateTime2(String dateStr) {
        if ((dateStr == null) || (dateStr.length() == 0))
            return null;
        try {
            return dateTimeFormat2.parse(dateStr);
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return return the date of today ,the format is yyyy-mm-dd
     */
    public static String getNowDate() {
        Date today= new Date();
        String todayDay = dateFormat.format(today);
        return todayDay;
    }

    /**
     *
     * @param nowTime nowTime is the time at present, the format is HH:mm:ss
     * @return return the datetime of today .the formate is:
     * "yyyy-MM-dd HH:mm:ss"
     */
    public static String getTodayDateTime(String nowTime) {
        String todayDate = CustomDateUtil.getNowDate();
        if ((!"".equals(todayDate)) && (null != todayDate)) {
            String todayDateTime = todayDate + " " + nowTime;
            return todayDateTime;
        }
        return nowTime;

    }

    public static final Date parseDateTime(String dateStr) {
        if ((dateStr == null) || (dateStr.length() == 0))
            return null;
        try {
            return dateTimeFormat.parse(dateStr);
        } catch (ParseException e) {
            // e.printStackTrace();
        }
        return null;
    }

    /*
     * dateStr format : yy-mm-dd hh:mm:ss
     */
    public static final String allDateToHm(String dateStr) {
        if ((dateStr == null) || (dateStr.length() == 0))
            return null;
        Date tempDate;
        try {
            tempDate = dateTimeFormat.parse(dateStr);
            return formatHM.format(tempDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /*
     * dateStr format : yy-mm-dd
     */
    public static final String allDateToYY(String dateStr) {
        if ((dateStr == null) || (dateStr.length() == 0))
            return null;
        Date tempDate;
        try {
            tempDate = dateTimeFormat.parse(dateStr);
            return dateFormat.format(tempDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static long getTimestamp(String nowTime) {
        long timestamp = 0;
        try {
            Date d = dateTimeFormat.parse(nowTime);
            timestamp = d.getTime() / 1000;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timestamp;

    }

    /**
     * 获取某天是星期几,参数格式是：2012-03-12
     *
     * @param preString 前缀字符串，如：周*，星期*
     * @param pTime
     * @return 返回类型为“一”、“二”……
     */
    public static String getWeekName(String preString, String pTime) {
        String Week = preString;
        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }

        return Week;
    }

    /**
     * 参数是秒
     *
     * @param data
     * @return
     */
    public static String getTime(String data) {
        long time = Long.parseLong(data);// 秒
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(time * 1000);
        return dateFormat.format(gc.getTime());
    }

    /**
     * 参数是秒
     *
     * @param data
     * @return
     */
    public static String getTimel(String data) {
        long time = Long.parseLong(data);// 秒
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(time * 1000);
        return dateFormatl.format(gc.getTime());
    }

    public static String gettimeHHMM(String data) {
        long time = Long.parseLong(data);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(time * 1000);
        return dateTimeFormat2.format(gc.getTime());
    }

    /**
     * 从date中获取年year
     *
     * @param date
     * @return
     */
    public static int getYearByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 从date中获取月
     *
     * @param date
     * @return month日期，非下标
     */
    public static int getMonthByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 从date中获取日
     *
     * @param date
     * @return
     */
    public static int getDayByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 日期加减
     *
     * @param date   格式 yyyy-mm-dd
     * @param number
     * @throws ParseException
     */
    public static String modifyDate(String date, int number) throws ParseException {
        Calendar calendar = Calendar.getInstance();

        Date tempDate = dateFormat.parse(date);
        calendar.setTime(tempDate);
        calendar.add(Calendar.DAY_OF_YEAR, number);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }

    public static String gettimeHHmmss(String data) {
        long time = Long.parseLong(data);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(time * 1000);
        return dateTimeFormat.format(gc.getTime());
    }

    public static int compare_date(String DATE1, String DATE2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间和当前时间比较，
     */
    @SuppressLint("SimpleDateFormat")
    public static long diffTime2Now(String tempTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(new Date());
        // tempTime = sdf.format(tempTime);
        long days = 0;
        try {
            Date d1 = sdf.parse(tempTime);
            Date dnow = sdf.parse(nowTime);

            long diff = d1.getTime() - dnow.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;

    }


    /**
     * 时间和当前时间比较，
     */
    @SuppressLint("SimpleDateFormat")
    public static long diffTime2Now01(String tempTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = sdf.format(new Date());
        // tempTime = sdf.format(tempTime);
        long days = 0;
        try {
            Date d1 = sdf.parse(tempTime);
            Date dnow = sdf.parse(nowTime);

            long diff = d1.getTime() - dnow.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;

    }

    /**
     * 获取某日期｛date｝前几天{index}的日期数
     *
     * @param date  从某一天开始，格式"yyyy-MM-dd"
     * @param index 获取几天的数据
     * @return
     */
    public static String[][] getLastDateByDays(String date, int index) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[][] strings = new String[index][2];
        Calendar c = Calendar.getInstance();
        Date dNow = new Date();

        try {
            dNow = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < index; i++) {
            c.setTime(dNow);
            c.add(Calendar.DATE, -(index - i - 1));
            Date newDate = c.getTime();
            String someDay = sdf.format(newDate);
            strings[i][0] = someDay;

            if (isTodayStr(someDay)) {
                strings[i][1] = "今天";
            } else if (isYesterdayStr(someDay)) {
                strings[i][1] = "昨天";
            } else {
                strings[i][1] = getWeekName("周", someDay);
            }

        }
        return strings;
    }


    /**
     * 是否是今天
     *
     * @param strDate "yyyy-MM-dd"格式的时间
     * @return
     */
    public static boolean isTodayStr(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dNow = sdf.parse(strDate);
            return isToday(dNow);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 判断日期是否在当前日期之前
     *
     * @return
     */
    public static boolean dateIsBeforeNowDate(String tempDate) {
//        tempDate = tempDate + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = sdf.format(new Date());

        Calendar cal1 = Calendar.getInstance();
        Calendar cNow = Calendar.getInstance();
        try {
            cal1.setTime(sdf.parse(tempDate));
            cNow.setTime(sdf.parse(nowTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = cal1.compareTo(cNow);
        return result < 0;
    }

    /**
     * 是否是今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(final Date date) {
        return isSomeDay(date, new Date());
    }

    /**
     * 是否是指定日期
     *
     * @param date
     * @param day
     * @return
     */
    public static boolean isSomeDay(final Date date, final Date day) {
        return date.getTime() >= dayBegin(day).getTime()
                && date.getTime() <= dayEnd(day).getTime();
    }

    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    public static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date dayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /**
     * 是否是昨天
     *
     * @param strDate "yyyy-MM-dd"格式的时间
     * @return
     */
    public static boolean isYesterdayStr(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dYestarday = sdf.parse(strDate);
            return isYesterday(dYestarday);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 是否是昨天
     *
     * @param date
     * @return
     */
    public static boolean isYesterday(Date date) {
        return isSomeDay(date, getYesterday());
    }

    /**
     * 是否是昨天
     *
     * @return
     */
    public static Date getYesterday() {
        Calendar current = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();//昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        return yesterday.getTime();
    }

    /**
     * 转成分段时间
     * @param str
     * @return
     */
    public static ArrayList<String> getShowTimeArray(String str, int spaceTime, boolean flag) {
        ArrayList<String> tmp = new ArrayList<String>();
        String[] tmpStr1 = str.trim().split("-");
        String[] tmpStr2 = tmpStr1[0].trim().split(":");
        String[] tmpStr3 = tmpStr1[1].trim().split(":");
        int start = 0;
        int end = 0;
        try {
            start = Integer.parseInt(tmpStr2[0].trim()) * 60 + Integer.parseInt(tmpStr2[1].trim());
            end = Integer.parseInt(tmpStr3[0].trim()) * 60 + Integer.parseInt(tmpStr3[1].trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (start >end) {
            end += 24 * 60;
            int zstart = 0;
            int zend = end;
            if (flag) {
                // 每半个小小时添加一次
                for (int i = 0; i <=((zend - zstart) / spaceTime) ; i++) {
                    tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 % 24) + ":"
                            + String.format("%1$02d", (start + i * spaceTime) % 60));
                }
            }
            for (int i = 0; i <= ((24 - start) / spaceTime); i++) {
                tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 % 24) + ":"
                        + String.format("%1$02d", (start + i * spaceTime) % 60));
            }
        } else if (start <end) {
            for (int i = 0; i <= (end - start) / spaceTime; i++) {
                int time = start + i * spaceTime;
                if (time <=end) {
                    tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 % 24) + ":"
                            + String.format("%1$02d", (start + i * spaceTime) % 60));
                }
            }
        }
        return tmp;
    }
    /**
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
     * @param date String 想要格式化的日期
     * @param oldPattern String 想要格式化的日期的现有格式
     * @param newPattern String 想要格式化成什么格式
     * @return String
     */
    public static String StringPattern(String date, String oldPattern, String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return "";
        if(TextUtils.isEmpty(date)){
            return "";
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern) ;        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern) ;        // 实例化模板对象
        Date d = null ;
        try{
            d = sdf1.parse(date) ;   // 将给定的字符串中的日期提取出来
        }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace() ;       // 打印异常信息
        }
        return sdf2.format(d);
    }

}
