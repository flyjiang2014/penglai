package com.penglai.haima.dialog;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.penglai.haima.R;
import com.penglai.haima.adapter.TraceChooseAdapter;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.bean.TraceItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2019/11/5.
 * 文件说明：
 */
public class TraceChooseDialog extends BaseDialogView {
    RecyclerView recyclerView;
    TraceChooseAdapter traceChooseAdapter;
    List<TraceItemBean> traceItemBeanList = new ArrayList<>();
    ItemChooseListener itemChooseListener;

    public TraceChooseDialog(BaseActivity activity, List<TraceItemBean> traceItemBeanList, ItemChooseListener itemChooseListener) {
        super(activity);
        this.traceItemBeanList = traceItemBeanList;
        this.itemChooseListener = itemChooseListener;
        init(R.layout.dialog_trace_choose);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void initViewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(traceChooseAdapter);
        traceChooseAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                itemChooseListener.ItemChoose(traceItemBeanList.get((position)));
            }
        });
    }

    @Override
    public void initViewListener() {
    }


    public interface ItemChooseListener {
        void ItemChoose(TraceItemBean traceItemBean);
    }
}
