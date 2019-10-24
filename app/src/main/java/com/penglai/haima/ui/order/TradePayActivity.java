package com.penglai.haima.ui.order;

import android.os.Bundle;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.callback.JsonCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.SharepreferenceUtil;

public class TradePayActivity extends BaseActivity {

    private String tradeNo = "";
    private String balance = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("支付");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_trade_pay;
    }

    @Override
    public void init() {
        tradeNo = getIntent().getStringExtra("tradeNo");
        balance = getIntent().getStringExtra("balance");

        findViewById(R.id.tv_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ClickUtil.isFastDoubleClick()) {
//                    return;  //防止快速多次点击
//                }
                payForTrade();
            }
        });
    }

    public void payForTrade() {
        OkGo.<CommonReturnData<Object>>post(Constants.URL_FOR_OTHER + "hot/payOrderList")
                .params("mobile", SharepreferenceUtil.getString(Constants.MOBILE))
                .params("tradeNo", tradeNo)
                .execute(new JsonCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> commonReturnData) {
                        showToast("支付成功");
                    }
                });
    }
}
