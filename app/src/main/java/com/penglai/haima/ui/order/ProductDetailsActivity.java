package com.penglai.haima.ui.order;

import android.os.Bundle;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ProductBean;
import com.penglai.haima.utils.ViewHWRateUtil;
import com.penglai.haima.widget.GlideRoundImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProductDetailsActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_product_content)
    TextView tvProductContent;
    @BindView(R.id.tv_type_show)
    TextView tvTypeShow;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_price_show)
    TextView tvPriceShow;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    ProductBean productBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("商品详情");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_product_details;
    }

    @Override
    public void init() {
        productBean = (ProductBean) getIntent().getSerializableExtra("mData");
        ViewHWRateUtil.setHeightWidthRate(mContext, banner, 2.13);//640/300
        List<String> images = new ArrayList<>();
        images.add(Constants.URL_FOR_PIC + "banner/banner1.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner2.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner3.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner4.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner5.png");
        banner.setImageLoader(new GlideRoundImageLoader());
        banner.setImages(images);
        banner.start();
        tvProductContent.setText(productBean.getContent());
        tvType.setText(productBean.getModel());
        tvPrice.setText("￥" + productBean.getPrice());
    }
}
