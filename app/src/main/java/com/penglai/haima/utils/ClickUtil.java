package com.penglai.haima.utils;

/**
 * Created by ${flyjiang} on 2016/8/23.
 * 文件说明：防止快速多次点击按钮工具类
 */
public class ClickUtil {
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
