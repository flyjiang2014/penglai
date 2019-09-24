package com.penglai.haima.utils;


import com.penglai.haima.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：flyjiang
 * 说明: Activity管理类
 */
public class ActivityUtil {
    /**
     * 所有Activity的集合
     */
    private static List<BaseActivity> mActivityALL = new ArrayList<>();
    /**
     * 可见的Activity
     */
    private static List<BaseActivity> mActivityVISIBLE = new ArrayList<>();
    /**
     * 不可见但是在运行的Activity
     */
    private static List<BaseActivity> mActivityINVISIBLE = new ArrayList<>();

    /**
     * 添加Activity到所有Activity
     *
     * @param activity 需要添加的Activity
     */
    public static void addActivityALL(BaseActivity activity) {
        if (activity != null && !mActivityALL.contains(activity)) {
            mActivityALL.add(activity);
        }
    }

    /**
     * 添加Activity到可见mActivityVISIBLE
     *
     * @param activity 需要添加的Activity
     */
    public static void addActivityVISIBLE(BaseActivity activity) {
        if (activity != null && !mActivityVISIBLE.contains(activity)) {
            mActivityVISIBLE.add(activity);
        }
    }

    /**
     * 添加Activity到不可见mActivityINVISIBLE
     *
     * @param activity 需要添加的Activity
     */
    public static void addActivityINVISIBLE(BaseActivity activity) {
        if (activity != null && !mActivityINVISIBLE.contains(activity)) {
            mActivityINVISIBLE.add(activity);
        }
    }

    /**
     * 移除销毁的Activity
     *
     * @param activity 需要移除的Activity
     */
    public static void removeActivityInALL(BaseActivity activity) {
        // 如果需要移除的CcActivity为空，则不执行
        if (activity == null) {
            return;
        }
        // ALL中移除
        if (mActivityALL.contains(activity)) {
            mActivityALL.remove(activity);
        }
        // 可见中移除
        if (mActivityVISIBLE.contains(activity)) {
            mActivityVISIBLE.remove(activity);
        }
        // 不可见中移除
        if (mActivityINVISIBLE.contains(activity)) {
            mActivityINVISIBLE.remove(activity);
        }
    }

    /**
     * 移除可见的Activity
     *
     * @param activity 需要移除的Activity
     */
    public static void removeActivityInVISIBLE(BaseActivity activity) {
        if (activity != null && mActivityVISIBLE.contains(activity)) {
            mActivityVISIBLE.remove(activity);
        }
    }

    /**
     * 移除不可见的Activity
     *
     * @param activity 需要移除的Activity
     */
    public static void removeActivityInINSIVIBLE(BaseActivity activity) {
        if (activity != null && mActivityINVISIBLE.contains(activity)) {
            mActivityINVISIBLE.remove(activity);
        }
    }

    /**
     * 结束全部Activity
     */
    public static void finishAll() {
        for (BaseActivity activity : mActivityALL) {
            if (activity != null) {
                activity.finish();
            }
        }
        mActivityALL.clear();
        mActivityVISIBLE.clear();
        mActivityINVISIBLE.clear();
    }

    /**
     * 结束指定的Activity
     *
     * @param activity 需要结束的Activity
     */
    public static void finishOneActivity(final BaseActivity activity) {
        if (activity != null) {
            for (BaseActivity baseActivity : mActivityALL) {
                if (baseActivity == activity) {
                    removeActivityInALL(baseActivity);
                    baseActivity.finish();
                    return;
                }
            }
        }
    }
}
