package com.penglai.haima.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by ${flyjiang} on 2017/11/23.
 * 文件说明：设置ImgView宽高比例
 */

public class ViewHWRateUtil {

    /**
     * @param view
     * @param width 给定的控件高度
     * @param rate 宽高比
     */
    public static void setHeightWidthRate(View view, int width, double rate) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = (int)( params.width/rate);
        view.setLayoutParams(params);
    }

    /**
     * 宽度为屏幕宽度时设置参数
     * @param context
     * @param rate 宽高比
     */
    public static void setHeightWidthRate(Context context, View view, double rate) {
        setHeightWidthRate(view, PhoneUtil.getScreenWidth(context), rate);
    }
}
