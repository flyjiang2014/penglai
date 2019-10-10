package com.penglai.haima.ui.charge;

import android.os.Bundle;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;

public class ChargeRecordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("交易记录");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_charge_record;
    }

    @Override
    public void init() {
        getChargeRecordData();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getChargeRecordData();
    }

    /**
     * 获取交易记录
     */
    private void getChargeRecordData() {
        OkGo.<CommonReturnData<Object>>post(Constants.URL + "queryCusTrans")
                .execute(new DialogCallback<CommonReturnData<Object>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> commonReturnData) {
                        showToast("成功");
                    }
                });
    }
}
