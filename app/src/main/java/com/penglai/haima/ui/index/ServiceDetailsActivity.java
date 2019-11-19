package com.penglai.haima.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.dialog.CommonOperateDialog;
import com.penglai.haima.dialog.MessageShowDialog;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.ClickUtil;
import com.penglai.haima.utils.ViewHWRateUtil;
import com.penglai.haima.widget.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ServiceDetailsActivity extends BaseActivity {

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
    private String orgType;
    private String serviceId = "";
    private MessageShowDialog dialog;
    private CommonOperateDialog commonOperateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("服务详情");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_service_details;
    }

    @Override
    public void init() {
        ViewHWRateUtil.setHeightWidthRate(mContext, banner, 2.13);//640/300
        orgType = getIntent().getStringExtra("orgType");
        serviceId = getIntent().getStringExtra("serviceId");

        if ("0".equals(orgType)) {//个人
            tvType.setText("价格");
            String price = getIntent().getStringExtra("ind_price");
            tvTypeContent.setText("￥" + price);

        } else if ("1".equals(orgType)) {//机构
            tvType.setText("服务地址");
            String address = getIntent().getStringExtra("address");
            tvTypeContent.setText(address);
        }
        tvServiceContent.setText(getIntent().getStringExtra("detail"));

        List<String> images = new ArrayList<>();
        images.add(Constants.URL_FOR_PIC + "banner/banner1.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner2.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner3.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner4.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner5.png");
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.start();
    }

    @OnClick(R.id.tv_charge)
    public void onViewClicked() {
        if (ClickUtil.isFastDoubleClick()) {
            return;  //防止快速多次点击
        }
        commonOperateDialog = new CommonOperateDialog(this, new CommonOperateDialog.OperateListener() {
            @Override
            public void sure() {
                if (ClickUtil.isFastDoubleClick()) {
                    return;  //防止快速多次点击
                }
                createServiceOrder();
                commonOperateDialog.dismiss();
            }
        });
        commonOperateDialog.setContentText("尊敬的客户，预约成功之后，商家会与您沟通服务时间");
        commonOperateDialog.show();
    }


    /**
     * 创建服务订单
     */
    private void createServiceOrder() {
        OkGo.<CommonReturnData<Object>>post(Constants.BASE_URL + "service/insertOrder")
                .params("serviceId", serviceId)
                .execute(new DialogCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> commonReturnData) {
                        dialog = new MessageShowDialog(ServiceDetailsActivity.this, new MessageShowDialog.OperateListener() {
                            @Override
                            public void sure() {
                                dialog.dismiss();
                                startActivity(new Intent(mContext, ServiceOrderListActivity.class));
                                ServiceDetailsActivity.this.finish();
                            }
                        });
                        dialog.setContentText("预约成功，等待商家确认");
                        dialog.show();
                    }
                });
    }

}
