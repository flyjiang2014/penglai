package com.penglai.haima.utils;

import android.text.TextUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：flyjiang
 * 说明: 用于字符串的处理
 */
public class StringUtil {
    /**
     * 转换全角到半角
     *
     * @param s 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String full2Half(final String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }

        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] >= 65281 && c[i] <= 65374) {
                c[i] = (char) (c[i] - 65248);
            } else if (c[i] == 12288) { // 空格
                c[i] = (char) 32;
            }
        }
        return new String(c);
    }

    /**
     * 转换半角到全角
     *
     * @param s 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String half2Full(String s) {
        if (TextUtils.isEmpty(s)) {
            return s;
        }
        char[] c = s.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
            } else if (c[i] >= 33 && c[i] <= 126) {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    /**
     * 提取字符串中的数字为数字数组
     *
     * @param s 需要提取的字符串
     * @return 提取后的数字数组
     */
    public static Long[] fetchNumber(String s) {
        if (TextUtils.isEmpty(s)) {
            return new Long[0];
        }
        List<Long> longNumbers = new ArrayList<Long>();
        char[] chs = s.toCharArray();
        long number = -1;
        boolean isNumber = false;
        for (int i = 0; i < chs.length; i++) {
            if (chs[i] >= 48 && chs[i] <= 57) {
                if (number < 0) {
                    number = 0;
                }
                number = number * 10 + chs[i] - 0x30;
                isNumber = true;
            } else {
                if (number >= 0 && isNumber) {
                    longNumbers.add(number);
                    number = -1;
                    isNumber = false;
                }
            }
        }
        if (number >= 0) {
            longNumbers.add(number);
        }
        return (Long[]) longNumbers.toArray(new Long[longNumbers.size()]);
    }

    /**
     * 过滤掉回车和换行
     *
     * @param oldString 需要过滤的字符串
     * @return 过滤后字符串
     */
    public static String replaceEnter(String oldString) {
        if (TextUtils.isEmpty(oldString)) {
            return "";
        }
        Pattern pattern = Pattern.compile("(\r\n|\r|\n|\n\r)");
        //正则表达式的匹配一定要是这样，单个替换\r|\n的时候会错误
        Matcher matcher = pattern.matcher(oldString);
        String newString = matcher.replaceAll("<br>");
        return newString;
    }

    /**
     * 转成分段时间
     *
     * @param str 8：30-15：30
     * @param spaceTime 30min
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
        if (start > end) {
            end += 24 * 60;
            int zstart = 0;
            int zend = end;
            if (flag) {
                // 每半个小小时添加一次
                for (int i = 0; i <= (zend - zstart) / spaceTime; i++) {
                    tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 % 24) + ":"
                            + String.format("%1$02d", (start + i * spaceTime) % 60));
                }
            }
            for (int i = 0; i <= (24 - start) / spaceTime; i++) {
                tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 % 24) + ":"
                        + String.format("%1$02d", (start + i * spaceTime) % 60));
            }
        } else {
            // if(end == 24 * 60){
            // end = 23*60+30;
            // // 每半个小小时添加一次
            // for (int i = 0; i <= (end - start) / spaceTime; i++) {
            // tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 %
            // 24) + ":" + String.format("%1$02d", (start + i * spaceTime) %
            // 60));
            // }
            // }else {
            // 每半个小小时添加一次
            for (int i = 0; i <= (end - start) / spaceTime; i++) {
                int time = start + i * spaceTime;
                if (time < end) {
                    tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 % 24) + ":"
                            + String.format("%1$02d", (start + i * spaceTime) % 60));
                }
            }
            // }
        }
        return tmp;
    }

    /**
     * 验证手机号码手机号是否合法
     * 只验证1抬头且11位数字
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        //  Pattern pattern = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(17[0-3,5-8])|(18[0-9]))(\\d){8}$");
        Pattern pattern = Pattern.compile("^(1)(\\d){10}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static void filtNull(TextView textView, String s) {
        if (s != null) {
            textView.setText(s);
        } else {
            textView.setText(filtNull(s));
        }
    }


    //判断过滤单个string为null
    public static String filtNull(String s) {
        if (s != null) {
            return s;
        } else {
            s = "null";
        }
        return s;
    }
}
