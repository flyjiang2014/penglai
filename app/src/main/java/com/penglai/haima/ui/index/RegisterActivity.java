package com.penglai.haima.ui.index;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;

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
        String mobile = etMobile.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_get_code:
                if(TextUtils.isEmpty(mobile)){
                    showToast("请输入手机号");
                    return;
                }
                getCode(mobile);
                break;
            case R.id.tv_register:
                String realName = etName.getText().toString().trim();
                String validationCode = etCode.getText().toString().trim();
                String cusManCode = etCustomerNo.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                if(TextUtils.isEmpty(realName)){
                    showToast("请输入姓名");
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    showToast("请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(validationCode)){
                    showToast("请输入验证码");
                    return;
                }
                if(TextUtils.isEmpty(cusManCode)){
                    showToast("请输入客户经理编号");
                    return;
                }
                if(TextUtils.isEmpty(address)){
                    showToast("请输入联系地址");
                    return;
                }
                register( realName, mobile, validationCode, cusManCode, address);
                break;
        }
    }

    /**
     * 获取验证码
     * @param mobile
     */
    private void getCode(String mobile){
        OkGo.<CommonReturnData<Object>>post(Constants.URL + "verifyPhone")
                .params("mobile", mobile)
                .execute(new DialogCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> objectCommonReturnData) {
                        showToast("获取成功");
                    }
                });
    }
    /**
     *
     * @param mobile
     * @param validationCode
     */
    private void register(String realName,String mobile,String validationCode,String cusManCode,String address){

        OkGo.<CommonReturnData<Object>>post("http://wx.ypimp.cn/ypimp/saveUserInfo")
                .params("realName", realName)
                .params("mobile", mobile)
                .params("validationCode", validationCode)
                .params("cusManCode", cusManCode)
                .params("address", address)
                .execute(new DialogCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> objectCommonReturnData) {
                        showToast("注册成功");
                    }

                    @Override
                    public void onError(Response<CommonReturnData<Object>> response) {
                        super.onError(response);
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
