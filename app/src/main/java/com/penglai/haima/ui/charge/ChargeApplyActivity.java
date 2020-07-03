package com.penglai.haima.ui.charge;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.UserInfoBean;
import com.penglai.haima.callback.JsonCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.ClickUtil;
import com.penglai.haima.utils.SharepreferenceUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class ChargeApplyActivity extends BaseActivity {

    @BindView(R.id.tv_apply_balance)
    TextView tvApplyBalance;
    @BindView(R.id.ll_person_balance)
    LinearLayout llPersonBalance;
    @BindView(R.id.et_zfb_account)
    EditText etZfbAccount;
    @BindView(R.id.et_zfb_name)
    EditText etZfbName;
    @BindView(R.id.tv_charge_apply)
    TextView tvChargeApply;
    private String balance;//余额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("提现");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_charge_apply;
    }

    @Override
    public void init() {
        getIndexData();
        setAccountInfo();
    }

    @OnClick(R.id.tv_charge_apply)
    public void onViewClicked() {
        if (ClickUtil.isFastDoubleClick()) {
            return;
        }
        if (TextUtils.isEmpty(balance)) {
            return;
        }
        Apply();
    }

    /**
     * 获取个人主页数据
     */
    private void getIndexData() {
        OkGo.<CommonReturnData<UserInfoBean>>get(Constants.BASE_URL + "getUserInfo")
                .execute(new JsonCallback<CommonReturnData<UserInfoBean>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<UserInfoBean> commonReturnData) {
                        UserInfoBean userInfo = commonReturnData.getData();
                        balance = userInfo.getBalance();
                        tvApplyBalance.setText(balance + "元");
                    }
                });
    }

    /**
     * 提现账户信息
     */
    private void setAccountInfo() {
        if (TextUtils.isEmpty(SharepreferenceUtil.getString(Constants.APPLY_ACCOUNT_NO))) {
            getAccountInfo();
        } else {
            etZfbAccount.setText(SharepreferenceUtil.getString(Constants.APPLY_ACCOUNT_NO));
            etZfbName.setText(SharepreferenceUtil.getString(Constants.APPLY_ACCOUNT_NAME));
        }
    }

    /**
     * 提现账户信息
     */
    private void getAccountInfo() {
        OkGo.<CommonReturnData<UserInfoBean>>get(Constants.BASE_URL + "getUserInfo")
                .execute(new JsonCallback<CommonReturnData<UserInfoBean>>(this, false) {
                    @Override
                    public void onSuccess(CommonReturnData<UserInfoBean> commonReturnData) {
                        //保存账号和姓名
                    }
                });
    }

    /**
     * 申请提现
     */
    private void Apply() {
        OkGo.<CommonReturnData<UserInfoBean>>get(Constants.BASE_URL + "getUserInfo")
                .execute(new JsonCallback<CommonReturnData<UserInfoBean>>(this, false) {
                    @Override
                    public void onSuccess(CommonReturnData<UserInfoBean> commonReturnData) {
                        //弹出提示
                    }
                });
    }
}
