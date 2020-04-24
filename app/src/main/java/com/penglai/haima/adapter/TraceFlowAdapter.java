package com.penglai.haima.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.bean.TraceFlowBean;

import java.util.List;

/**
 * Created by  on 2019/11/5.
 * 文件说明：
 */
public class TraceFlowAdapter extends BaseQuickAdapter<TraceFlowBean, BaseViewHolder> {

    public TraceFlowAdapter(@Nullable List<TraceFlowBean> data) {
        super(R.layout.item_trace_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TraceFlowBean item) {
        TextView tv_trace_flow = helper.getView(R.id.tv_trace_flow);
        TextView tv_trace_time = helper.getView(R.id.tv_trace_time);
        ImageView img_solid = helper.getView(R.id.img_solid);
        View view = helper.getView(R.id.view);
        tv_trace_flow.setText(item.getAcceptStation());
        tv_trace_time.setText(item.getAcceptTime());

        if (helper.getLayoutPosition() == 0) {
            tv_trace_flow.setTextColor(mContext.getResources().getColor(R.color.green2));
            tv_trace_time.setTextColor(mContext.getResources().getColor(R.color.green2));
            img_solid.setImageResource(R.drawable.solid_green);
        } else {
            tv_trace_flow.setTextColor(mContext.getResources().getColor(R.color.text_grey666));
            tv_trace_time.setTextColor(mContext.getResources().getColor(R.color.text_grey666));
            img_solid.setImageResource(R.drawable.solid_grey);
        }
        if (helper.getLayoutPosition() == mData.size() - 1) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
