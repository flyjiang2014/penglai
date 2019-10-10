package com.penglai.haima.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.widget.loading.LoadingLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.weixin)
    Button weixin;
    @BindView(R.id.ali)
    Button ali;


    @Override
    public int setBaseContentView() {
        setIsUseLoading(false);
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init() {
        mLoadingLayout.setStatus(LoadingLayout.Success);
    }

    @OnClick({R.id.login, R.id.register, R.id.weixin, R.id.ali})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                startActivity(new Intent(mContext, LoginActivity.class));
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
