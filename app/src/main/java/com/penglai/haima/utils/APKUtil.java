package com.penglai.haima.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import in.srain.cube.util.CLog;

/**
 * 作者：flyjiang
 * 说明: PAK操作工具类
 */
public class APKUtil {
    private static final String TAG = "APKUtil";

    /**
     * APK的安装
     *
     * @param context  上下文
     * @param apk_file 需要安装的APK文件
     */
    public static void installAPK(final Context context, final File apk_file) {
        if (context == null || apk_file == null || !apk_file.exists()) {
            CLog.e(TAG, "安装环境或安装文件存在问题导致安装APK失败！");
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apk_file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
