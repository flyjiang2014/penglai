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
        TextView tv_trace_company = helper.getView(R.id.tv_trace_company);
        TextView tv_trace_no = helper.getView(R.id.tv_trace_no);
        helper.addOnClickListener(R.id.tv_traces_see);
        tv_trace_company.setText(String.format("%s      %s", (helper.getLayoutPosition() + 1), item.getKd_company()));
        tv_trace_no.setText(item.getKd_no());
    }
}
