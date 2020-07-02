package com.penglai.haima.ui.charge;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;

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

    }

    @OnClick(R.id.tv_charge_apply)
    public void onViewClicked() {
    }
}
