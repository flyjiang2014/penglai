package com.penglai.haima.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ServiceDetailBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.ui.order.TradePayActivity;
import com.penglai.haima.utils.ViewHWRateUtil;
import com.penglai.haima.widget.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    private String price = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_service_order;
    }

    @Override
    public void init() {
        ViewHWRateUtil.setHeightWidthRate(mContext, banner, 2.13);//640/300
        serviceId = getIntent().getStringExtra("serviceId");
        trade_no = getIntent().getStringExtra("trade_no");
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
                if (!TextUtils.isEmpty(price)) {
                    Intent intent = new Intent(mContext, TradePayActivity.class);
                    intent.putExtra("tradeNo", trade_no);
                    intent.putExtra("totalMoney", price);
                    intent.putExtra("hasNoBalance", true);
                    intent.putExtra("isForService", true);
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
                            price = serviceDetailBean.getInd_price();

                        } else if ("1".equals(orgType)) {//机构
                            tvType.setText("服务地址");
                            tvTypeContent.setText(serviceDetailBean.getAddress());
                            price = serviceDetailBean.getOrg_price();
                        }
                        tvServiceContent.setText(serviceDetailBean.getDetail());

                        List<String> images = new ArrayList<>();
                        for (int i = 1; i < 4; i++) {
                            images.add(Constants.URL_FOR_PIC2 + serviceDetailBean.getCover_image() + "_" + i + Constants.PIC_JPG);
                        }
                        banner.setImageLoader(new GlideImageLoader());
                        banner.setImages(images);
                        // banner.stopAutoPlay();
                        banner.start();
                    }
                });
    }
}
