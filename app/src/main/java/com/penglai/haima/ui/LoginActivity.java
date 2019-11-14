package com.penglai.haima.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.LoginSuccessBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.SharepreferenceUtil;
import com.penglai.haima.utils.StringUtil;

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
    private TimeCount timeCount;

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
        timeCount = new TimeCount(60000, 1000);// 构造CountDownTimer对象
        etMobile.setText(SharepreferenceUtil.getString(Constants.MOBILE));
    }

    @OnClick({R.id.btn_get_code, R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        String mobile = etMobile.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_get_code:
                if (TextUtils.isEmpty(mobile)) {
                    showToast("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobile(mobile)) {
                    showToast("手机号输入不正确");
                    return;
                }
                getCode(mobile);
                break;
            case R.id.tv_login:
                String verCode = etCode.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {
                    showToast("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobile(mobile)) {
                    showToast("手机号输入不正确");
                    return;
                }
                if (TextUtils.isEmpty(verCode)) {
                    showToast("请输入验证码");
                    return;
                }
                login(mobile, verCode);
                break;
            case R.id.tv_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
        }
    }

    /**
     * 获取验证码
     *
     * @param mobile 手机号
     */
    private void getCode(String mobile) {
        OkGo.<CommonReturnData<Object>>post(Constants.URL + "verifyPhoneForApp")
                .params("mobile", mobile)
                .execute(new DialogCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> objectCommonReturnData) {
                        btnGetCode.setBackgroundResource(R.drawable.frame_solid_grey);
                        timeCount.start();
                    }
                });
    }

    /**
     * 登录
     *
     * @param mobile  手机号
     * @param valCode 验证码
     */
    private void login(final String mobile, String valCode) {
        OkGo.<CommonReturnData<LoginSuccessBean>>post(Constants.BASE_URL + "login/validateCode")
                .params("mobile", mobile)
                .params("valCode", valCode)
                .execute(new DialogCallback<CommonReturnData<LoginSuccessBean>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<LoginSuccessBean> commonReturnData) {
                        String token = commonReturnData.getData().getToken();
                        etCode.setText("");
                        SharepreferenceUtil.saveBoolean(Constants.IS_LOGIN, true);
                        SharepreferenceUtil.saveString(Constants.MOBILE, mobile);
                        SharepreferenceUtil.saveString(Constants.TOKEN, token);
                        startActivity(new Intent(mContext, MainActivity.class));
                        finish();
                    }
                });
    }


    /**
     * 倒计时控件
     */
    class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            btnGetCode.setText("重新获取");
            btnGetCode.setBackgroundResource(R.drawable.frame_solid_orange);
            btnGetCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btnGetCode.setClickable(false);
            btnGetCode.setText(millisUntilFinished / 1000 + "s后重试");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
    }
}
