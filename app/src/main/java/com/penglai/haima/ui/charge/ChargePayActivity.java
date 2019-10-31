package com.penglai.haima.ui.charge;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.WeChatPayResponse;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.SharepreferenceUtil;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChargePayActivity extends BaseActivity {

    @BindView(R.id.tv_charge)
    TextView tvCharge;
    @BindView(R.id.et_pay_money)
    EditText etPayMoney;
    private IWXAPI weChatApi;

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
        weChatApi = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APP_ID);
    }

    @OnClick(R.id.tv_charge)
    public void onViewClicked() {
        String payMoney = etPayMoney.getText().toString().trim();
        if (TextUtils.isEmpty(payMoney)) {
            showToast("请输入充值金额");
            return;
        }
        if (Integer.parseInt(payMoney) <= 0) {
            showToast("充值金额需大于0");
            return;
        }
        charge_pay(payMoney);
    }

    /**
     * 生成订单并支付
     *
     * @param cashnum
     */
    private void charge_pay(String cashnum) {
        OkGo.<CommonReturnData<WeChatPayResponse>>post(Constants.URL_FOR_OTHER + "order/pay")
                .params("cashnum", "1")
                .params("mobile", SharepreferenceUtil.getString(Constants.MOBILE))
                .params("mercid", "001")
                .execute(new DialogCallback<CommonReturnData<WeChatPayResponse>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<WeChatPayResponse> commonReturnData) {
                        if (isWeChatInstalled(ChargePayActivity.this)) {
                            weChat_pay(commonReturnData.getData());
                        } else {
                            showToast("未安装微信，尝试其他支付");
                        }
                    }
                });
    }

    /**
     * 进行微信支付
     *
     * @param response
     */
    private void weChat_pay(WeChatPayResponse response) {
        weChatApi = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APP_ID);
        weChatApi.registerApp(Constants.WECHAT_APP_ID);
        boolean isPaySupported = weChatApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported) {
            PayReq req = new PayReq();
            req.appId = Constants.WECHAT_APP_ID;
            req.partnerId = response.getPartnerid();
            req.nonceStr = response.getNoncestr();
            req.prepayId = response.getPrepayid();
            req.timeStamp = String.valueOf(response.getTimestamp());
            req.sign = response.getSign();
            req.packageValue = response.getPackageValue();
            weChatApi.sendReq(req);
        }
    }

    /**
     * 是否安装了微信
     */
    public static boolean isWeChatInstalled(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (packages != null) {
            for (int i = 0, length = packages.size(); i < length; i++) {
                String pn = packages.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
}
