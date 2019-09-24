package com.penglai.haima.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

/**
 * 作者：flyjiang
 * 说明: 文本填充方式工具类,可以改变字符串部分文字颜色及前景、背景色
 * 用法:textStyle.setString("共有" + count + "人关注");
 * textStyle.setForegroundColor(mContext.getResources().getColor(R.color.yellow), 2, 2 + count.length());
 * 可参考http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0120/2335.html
 */
public class TextStyleUtil {

    private SpannableString spannableString;

    /**
     * 无参构造方法
     */
    public TextStyleUtil() {
    }

    /**
     * 带参构造方法
     *
     * @param str 需要操作的字符串
     */
    public TextStyleUtil(String str) {
        if (!TextUtils.isEmpty(str)) {
            spannableString = new SpannableString(str);
        }
    }

    /**
     * 无参构造后设置字符串
     *
     * @param str 需要操作的字符串
     */
    public void setString(String str) {
        if (!TextUtils.isEmpty(str)) {
            spannableString = new SpannableString(str);
        }
    }

    /**
     * 获取操作后的字符串
     *
     * @return 操作后的字符串
     */
    public SpannableString getSpannableString() {
        return spannableString;
    }

    /**
     * 设置绝对字体大小
     *
     * @param size  需要的大小
     * @param start 开始位置
     * @param end   结束位置
     * @param dp    是否为DP值
     * @return 设置后的字符串
     */
    public TextStyleUtil setAbsoluteSize(int size, int start, int end, boolean dp) {
        if (spannableString == null) {
            return this;
        }
        spannableString.setSpan(new AbsoluteSizeSpan(size, dp), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置相对现在字体大小
     *
     * @param size  设置和现在大小的显示比例(0.5f表示默认字体大小的一半)
     * @param start 开始位置
     * @param end   结束位置
     * @return 设置后的字符串
     */
    public TextStyleUtil setRelativeSize(float size, int start, int end) {
        if (spannableString == null) {
            return this;
        }
        spannableString.setSpan(new RelativeSizeSpan(size), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置前景色
     *
     * @param Color 需要设置的颜色
     * @param start 开始位置
     * @param end   结束位置
     * @return 设置颜色后的字符串
     */
    public TextStyleUtil setForegroundColor(int Color, int start, int end) {
        if (spannableString == null) {
            return this;
        }
        spannableString.setSpan(new ForegroundColorSpan(Color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置背景色
     *
     * @param Color 需要设置的颜色
     * @param start 开始位置
     * @param end   结束位置
     * @return 设置颜色后的字符串
     */
    public TextStyleUtil setBackgroundColor(int Color, int start, int end) {
        if (spannableString == null) {
            return this;
        }
        spannableString.setSpan(new BackgroundColorSpan(Color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置粗体
     *
     * @param textView 需要设置的TextView
     * @param isBold   是否设置粗体
     */
    public static void setFakeBold(TextView textView, boolean isBold) {
        TextPaint tp = textView.getPaint();
        tp.setFakeBoldText(isBold);
    }

    /**
     * 设置下划线
     *
     * @param start 开始位置
     * @param end   结束位置
     * @return 设置下划线后的字符串
     */
    public TextStyleUtil setUnderlineSpan(int start, int end) {
        if (spannableString == null) {
            return this;
        }
        spannableString.setSpan(new UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置删除线
     *
     * @param start 开始位置
     * @param end   结束位置
     * @return 设置删除线后的字符串
     */
    public TextStyleUtil setStrikethroughSpan(int start, int end) {
        if (spannableString == null) {
            return this;
        }
        spannableString.setSpan(new StrikethroughSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置脚注样式(如CO2,二氧化碳化学名)
     *
     * @param start 开始位置
     * @param end   结束位置
     * @return 设置脚注样式后的字符串
     */
    public TextStyleUtil setSubscriptSpan(int start, int end) {
        if (spannableString == null) {
            return this;
        }
        spannableString.setSpan(new SubscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    /**
     * 设置上标样式(如2^2=4,后面的2在前面2的右上角)
     *
     * @param start 开始位置
     * @param end   结束位置
     * @return 设置上标样式后的字符串
     */
    public TextStyleUtil setSuperscriptSpan(int start, int end) {
        if (spannableString == null) {
            return this;
        }
        spannableString.setSpan(new SuperscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }
}
