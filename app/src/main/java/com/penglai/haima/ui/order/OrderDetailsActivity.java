package com.penglai.haima.ui.order;

import android.os.Bundle;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.OrderDetailBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;

public class OrderDetailsActivity extends BaseActivity {
    private String tradeNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("订单详情");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_order_details;
    }

    @Override
    public void init() {
        tradeNo = getIntent().getStringExtra("tradeNo");
        getData();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getData();
    }

    public void getData() {
        OkGo.<CommonReturnData<OrderDetailBean>>get(Constants.URL_FOR_OTHER + "hot/queryOrderSingle")
                .params("tradeNo", tradeNo)
                .execute(new DialogCallback<CommonReturnData<OrderDetailBean>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<OrderDetailBean> commonReturnData) {
                        showToast(commonReturnData.getData().getReceiveAddress());
                    }
                });
    }
}
