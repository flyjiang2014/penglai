package com.penglai.haima.dialog;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.adapter.TraceAdapter;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.bean.TraceBean;
import com.penglai.haima.bean.TraceFlowBean;

import java.util.Collections;
import java.util.List;

/**
 * Created by  on 2019/11/5.
 * 文件说明：
 */
public class TraceFlowDialog extends BaseDialogView {
    TraceBean traceBean;
    ImageView img_close;
    TextView tv_state;
    TextView tv_trace_no;
    RecyclerView recyclerView;
    String kd_no;
    TraceAdapter traceAdapter;

    public TraceFlowDialog(BaseActivity activity, TraceBean traceBean, String kd_no) {
        super(activity);
        this.traceBean = traceBean;
        this.kd_no = kd_no;
        init(R.layout.dialog_trace_flow);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        tv_state = findViewById(R.id.tv_state);
        tv_trace_no = findViewById(R.id.tv_trace_no);
        img_close = findViewById(R.id.img_close);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void initViewData() {
        tv_state.setText(traceBean.getKdState());
        tv_trace_no.setText(traceBean.getCompany() + ":" + kd_no);
        List<TraceFlowBean> data = traceBean.getTraces();
        Collections.reverse(data);//集合数据倒序排序
        traceAdapter = new TraceAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(traceAdapter);
    }

    @Override
    public void initViewListener() {
        img_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
