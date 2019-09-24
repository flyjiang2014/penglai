package com.penglai.haima.utils;

import android.content.Context;
import android.view.View;

/**
 * 作者：flyjiang
 * 说明: 用于处理View的测量等
 */
public class ViewUtil {
    /**
     * 根据手机的分辨率从dp的单位转成为px(像素)
     *
     * @param context 上下文
     * @param dpValue 需要转换的DP值
     * @return 转换后的DP值
     */
    public static float dip2px(final Context context, float dpValue) {
        return context == null ? dpValue : (dpValue * context.getResources().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从px(像素)的单位转成为dp
     *
     * @param context 上下文
     * @param pxValue 需要转换的PX值
     * @return 转换后的PX值
     */
    public static float px2dip(final Context context, float pxValue) {
        return context == null || pxValue == 0 ? pxValue : (pxValue / context.getResources().getDisplayMetrics().density);
    }

    /**
     * 根据手机的分辨率从sp的单位转成为px(像素)
     *
     * @param context 上下文
     * @param spValue 需要转换的SP值
     * @return 转换后的PX值
     */
    public static float sp2px(final Context context, float spValue) {
        return context == null ? spValue : spValue * context.getResources().getDisplayMetrics().density;
    }

    /**
     * 描述：根据手机的分辨率从px(像素)的单位转成为sp
     *
     * @param context 上下文
     * @param pxValue 需要转换的PX值
     * @return 转换后的SP值
     */
    public static float px2sp(final Context context, float pxValue) {
        return context == null || pxValue == 0 ? pxValue : pxValue / context.getResources().getDisplayMetrics().density;
    }

    /**
     * 测量view宽度
     *
     * @param view 需要测量的View
     * @return View的宽度
     */
    public static int measureVieWidth(View view) {
        if (view == null) {
            return 0;
        }
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredWidth();
    }

    /**
     * 测量view高度
     *
     * @param view 需要测量的View
     * @return View的高度
     */
    public static int measureViewHeight(View view) {
        if (view == null) {
            return 0;
        }
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
    }

}
