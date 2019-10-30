package com.penglai.haima.utils;

import android.content.Context;

public class ScreenUtil {
    private static int width; // 屏幕宽度（像素）
    private static int height; // 屏幕高度（像素）
    private static float density; // 屏幕密度（0.75 / 1.0 / 1.5）
    private static int densityDpi; // 屏幕密度DPI（120 / 160 / 240）

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }
}
