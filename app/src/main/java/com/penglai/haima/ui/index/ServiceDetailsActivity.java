package com.penglai.haima.ui.index;

import android.os.Bundle;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.utils.ViewHWRateUtil;
import com.penglai.haima.widget.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
}
