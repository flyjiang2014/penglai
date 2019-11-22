package com.penglai.haima.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ShopProductBean;
import com.penglai.haima.config.GlideApp;

import java.util.List;

/**
 * Created by  on 2019/11/5.
 * 文件说明：
 */
public class ShopProductAdapter extends BaseQuickAdapter<ShopProductBean, BaseViewHolder> {

    public ShopProductAdapter(@Nullable List<ShopProductBean> data) {
        super(R.layout.item_shop_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopProductBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_address = helper.getView(R.id.tv_address);
        ImageView img_pic = helper.getView(R.id.img_pic);
        tv_name.setText(item.getModel());
        tv_address.setText(item.getNumber());
        GlideApp.with(mContext).load(Constants.URL_FOR_PIC + item.getImage_name() + Constants.PIC_JPG).defaultOptions().into(img_pic);
    }
}
