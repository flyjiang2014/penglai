package com.penglai.haima.third.weichat.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;

import com.penglai.haima.R;
import com.penglai.haima.base.Constants;
import com.penglai.haima.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置固定竖屏
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.WECHAT_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                ToastUtil.showToast("支付成功");
            } else if (!TextUtils.isEmpty(resp.errStr)) {
                ToastUtil.showToast(resp.errStr);
            }
            finish();
            overridePendingTransition(0, 0);
        }
    }
}