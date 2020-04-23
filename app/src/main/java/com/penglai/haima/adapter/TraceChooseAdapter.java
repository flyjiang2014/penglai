package com.penglai.haima.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.bean.TraceItemBean;

import java.util.List;

/**
 * Created by  on 2019/11/5.
 * 文件说明：
 */
public class TraceChooseAdapter extends BaseQuickAdapter<TraceItemBean, BaseViewHolder> {

    public TraceChooseAdapter(@Nullable List<TraceItemBean> data) {
        super(R.layout.item_trace_choose_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TraceItemBean item) {
        TextView tv_trace_flow = helper.getView(R.id.tv_trace_flow);
        TextView tv_trace_time = helper.getView(R.id.tv_trace_time);
    }
}
