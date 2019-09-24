package com.penglai.haima.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 作者：flyjiang
 * 说明: 用于获取手机各类信息
 */
public class PhoneUtil {
    /**
     * 获取屏幕宽度
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public static int getScreenWidth(final Context context) {
        return context == null ? -1 : context.getResources().getDisplayMetrics().widthPixels;
        //WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //Point size=new Point();
        //windowManager.getDefaultDisplay().getSize(size);
        //return size.x;
    }

    /**
     * 获取屏幕高度
     *
     * @param context 上下文
     * @return 屏幕高度
     */
    public static int getScreenHeight(final Context context) {
        return context == null ? -1 : context.getResources().getDisplayMetrics().heightPixels;
        //WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //DisplayMetrics dm = new DisplayMetrics();
        //windowManager.getDefaultDisplay().getMetrics(dm);
        //return dm.heightPixels;
    }

    /**
     * 获取手机状态栏高度
     *
     * @param context 上下文
     * @return 手机状态栏高度(获取失败返回-1)
     */
    public static int getStatusHeight(final Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context == null ? -1 : context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取手机屏幕密度(0.75 / 1.0 / 1.5)
     *
     * @param context 上下文
     * @return 手机屏幕密度
     */
    public static float getScreenDensity(final Context context) {
        return context == null ? 1 : context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕密度DPI
     * ldpi 低密度 120dpi
     * mdpi 中密度 160dpi
     * hdpi 高密度 240dpi
     * xhdpi 超高密度 320dpi
     * xxhdpi 超超高密度 480dpi
     * xxxhdpi 超超超高密度 640dpi
     *
     * @param context 上下文
     * @return 屏幕密度DPI
     */
    public static int getScreenDensityDpi(final Context context) {
        return context == null ? 1 : context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取手机国际移动客户识别码(IMEI)
     *
     * @param context 上下文
     * @return IMEI
     */
   public static String getPhoneIMEI(final Context context) {
        if (context == null) {
            return "";
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager == null ? "" : telephonyManager.getDeviceId();
    }

    /**
     * 获取手机国际移动台设备识别码(IMSI)
     *
     * @param context 上下文
     * @return IMSI
     */
    public static String getPhoneIMSI(final Context context) {
        if (context == null) {
            return "";
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager == null ? "" : telephonyManager.getSubscriberId() == null ? "" : telephonyManager.getSubscriberId();
    }

    /**
     * 获取手机SIM卡型号(中国移动/中国联通/中国电信)
     *
     * @param context 上下文
     * @return 中国移动/中国联通/中国电信
     */
    public static String getSIMModel(final Context context) {
        if (context == null) {
            return "获取失败";
        }
        String IMSI = getPhoneIMSI(context);
        if (TextUtils.isEmpty(IMSI)) {
            return "未插SIM卡";
        }
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            IMSI = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            IMSI = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            IMSI = "中国电信";
        } else {
            IMSI = "未知运营商";
        }
        return IMSI;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机SDK版本
     *
     * @return 手机SDK版本
     */
    public static int getPhoneSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取安卓版本
     *n
     *
     *
     * @return 手机安卓版本
     */
    public static String getPhoneAndroidVERSION() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机其他不常用信息
     *
     * @return 手机不常用信息
     */
    public static String getPhoneOtherInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\t\t\t\t").append("BOARD:").append(Build.BOARD)
                .append("\n\t\t\t\t").append("BOOTLOADER:").append(Build.BOOTLOADER)
                .append("\n\t\t\t\t").append("BRAND:").append(Build.BRAND)
                .append("\n\t\t\t\t").append("DEVICE:").append(Build.DEVICE)
                .append("\n\t\t\t\t").append("DISPLAY:").append(Build.DISPLAY)
                .append("\n\t\t\t\t").append("FINGERPRINT:").append(Build.FINGERPRINT)
                .append("\n\t\t\t\t").append("HARDWARE:").append(Build.HARDWARE)
                .append("\n\t\t\t\t").append("HOST:").append(Build.HOST)
                .append("\n\t\t\t\t").append("ID:").append(Build.ID)
                .append("\n\t\t\t\t").append("MANUFACTURER:").append(Build.MANUFACTURER)
                .append("\n\t\t\t\t").append("PRODUCT:").append(Build.PRODUCT)
                .append("\n\t\t\t\t").append("SERIAL:").append(Build.SERIAL)
                .append("\n\t\t\t\t").append("TAGS:").append(Build.TAGS)
                .append("\n\t\t\t\t").append("TIME:").append(Build.TIME)
                .append("\n\t\t\t\t").append("TYPE:").append(Build.TYPE)
                .append("\n\t\t\t\t").append("UNKNOWN:").append(Build.UNKNOWN)
                .append("\n\t\t\t\t").append("USER:").append(Build.USER);
        return builder.toString();
    }
}
