package com.penglai.haima.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.bean.ProductSelectBean;

import java.util.List;

/**
 * Created by ${flyjiang} on 2019/10/17.
 * 文件说明：
 */
public class ProductSelectAdapter extends BaseQuickAdapter<ProductSelectBean, BaseViewHolder> {

    public ProductSelectAdapter(@Nullable List<ProductSelectBean> data) {
        super(R.layout.select_product_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ProductSelectBean item) {
        TextView tv_price = helper.getView(R.id.tv_price);
        tv_price.setText("￥" + item.getPrice());

    }
}
