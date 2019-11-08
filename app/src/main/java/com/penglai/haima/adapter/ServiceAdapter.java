package com.penglai.haima.adapter;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.bean.ServiceBean;

import java.util.List;


/**
 * Created by  on 2019/10/30.
 * 文件说明：
 */
public class ServiceAdapter extends BaseQuickAdapter<ServiceBean, BaseViewHolder> {

    public ServiceAdapter(List<ServiceBean> data) {
        super(R.layout.item_service_layout, data);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(final BaseViewHolder helper, ServiceBean item) {
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_state = helper.getView(R.id.tv_state);
        tv_time.setText(item.getName());
        tv_state.setText(item.getInd_price());
    }
}
