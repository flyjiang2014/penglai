package com.penglai.haima.ui.index;

import android.content.Intent;
import android.os.Bundle;
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
        ImageViewHWRateUtil.setHeightWidthRate(mContext, imgTop, 1.61);//1080/507
        ImageViewHWRateUtil.setHeightWidthRate(mContext, imgBottom, 1.44);//1080/196

    }

    @OnClick({R.id.btn_get_code, R.id.tv_login, R.id.tv_register})
    public void onViewClicked(View view) {
        String mobile = etMobile.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_get_code:
                getCode(mobile);
                break;
            case R.id.tv_login:
                String verCode = etCode.getText().toString().trim();
                login(mobile,verCode);
                break;
            case R.id.tv_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
        }
    }
    public void getCode(String mobile){
        OkGo.<CommonReturnData<Object>>post(Constants.URL + "verifyPhone")
                .params("mobile", mobile)
                .execute(new DialogCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> objectCommonReturnData) {
                           showToast("获取成功");
                    }
                });
    }

    public void login(String mobile,String verCode){
        OkGo.<CommonReturnData<Object>>post(Constants.URL + "login")
                .params("mobile", mobile)
                .params("verCode", verCode)
                .execute(new DialogCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> objectCommonReturnData) {

                    }
                });
    }
}
