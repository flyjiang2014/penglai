package com.penglai.haima.ui.order;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.callback.JsonCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.ClickUtil;
import com.penglai.haima.utils.SharepreferenceUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class TradePayActivity extends BaseActivity {

    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.tv_left_money)
    TextView tvLeftMoney;
    @BindView(R.id.tv_charge)
    TextView tvCharge;
    @BindView(R.id.tv_no_money)
    TextView tvNoMoney;
    @BindView(R.id.rl_charge)
    RelativeLayout rlCharge;
    private String tradeNo = "";
    private String balance = "";
    private String totalMoney = "";

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
        totalMoney = getIntent().getStringExtra("totalMoney");
        tvPayMoney.setText(("￥" + totalMoney));
        String details = String.format("从账户个人余额（<font  color='#FF0000'>￥%s</font>）中扣除", balance);
        tvLeftMoney.setText(Html.fromHtml(details));
        if (Integer.parseInt(totalMoney) > Integer.parseInt(balance)) {//支付金额大于余额
            tvNoMoney.setVisibility(View.VISIBLE);
            rlCharge.setEnabled(false);
            rlCharge.setBackgroundResource(R.drawable.frame_solid_grey);
        }
    }

    /**
     * 支付
     */
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

    @OnClick({R.id.rl_charge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_charge:
                if (ClickUtil.isFastDoubleClick()) {
                    return;  //防止快速多次点击
                }
                payForTrade();
                break;
        }
    }
}
