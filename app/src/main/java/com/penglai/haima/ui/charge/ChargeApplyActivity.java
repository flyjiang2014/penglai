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
import com.penglai.haima.bean.ApplyAccountBean;
import com.penglai.haima.bean.UserInfoBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.callback.JsonCallback;
import com.penglai.haima.dialog.MessageShowDialog;
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
    @BindView(R.id.et_apply_money)
    EditText etApplyMoney;
    private String balance;//余额
    MessageShowDialog messageShowDialog;

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
        String account = etZfbAccount.getText().toString().trim();
        String name = etZfbName.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            showToast("请输入支付宝账号");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            showToast("请输入支付宝姓名");
            return;
        }
        String amount = etApplyMoney.getText().toString().trim();

        if (TextUtils.isEmpty(amount)) {
            showToast("请输入金额");
            return;
        }

        if (Integer.parseInt(amount) > Integer.parseInt(balance)) {
            showToast("余额不足");
            return;
        }
        if (Double.parseDouble(amount) < 10) {
            showToast("提现金额小于最低提现金额（10元）");
            return;
        }

        Apply(account, name, amount);
    }

    /**
     * 获取余额
     */
    private void getIndexData() {
        OkGo.<CommonReturnData<UserInfoBean>>get(Constants.BASE_URL + "getUserInfo")
                .execute(new DialogCallback<CommonReturnData<UserInfoBean>>(this, true) {
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
        OkGo.<CommonReturnData<ApplyAccountBean>>get(Constants.BASE_URL + "withdraw/getLast")
                .execute(new JsonCallback<CommonReturnData<ApplyAccountBean>>(this, false) {
                    @Override
                    public void onSuccess(CommonReturnData<ApplyAccountBean> commonReturnData) {
                        ApplyAccountBean data = commonReturnData.getData();
                        if (!TextUtils.isEmpty(data.getUser_account())) {
                            etZfbAccount.setText(data.getUser_account());
                            etZfbName.setText(data.getUser_name());
                        }
                    }
                });
    }

    /**
     * 申请提现
     */
    private void Apply(final String account, final String name, String amount) {
        OkGo.<CommonReturnData<Object>>post(Constants.BASE_URL + "withdraw/insertAccount")
                .params("account", account)
                .params("name", name)
                .params("amount", amount)
                .execute(new DialogCallback<CommonReturnData<Object>>(this, false) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> commonReturnData) {
                        SharepreferenceUtil.saveString(Constants.APPLY_ACCOUNT_NO, account);
                        SharepreferenceUtil.saveString(Constants.APPLY_ACCOUNT_NAME, name);
                        //弹出提示
                        messageShowDialog = new MessageShowDialog(ChargeApplyActivity.this, new MessageShowDialog.OperateListener() {
                            @Override
                            public void sure() {
                                ChargeApplyActivity.this.finish();
                            }
                        });
                        messageShowDialog.setContentText("申请成功，等待打款");
                        messageShowDialog.show();
                    }
                });
    }
}
