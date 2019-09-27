package com.penglai.haima.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.utils.ActivityManager;
import com.penglai.haima.utils.KeyboardUtil;
import com.penglai.haima.utils.PhoneUtil;
import com.penglai.haima.utils.ToastUtil;
import com.penglai.haima.widget.loading.LoadingDialog;
import com.penglai.haima.widget.loading.LoadingLayout;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：flyjiang
 * 时间: 2015/5/26
 * 说明: 基类
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * ━━━━━━感觉萌萌哒━━━━━━
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * TAG
     */
    protected final String TAG = this.getClass().getSimpleName();
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 布局根View
     */
    private RelativeLayout mBaseContainer;
    /**
     * 状态栏颜色
     */
    private int statusColor = -1;
    /**
     * 是否不填充状态栏
     */
    private boolean isNotFillSysState = false;
    /**
     * 是否显示标题
     */
    private boolean isShowTitle = true;
    /**
     * 是否使用loading框架
     */
    private boolean isUseLoading = true;
    /**
     * 屏幕宽度
     */
    protected int mScreentWidth;
    /**
     * 屏幕高度
     */
    protected int mScreentHeight;
    /**
     * 状态栏高度
     */
    protected int mStatusHeight;
    /**
     * 标题布局
     */
    private RelativeLayout mTitleLayout;
    /**
     * 页面加载过程布局
     */
    protected LoadingLayout mLoadingLayout;
    /**
     * 标题左边布局
     */
    protected RelativeLayout mTitleLeftRelativeLayout;
    /**
     * 标题右边布局
     */
    protected RelativeLayout mTitleRightRelativeLayout;
    /**
     * 标题中间布局
     */
    protected RelativeLayout mTitleMiddleRelativeLayout;
    /**
     * 标题左边图片
     */
    private ImageView mTitleLeftImageView;
    /**
     * 标题中间文字
     */
    private TextView mTitleMiddleTextView;
    /**
     * 标题右边图片
     */
    private ImageView mTitleRightImageView;
    /**
     * 标题右边文字
     */
    private TextView mTitleRightTextView;
    /**
     * 四种相对布局布局参数
     */
    protected RelativeLayout.LayoutParams mParamsRelativeMM;
    protected RelativeLayout.LayoutParams mParamsRelativeMW;
    protected RelativeLayout.LayoutParams mParamsRelativeWM;
    protected RelativeLayout.LayoutParams mParamsRelativeWW;

    private Unbinder mUnBinder;
    /**
     * 全局dialog
     */
    protected LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreateBefore();
        super.onCreate(savedInstanceState);
        // 添加当前Activity到所有管理列表
        ActivityManager.addActivity(this);
        container();
        mUnBinder = ButterKnife.bind(this);
        init();
        dialog = dialogCreate();
        init(savedInstanceState);
        ButterKnife.bind(this);
    }

    /**
     * 在setContentView之前的一些操作
     */
    public void onCreateBefore() {
        mContext = this;
        mScreentWidth = PhoneUtil.getScreenWidth(this);
        mScreentHeight = PhoneUtil.getScreenHeight(this);
        mStatusHeight = PhoneUtil.getStatusHeight(this);
        mParamsRelativeMM = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParamsRelativeMW = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mParamsRelativeWM = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mParamsRelativeWW = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置Activity的布局
     */
    private void container() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mBaseContainer = (RelativeLayout) inflater.inflate(R.layout.base_container, null);
        View view = inflater.inflate(setBaseContentView(), null);
        //如果版本高于安卓4.4则透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//安卓4.4
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
             tintManager.setStatusBarTintColor(statusColor == -1 ? getResources().getColor(R.color.transparent) : statusColor);
          //  tintManager.setStatusBarTintColor(statusColor);
        }
        if (!isNotFillSysState) {
            mBaseContainer.setClipToPadding(false);
            mBaseContainer.setFitsSystemWindows(false);
        }

        //标题布局控件
        mTitleLayout = (RelativeLayout) inflater.inflate(R.layout.title_layout, null);
        mTitleLeftRelativeLayout = (RelativeLayout) mTitleLayout.findViewById(R.id.title_layout_left);
        mTitleRightRelativeLayout = (RelativeLayout) mTitleLayout.findViewById(R.id.title_layout_right);
        mTitleMiddleRelativeLayout = (RelativeLayout) mTitleLayout.findViewById(R.id.title_layout_middle);
        mTitleLeftImageView = (ImageView) mTitleLayout.findViewById(R.id.title_layout_left_image);
        mTitleMiddleTextView = (TextView) mTitleLayout.findViewById(R.id.title_layout_middle_tv);
        mTitleRightImageView = (ImageView) mTitleLayout.findViewById(R.id.title_layout_right_image);
        mTitleRightTextView = (TextView) mTitleLayout.findViewById(R.id.title_layout_right_tv);
        //除标题外的布局参数
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //是否显示标题
        if (isShowTitle) {
            //如果需要填充标题栏,则标题栏顶部空出状态栏
            if (!isNotFillSysState && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mTitleLayout.setPadding(0, mStatusHeight, 0, 0);
            }
            //添加标题栏
            mBaseContainer.addView(mTitleLayout, mParamsRelativeMW);
            //添加左边点击事件
            mTitleLeftRelativeLayout.setOnClickListener(leftClickListener);
            //添加右边点击事件
            mTitleRightRelativeLayout.setOnClickListener(rightClickListener);
            //需要使用mTitleLayout.getId(),先要在XML中给mTitleLayout控件添加ID
            params.addRule(RelativeLayout.BELOW, mTitleLayout.getId());
        }

        //loading 框架布局
        mLoadingLayout = (LoadingLayout)inflater.inflate(R.layout.loading_layout, null);

        //是否使用loading框架,自定义标题栏的或其他特殊需求的可设为false,然后到具体的界面去实现LoadingLayout
        if(isUseLoading&&isShowTitle){
            mLoadingLayout.addView(view,0); //自定义的界面加载到最底层
            mBaseContainer.addView(mLoadingLayout,params);
        }else{
            mBaseContainer.addView(view, params);
        }
        setContentView(mBaseContainer);
    }

    /**
     * 设置活动的布局ID
     * @return
     */
    public abstract int setBaseContentView();

    /**
     * 初始化
     */
    public abstract void init();

    /**
     * 含传参的初始化
     *
     * @param savedInstanceState
     */
    public void init(Bundle savedInstanceState) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从所有管理列表移除当前Activity
        ActivityManager.removeActivity(this);
        dialog.dismiss();
        OkGo.getInstance().cancelTag(TAG); //页面关闭时断开未完成的网络请求
        mUnBinder.unbind();
    }

    /**
     * 获取dialog控件
     *
     * @return
     */
    public LoadingDialog getDialog() {
        return dialog;
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //执行Activity切换动画
    }

    @Override
    public void finish() {
        super.finish();
        KeyboardUtil.closeKeyboard(this);
        //执行Activity切换动画
    }

    /**
     * Toast的显示(默认位置)
     * @param message 需要显示的信息
     */
    public void showToast(CharSequence message ){
        ToastUtil.showToast(message);
    }

    /**
     * 设置状态栏背景色(需要在onCreateBefore中才有效)
     *
     * @param statusColor 需要设置的状态栏颜色
     */
    public void setStatusColor(int statusColor) {
        this.statusColor = statusColor;
    }

    /**
     * 设置是否需要填充状态栏
     *
     * @param isNotFillSysState 需要View填充到状态栏设为false
     */
    public void setIsNotFillSysState(boolean isNotFillSysState) {
        this.isNotFillSysState = isNotFillSysState;
    }

    /**
     * 设置是否显示标题,如需设置需要在onCreateBefore方法执行
     * @param isShowTitle
     */
    public void setIsShowTitle(boolean isShowTitle) {
        this.isShowTitle = isShowTitle;
    }

    /**
     * 设置是否使用loading布局,如需设置需要在onCreateBefore方法执行
     * @param isUseLoading
     */
    public void setIsUseLoading(boolean isUseLoading) {
        this.isUseLoading = isUseLoading;
    }

    /**
     * 左边点击监听
     */
    private View.OnClickListener leftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onClickLeft(view);
        }
    };

    /**
     * 右边点击监听
     */
    private View.OnClickListener rightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onClickRight(view);
        }
    };

    /**
     * 标题左边点击事件
     *
     * @param view 点击的View
     */
    public void onClickLeft(View view) {
        onBackPressed();
    }

    /**
     * 标题右边点击事件
     *
     * @param view 点击的View
     */
    public void onClickRight(View view) {
    }

    /**
     * 获取LoadingLayout控件
     *
     */
    public LoadingLayout getmLoadingLayout() {
        return mLoadingLayout;
    }

    /**
     * 设置标题左边图片
     *
     * @param resId 图片资源
     */
    public void setTitleLeftImage(int resId) {
        if (mTitleLeftImageView != null) {
            mTitleLeftImageView.setImageResource(resId);
        }
    }

    /**
     * 设置中间标题
     *
     * @param title
     */
    public void setTitleMiddleText(String title) {
        if (mTitleMiddleTextView != null && !TextUtils.isEmpty(title)) {
            mTitleMiddleTextView.setText(title);
        }
    }

    /**
     * 设置标题右边图片
     *
     * @param resId 图片资源
     */
    public void setTitleRightImage(int resId) {
        if (mTitleRightImageView != null) {
            mTitleRightImageView.setImageResource(resId);
            mTitleRightRelativeLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载菊花提示
     * @return
     */
    public LoadingDialog dialogCreate() {
        return new LoadingDialog.Builder(this).create();
    }

    /**
     * 设置右边文字
     *
     * @param title
     */
    public void setTitleRightText(String title) {
        if (mTitleRightTextView != null && !TextUtils.isEmpty(title)) {
            mTitleRightTextView.setText(title);
            mTitleRightRelativeLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取标题左边ImageView
     *
     * @return 左边ImageView
     */
    public ImageView getmTitleLeftImageView() {
        return mTitleLeftImageView;
    }

    /**
     * 获取中间TextView
     *
     * @return 左边TextView
     */
    public TextView getmTitleMiddleTextView() {
        return mTitleMiddleTextView;
    }

    /**
     * 获取标题右边ImageView
     *
     * @return 右边ImageView
     */
    public ImageView getmTitleRightImageView() {
        return mTitleRightImageView;
    }

    /**
     * 获取右边TextView
     *
     * @return 右边TextView
     */
    public TextView getmTitleRightTextView() {
        return mTitleRightTextView;
    }

    /**
     * 获取标题栏
     *
     * @return 标题栏布局
     */
    public RelativeLayout getmTitleLayout() {
        return mTitleLayout;
    }

    /**
     * 获取标题右边布局
     *
     * @return 标题右边布局
     */
    public RelativeLayout getmTitleRightRelativeLayout() {
        return mTitleRightRelativeLayout;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 1.0f全透明 0.5f半透明
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        if (bgAlpha == 1) {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        this.getWindow().setAttributes(lp);
    }
}
