package com.penglai.haima.utils;

import android.app.Activity;
import com.penglai.haima.ui.index.MainActivity;
import java.util.Stack;

/**
 * 作者：flyjiang
 * 说明: Activity管理类
 */
public class ActivityManager {
    //Activity堆栈管理
    public static Stack<Activity> activityStack = new Stack<>();

    /**
     * add Activity 添加Activity到栈
     */
    public static void addActivity(Activity activity) {
        activityStack.push(activity);
    }


    /**
     * 结束指定的Activity
     */
    public static void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);

        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                removeActivity(activity);
                activity.finish();
                break;
            }
        }
    }

    /**
     * 获取栈中某个Activity
     */
    public static Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 结束除主页外的Activity
     */
    public static void finishAllActivityWithoutMain() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i) && !(activityStack.get(i) instanceof MainActivity)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
}
