package com.penglai.haima.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ProductSelectBean;
import com.penglai.haima.config.GlideApp;

import java.util.List;

/**
 * Created by ${flyjiang} on 2019/10/17.
 * 文件说明：
 */
public class ProductBuyAdapter extends BaseQuickAdapter<ProductSelectBean, BaseViewHolder> {

    public ProductBuyAdapter(@Nullable List<ProductSelectBean> data) {
        super(R.layout.item_buy_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ProductSelectBean item) {
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_count = helper.getView(R.id.tv_count);
        TextView tv_name = helper.getView(R.id.tv_name);
        ImageView img_pic = helper.getView(R.id.img_pic);
        tv_price.setText("￥" + item.getPrice());
        tv_count.setText("×" + item.getChoose_number());
        tv_name.setText(item.getTitle());
        GlideApp.with(mContext).load(Constants.URL_FOR_PIC + item.getImage_name() + Constants.PIC_JPG).defaultOptions().into(img_pic);
    }
}
