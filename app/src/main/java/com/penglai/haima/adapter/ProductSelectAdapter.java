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
        super(R.layout.item_select_product, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ProductSelectBean item) {
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_count = helper.getView(R.id.tv_count);
        TextView tv_name = helper.getView(R.id.tv_name);
        tv_price.setText("￥" + item.getPrice());
        tv_count.setText("×" + item.getChoose_number());
        tv_name.setText(item.getTitle());
    }
}
