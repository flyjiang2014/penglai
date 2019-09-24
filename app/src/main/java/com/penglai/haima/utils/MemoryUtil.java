package com.penglai.haima.utils;

/**
 * 作者：flyjiang
 * 说明: 用于获取APP内存信息
 */
public class MemoryUtil {
    /**
     * 获取APP最大可用内存
     *
     * @return APP最大可用内存
     */
    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }
}
