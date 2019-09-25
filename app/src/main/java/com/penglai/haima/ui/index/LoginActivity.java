package com.penglai.haima.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setBaseContentView() {
        setIsShowTitle(false);
        return R.layout.activity_login;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.btn_get_code, R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code:
                showToast("获取验证码");
                break;
            case R.id.tv_login:
                showToast("登录");
                break;
            case R.id.tv_register:
                startActivity(new Intent(mContext,RegisterActivity.class));
                break;
        }
    }
}
