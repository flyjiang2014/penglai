package com.penglai.haima.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.penglai.haima.R;
import com.penglai.haima.utils.SharepreferenceUtil;
import com.penglai.haima.utils.ToastUtil;
import com.penglai.haima.widget.loading.LoadingLayout;

/**
 * 作者：flyjiang
 * 说明: 基于V4包的Fragment，为了使用FragmentPagerAdapter
 */
public abstract class BaseFragmentV4 extends Fragment {
    protected View rootView;
    protected Context mContext;
    protected boolean isVisible; //是否可见
    protected boolean isPrepared;  //View是否已加载完毕
    //  protected boolean isFirst = true;//是否第一次加载数据,为false时，切换不在重新加载数据
    //  protected int visibleTimes =0; //被可见的次数
    /**
     * 页面加载过程布局
     */
    protected LoadingLayout mLoadingLayout;
    /**
     * 是否使用loading框架
     */
    private boolean isUseLoading = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = getActivity().getApplicationContext();//使用整个应用的上下文对象
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = initView(inflater);
        }
        if (isUseLoading && mLoadingLayout == null) {
            mLoadingLayout = (LoadingLayout) inflater.inflate(R.layout.loading_layout, null);
            mLoadingLayout.addView(rootView, 0); //自定义的界面加载到最底层
            mLoadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() { //load点击重试功能
                @Override
                public void onReload(View v) {
                    reLoadData();
                }
            });
        }
        return isUseLoading?mLoadingLayout:rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    public void lazyLoad(){
        if (!isPrepared || !isVisible) {
            return;
        }
        initData();
        // isFirst = false; || !isFirst
    }

    /**
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    /**
     * Toast的显示(默认位置)
     *
     * @param message 需要显示的信息
     */
    public void showToast(CharSequence message) {
        ToastUtil.showToast(message);
    }

    public String getUserMobile() {
        return SharepreferenceUtil.getString(Constants.MOBILE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isVisible = false;
        isPrepared = false;
        if (isUseLoading) {
            if (mLoadingLayout != null && mLoadingLayout.getParent() != null) {
                ((ViewGroup) mLoadingLayout.getParent()).removeView(mLoadingLayout);
            }
        } else {
            if (rootView != null && rootView.getParent() != null) {
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            }
        }
    }

    /**
     * 子类实现初始化View操作
     */
    protected abstract View initView(LayoutInflater inflater);

    /**
     * 子类实现初始化View本地数据初始化
     */
    protected abstract void initViewData();

    /**
     * 子类实现初始化数据操作(子类自己调用)
     */
    public abstract void initData();

    /**
     * 设置是否使用loading框架
     */
    public void setUseLoading(boolean useLoading) {
        isUseLoading = useLoading;
    }

    public LoadingLayout getmLoadingLayout() {
        return mLoadingLayout;
    }

    /**
     * 重试处理，需重写的处理方法
     */
    public void reLoadData() {
    }

    /**
     * 获取RecycleView的emptyView
     *
     * @return
     */
    public View getEmptyView() {
        return LayoutInflater.from(mContext).inflate(R.layout.recyclerview_empty_view, null);
    }
}
