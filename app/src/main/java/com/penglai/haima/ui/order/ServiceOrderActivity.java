package com.penglai.haima.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.EventBean;
import com.penglai.haima.bean.ServiceDetailBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.ViewHWRateUtil;
import com.penglai.haima.widget.GlideRoundImageLoader;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 服务订单详情
 */
public class ServiceOrderActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_service_content)
    TextView tvServiceContent;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_type_content)
    TextView tvTypeContent;
    @BindView(R.id.tv_charge)
    TextView tvCharge;
    private String serviceId = "";
    private String trade_no = "";
    private String amount = "";
    private String state = "";
    private String title = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("订单详情");

    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_service_order;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        ViewHWRateUtil.setHeightWidthRate(mContext, banner, 2.13);//640/300
        serviceId = getIntent().getStringExtra("serviceId");
        trade_no = getIntent().getStringExtra("trade_no");
        state = getIntent().getStringExtra("state");
        amount = getIntent().getStringExtra("amount");
        setState();
        getData();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getData();
    }

    @OnClick({R.id.tv_charge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_charge:
                if (tvCharge.getText().toString().contains("去支付")) {
                    if (!TextUtils.isEmpty(amount)) {
                        Intent intent = new Intent(mContext, TradePayActivity.class);
                        intent.putExtra("tradeNo", trade_no);
                        intent.putExtra("totalMoney", amount);
                        intent.putExtra("hasNoBalance", true);
                        intent.putExtra("isForService", true);
                        startActivity(intent);
                    }
                } else if (tvCharge.getText().toString().contains("去评价")) {
                    Intent intent = new Intent(mContext, ServiceCommentActivity.class);
                    intent.putExtra("serviceId", serviceId);
                    intent.putExtra("title", title);
                    startActivity(intent);
                }

                break;
        }
    }

    private void getData() {
        OkGo.<CommonReturnData<ServiceDetailBean>>get(Constants.BASE_URL + "service/getServiceById")
                .params("serviceId", serviceId)
                .execute(new DialogCallback<CommonReturnData<ServiceDetailBean>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<ServiceDetailBean> commonReturnData) {
                        ServiceDetailBean serviceDetailBean = commonReturnData.getData();
                        String orgType = serviceDetailBean.getOrgtype();

                        if ("0".equals(orgType)) {//个人
                            tvType.setText("价格");
                            tvTypeContent.setText("￥" + serviceDetailBean.getInd_price());
                            title = serviceDetailBean.getTitle();

                        } else if ("1".equals(orgType)) {//机构
                            tvType.setText("服务地址");
                            tvTypeContent.setText(serviceDetailBean.getAddress());
                            title = serviceDetailBean.getOrg_summary();
                        }
                        tvServiceContent.setText(serviceDetailBean.getDetail());
                        List<String> images = new ArrayList<>();
                        for (int i = 1; i < 4; i++) {
                            images.add(Constants.URL_FOR_PIC2 + serviceDetailBean.getCover_image() + "_" + i + Constants.PIC_JPG);
                        }
                        banner.setImageLoader(new GlideRoundImageLoader());
                        banner.setImages(images);
                        // banner.stopAutoPlay();
                        banner.start();
                    }
                });
    }

    public void setState() {
        switch (state) {
            case "0":  //待商家确认
                tvCharge.setVisibility(View.GONE);
                tvCharge.setText("去支付");
                tvCharge.setEnabled(false);
                tvCharge.setBackgroundResource(R.drawable.frame_solid_grey);
                break;
            case "1":  //待支付
                tvCharge.setText("去支付");
                tvCharge.setEnabled(true);
                tvCharge.setBackgroundResource(R.drawable.frame_solid_orange);
                break;
            case "2": //待服务
                tvCharge.setVisibility(View.GONE);
                tvCharge.setText("去评价");
                tvCharge.setEnabled(false);
                tvCharge.setBackgroundResource(R.drawable.frame_solid_grey);
                break;
            case "3": //服务完成
                break;
            case "4": //待评价
                tvCharge.setText("去评价");
                tvCharge.setEnabled(true);
                tvCharge.setBackgroundResource(R.drawable.frame_solid_orange);
                break;
            case "5": //已关闭
                tvCharge.setVisibility(View.GONE);
                tvCharge.setText("已关闭");
                tvCharge.setEnabled(false);
                tvCharge.setBackgroundResource(R.drawable.frame_solid_grey);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean data) {
        switch (data.getEvent()) {
            case EventBean.ORDER_REPAY_SUCCESS:
                tvCharge.setVisibility(View.GONE);
                tvCharge.setText("去评价");
                tvCharge.setEnabled(false);
                tvCharge.setBackgroundResource(R.drawable.frame_solid_grey);
                break;
            case EventBean.SERVICE_COMMENT_SUCCESS:
                tvCharge.setVisibility(View.GONE);
                tvCharge.setText("已关闭");
                tvCharge.setEnabled(false);
                tvCharge.setBackgroundResource(R.drawable.frame_solid_grey);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
