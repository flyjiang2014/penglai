package com.penglai.haima.ui.charge;

import android.os.Bundle;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;

public class ChargePayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("充值");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_charge_pay;
    }

    @Override
    public void init() {

    }
}
