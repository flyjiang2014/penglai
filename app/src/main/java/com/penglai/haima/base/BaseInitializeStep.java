package com.penglai.haima.base;

/**
 * 作者：flyjiang
 * 时间: 2017/11/07 16:30
 * 说明: 初始化实现方法接口
 */
public interface BaseInitializeStep {

    void initData();

    void initView();

    void initViewData();

    void initViewListener();
}
