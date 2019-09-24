package com.penglai.haima.ui.index;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.widget.loading.LoadingLayout;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Bind(R.id.login)
    Button login;
    @Bind(R.id.register)
    Button register;
    @Bind(R.id.weixin)
    Button weixin;
    @Bind(R.id.ali)
    Button ali;

    @Override
    public int setBaseContentView() {
        //   setIsShowTitle(false);
        setIsUseLoading(false);
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void init() {
        mLoadingLayout.setStatus(LoadingLayout.Success);
    }

    @OnClick({R.id.login, R.id.register, R.id.weixin, R.id.ali})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                break;
            case R.id.register:
                break;
            case R.id.weixin:
                break;
            case R.id.ali:
                break;
        }
    }
}
