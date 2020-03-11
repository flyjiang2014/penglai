package com.penglai.haima.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.penglai.haima.base.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：flyjiang
 * 说明: SharedPreferences数据存取工具类
 */
public class SharepreferenceUtil {
    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;

    /**
     * 初始化SharedPreferences
     *
     * @param context 上下文
     */
    public static void init(final Context context) {
        if (context != null && mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
            mEditor = mSharedPreferences.edit();
        }
    }

    /**
     * 清除保存的信息
     *
     * @return 成功返回true，失败返回false
     */
    public static boolean clearSharedPreferences() {
        if (mEditor == null) {
            return false;
        } else {
            mEditor.clear().commit();
            return true;
        }
    }

    /**
     * 移除保存的值
     *
     * @param key 保存值对应的key
     * @return 成功返回true，失败返回false
     */
    public static boolean removeKeyValue(String key) {
        if (mEditor == null || TextUtils.isEmpty(key)) {
            return false;
        }
        mEditor.remove(key).commit();
        return true;
    }

    /**
     * 保存字符串
     *
     * @param key   保存对应的key值
     * @param value 需要保存的字符串
     * @return 成功返回true, 失败返回false
     */
    public static boolean saveString(String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value) || mEditor == null) {
            return false;
        }
        mEditor.putString(key, value).commit();
        return true;
    }

    /**
     * 获取保存的字符串
     *
     * @param key 字符串对应的key值
     * @return 有对应的字符串返回对应的字符串，没有则返回空字符
     */
    public static String getString(String key) {
        return TextUtils.isEmpty(key) || mSharedPreferences == null ? getDefaultString(key) : mSharedPreferences.getString(key, getDefaultString(key));
    }

    /**
     * 保存boolean 值
     */
    public static boolean saveBoolean(String key, Boolean value) {
        if (TextUtils.isEmpty(key) || mEditor == null) {
            return false;
        }
        mEditor.putBoolean(key, value).commit();
        return true;
    }

    public static boolean getBoolean(String key) {
        return TextUtils.isEmpty(key) || mSharedPreferences == null ? false : mSharedPreferences.getBoolean(key, false);
    }

    /**
     * 集合保存
     *
     * @param key  保存对应的key值
     * @param list 需要保存的集合
     * @return 成功返回true, 失败返回false
     */
    public static boolean saveListData(String key, List list) {
        if (TextUtils.isEmpty(key) || list == null || mEditor == null) {
            return false;
        }
        Gson gson = new Gson();
        String data = gson.toJson(list);
        mEditor.putString(key, data).commit();
        return true;
    }

    /**
     * 获取集合
     *
     * @param key 保存对应的key值
     */
    public static List getListData(String key) {
        if (TextUtils.isEmpty(key) || mSharedPreferences == null) {
            return new ArrayList();
        }
        String data = mSharedPreferences.getString(key, "");
        if (TextUtils.isEmpty(data)) {
            return new ArrayList();
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    private static String getDefaultString(String key) {
        if (key.equals(Constants.CURRENT_CITY)) {
            return "无锡市";
        } else {
            return "";
        }
    }
}
