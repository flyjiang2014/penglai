package com.penglai.haima.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.ImageViewHWRateUtil;
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
    @BindView(R.id.img_top)
    ImageView imgTop;
    @BindView(R.id.img_bottom)
    ImageView imgBottom;
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
        ImageViewHWRateUtil.setHeightWidthRate(mContext, imgTop, 1.61);//1256/781
        ImageViewHWRateUtil.setHeightWidthRate(mContext, imgBottom, 1.44);//1256/875
        timeCount = new TimeCount(60000, 1000);// 构造CountDownTimer对象

    }

    @OnClick({R.id.btn_get_code, R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        String mobile = etMobile.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_get_code:
                if(TextUtils.isEmpty(mobile)){
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
                startActivity(new Intent(mContext,PersonIndexActivity.class));

                String verCode = etCode.getText().toString().trim();
                if(TextUtils.isEmpty(mobile)){
                    showToast("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobile(mobile)) {
                    showToast("手机号输入不正确");
                    return;
                }
                if(TextUtils.isEmpty(verCode)){
                    showToast("请输入验证码");
                    return;
                }
                login(mobile,verCode);
                break;
            case R.id.tv_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
        }
    }

    /**
     * 获取验证码
     * @param mobile 手机号
     */
    private void getCode(String mobile){
        OkGo.<CommonReturnData<Object>>post(Constants.URL + "verifyPhone")
                .params("mobile", mobile)
                .execute(new DialogCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> objectCommonReturnData) {
                        btnGetCode.setBackgroundResource(R.drawable.frame_solid_grey);
                        timeCount.start();
                           showToast("获取成功");
                    }
                });
    }

    /**
     * 登录
     * @param mobile 手机号
     * @param verCode  验证码
     */
    private void login(String mobile,String verCode){
        OkGo.<CommonReturnData<Object>>post(Constants.URL + "login")
                .params("mobile", mobile)
                .params("verCode", verCode)
                .execute(new DialogCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> objectCommonReturnData) {
                        showToast("登录成功");
                        startActivity(new Intent(mContext,PersonIndexActivity.class));
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
