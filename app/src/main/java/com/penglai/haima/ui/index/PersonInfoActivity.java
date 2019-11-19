package com.penglai.haima.ui.index;

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
import com.penglai.haima.bean.UserInfoBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 个人信息
 */
public class PersonInfoActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_customer_no)
    EditText etCustomerNo;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("个人信息");

    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_person_info;
    }

    @Override
    public void init() {
        timeCount = new TimeCount(60000, 1000);// 构造CountDownTimer对象
        getPersonInfoData();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getPersonInfoData();
    }

    @OnClick({R.id.btn_get_code, R.id.tv_save})
    public void onViewClicked(View view) {
        String mobile = tvMobile.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_get_code:
                getCode(mobile);
                break;
            case R.id.tv_save:
                String realName = etName.getText().toString().trim();
                String validationCode = etCode.getText().toString().trim();
                String cusManCode = etCustomerNo.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                if (TextUtils.isEmpty(realName)) {
                    showToast("请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(validationCode)) {
                    showToast("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(cusManCode)) {
                    showToast("请输入客户经理编号");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    showToast("请输入联系地址");
                    return;
                }
                saveInfo(realName, mobile, validationCode, cusManCode, address);
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
                        showToast("获取成功");
                    }
                });
    }

    /**
     * 注册
     *
     * @param mobile         手机号
     * @param validationCode 验证码
     */
    private void saveInfo(String realName, String mobile, String validationCode, String cusManCode, String address) {
        HashMap<String, String> params = new HashMap<>();
        params.put("realName", realName);
        params.put("mobile", mobile);
        params.put("validationCode", validationCode);
        params.put("cusManCode", cusManCode);
        params.put("address", address);
        JSONObject jsonObject = new JSONObject(params);
        OkGo.<CommonReturnData<Object>>post(Constants.URL + "saveUserInfo")
                .upJson(jsonObject)
                .execute(new DialogCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> commonReturnData) {
                        showToast("保存成功");
                        finish();
                    }
                });
    }

    /**
     * 获取个人主页数据
     */
    private void getPersonInfoData() {
        OkGo.<CommonReturnData<UserInfoBean>>post(Constants.BASE_URL + "getUserInfo")
                .execute(new DialogCallback<CommonReturnData<UserInfoBean>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<UserInfoBean> commonReturnData) {
                        UserInfoBean userInfo = commonReturnData.getData();
                        etName.setText(userInfo.getName());
                        tvMobile.setText(userInfo.getMobile());
                        etCustomerNo.setText(userInfo.getManagerCode());
                        etAddress.setText(userInfo.getAddress());
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
