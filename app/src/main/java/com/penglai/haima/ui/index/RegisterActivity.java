package com.penglai.haima.ui.index;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_customer_no)
    EditText etCustomerNo;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("注册");
        timeCount = new TimeCount(60000, 1000);// 构造CountDownTimer对象
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.btn_get_code, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code:
                showToast("获取验证码");
                break;
            case R.id.tv_register:
                showToast("注册");
                break;
        }
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
//            btnGetCode.setText("重新获取");
//            btnGetCode.setBackgroundResource(R.drawable.frame_solid_orange);
//            btnGetCode.setBackgroundResource(R.drawable.frame_solid_grey);
//            btnGetCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
//            btnGetCode.setClickable(false);
//            btnGetCode.setText(millisUntilFinished / 1000 + "s");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
    }
}
