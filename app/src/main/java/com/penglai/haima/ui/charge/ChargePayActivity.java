package com.penglai.haima.ui.charge;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.OrderWeChatPayResponse;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.OnClick;

public class ChargePayActivity extends BaseActivity {

    @BindView(R.id.tv_charge)
    TextView tvCharge;
    @BindView(R.id.et_pay_money)
    EditText etPayMoney;

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
        weChatApi = WXAPIFactory.createWXAPI(this, "wxccfc962edd5bea78");
    }

    @OnClick(R.id.tv_charge)
    public void onViewClicked() {
        charge_pay();
    }

    private void charge_pay() {
        OkGo.<CommonReturnData<OrderWeChatPayResponse>>post("http://120.55.61.20:8089/order/pay/")
                .params("cashnum", etPayMoney.getText().toString().trim())
                .params("mercid", "001")
                .execute(new DialogCallback<CommonReturnData<OrderWeChatPayResponse>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<OrderWeChatPayResponse> commonReturnData) {
                        weChat_pay(commonReturnData.getData());
                        showToast("获取成功");
                    }
                });
    }

    /**
     * 进行微信支付
     *
     * @param weChatPay
     */
    private IWXAPI weChatApi;

    public void weChat_pay(OrderWeChatPayResponse weChatPay) {
        weChatApi = WXAPIFactory.createWXAPI(this, "wxccfc962edd5bea78");
        weChatApi.registerApp(Constants.WECHAT_APP_ID);
        boolean isPaySupported = weChatApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
            PayReq req = new PayReq();
            req.appId = Constants.WECHAT_APP_ID;
            req.partnerId = weChatPay.partnerid;
            req.nonceStr = weChatPay.noncestr;
            req.prepayId = weChatPay.prepayid;
            req.timeStamp = String.valueOf(weChatPay.timestamp);
            req.sign = weChatPay.sign;
            req.packageValue = "Sign=WXPay";
            weChatApi.sendReq(req);
        }
    }
}
