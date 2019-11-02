package com.penglai.haima.base;

import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * 作者：flyjiang
 * 说明: View基类
 */
public abstract class BaseView extends LinearLayout implements BaseInitializeStep {

    private BaseActivity mActivity;
    protected String tag = "";

    public BaseView(BaseActivity activity) {
        super(activity);
        this.mActivity = activity;
        this.tag = mActivity.TAG;
    }

    public void init(int layoutId) {
        LayoutInflater.from(mActivity).inflate(layoutId, this);
        initData();
        initView();
        initViewData();
        initViewListener();
    }
    public BaseActivity getmActivity() {
        return this.mActivity;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public BaseActivity getActivity() {
        return this.mActivity;
    }
}
