package com.penglai.haima.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.EventBean;
import com.penglai.haima.bean.UserInfoBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.dialog.CommonOperateDialog;
import com.penglai.haima.dialog.MessageShowDialog;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.ui.charge.ChargePayActivity;
import com.penglai.haima.utils.ClickUtil;
import com.penglai.haima.utils.SharepreferenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 订单支付
 */
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
    @BindView(R.id.rl_go_pay)
    RelativeLayout rlGoPay;
    private String tradeNo = "";
    private String balance = "";
    private String totalMoney = "";
    private MessageShowDialog dialog;
    private boolean hasNoBalance;
    private boolean isForService;
    private CommonOperateDialog commonOperateDialog;
    boolean isShopProduct;

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
        EventBus.getDefault().register(this);
        tradeNo = getIntent().getStringExtra("tradeNo");
        isShopProduct = getIntent().getBooleanExtra("isShopProduct", false);
        totalMoney = getIntent().getStringExtra("totalMoney");
        tvPayMoney.setText(("￥" + totalMoney));
        hasNoBalance = getIntent().getBooleanExtra("hasNoBalance", false);
        isForService = getIntent().getBooleanExtra("isForService", false);
        if (hasNoBalance) {
            getIndexData();
        } else {
            balance = getIntent().getStringExtra("balance");
            String details = String.format("从账户个人余额（<font  color='#FF0000'>￥%s</font>）中扣除", balance);
            tvLeftMoney.setText(Html.fromHtml(details));
            if (Integer.parseInt(totalMoney) > Integer.parseInt(balance)) {//支付金额大于余额
                tvNoMoney.setVisibility(View.VISIBLE);
                rlCharge.setEnabled(false);
                rlCharge.setBackgroundResource(R.drawable.frame_solid_grey);
                rlGoPay.setVisibility(View.VISIBLE);
            }
        }
    }


    /**
     * 支付
     */
    public void payForTrade() {
        OkGo.<CommonReturnData<Object>>post(Constants.BASE_URL + "hot/payOrderList")
                .params("mobile", SharepreferenceUtil.getString(Constants.MOBILE))
                .params("tradeNo", tradeNo)
                .params("type", isShopProduct ? "1" : "0")
                .execute(new DialogCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> commonReturnData) {
                        EventBus.getDefault().post(new EventBean(EventBean.ORDER_REPAY_SUCCESS));//待支付订单支付
                        dialog = new MessageShowDialog(TradePayActivity.this, new MessageShowDialog.OperateListener() {
                            @Override
                            public void sure() {
                                dialog.dismiss();
                                TradePayActivity.this.finish();
                            }
                        });
                        dialog.setContentText("交易成功");
                        dialog.show();
                    }
                });
    }

    /**
     * 服务支付
     */
    public void payForServiceTrade() {
        OkGo.<CommonReturnData<Object>>post(Constants.BASE_URL + "/service/payServiceOrder")
                .params("mobile", SharepreferenceUtil.getString(Constants.MOBILE))
                .params("tradeNo", tradeNo)
                .execute(new DialogCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> commonReturnData) {
                        EventBus.getDefault().post(new EventBean(EventBean.ORDER_REPAY_SUCCESS));//待支付订单支付
                        dialog = new MessageShowDialog(TradePayActivity.this, new MessageShowDialog.OperateListener() {
                            @Override
                            public void sure() {
                                dialog.dismiss();
                                TradePayActivity.this.finish();
                            }
                        });
                        dialog.setContentText("交易成功");
                        dialog.show();
                    }
                });
    }

    @OnClick({R.id.rl_charge, R.id.rl_go_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_charge:
                if (ClickUtil.isFastDoubleClick()) {
                    return;  //防止快速多次点击
                }
                commonOperateDialog = new CommonOperateDialog(this, new CommonOperateDialog.OperateListener() {
                    @Override
                    public void sure() {
                        if (isForService) {  //服务支付
                            payForServiceTrade();
                        } else {
                            payForTrade();
                        }
                        commonOperateDialog.dismiss();
                    }
                });
                commonOperateDialog.setContentText("确认支付");
                commonOperateDialog.show();
                break;
            case R.id.rl_go_pay:
                startActivity(new Intent(mContext, ChargePayActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean data) {
        switch (data.getEvent()) {
            case EventBean.RECHARGE_PAY_SUCCESS:
                getIndexData();
                break;
        }
    }

    /**
     * 获取个人主页数据
     */
    private void getIndexData() {
        OkGo.<CommonReturnData<UserInfoBean>>post(Constants.BASE_URL + "getUserInfo")
                .execute(new DialogCallback<CommonReturnData<UserInfoBean>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<UserInfoBean> commonReturnData) {
                        UserInfoBean userInfo = commonReturnData.getData();
                        balance = userInfo.getBalance();
                        String details = String.format("从账户个人余额（<font  color='#FF0000'>￥%s</font>）中扣除", balance);
                        tvLeftMoney.setText(Html.fromHtml(details));
                        if (Double.parseDouble(totalMoney) > Double.parseDouble(balance)) {//支付金额大于余额
                            tvNoMoney.setVisibility(View.VISIBLE);
                            rlCharge.setEnabled(false);
                            rlCharge.setBackgroundResource(R.drawable.frame_solid_grey);
                            rlGoPay.setVisibility(View.VISIBLE);
                        } else {
                            tvNoMoney.setVisibility(View.GONE);
                            rlCharge.setEnabled(true);
                            rlCharge.setBackgroundResource(R.drawable.frame_solid_orange);
                            rlGoPay.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getIndexData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
