package com.penglai.haima.ui.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.penglai.haima.R;
import com.penglai.haima.adapter.OrderListAdapter;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.OrderListBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.widget.DividerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2019/10/30.
 * 文件说明：
 */
public class OrderFragment extends BaseFragmentV4 {

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private View emptyView;
    private int state = -1; // 0：待上线，1:上线中,2:已下线  -1 全部（自定义状态）
    List<OrderListBean> orderListBeans = new ArrayList<>();
    OrderListAdapter orderListAdapter;

    @Override
    protected View initView(LayoutInflater inflater) {
        state = getArguments().getInt("state");
        View view = inflater.inflate(R.layout.fragment_order_list, null);
        initView(view);
        initViewData();
        return view;
    }

    @Override
    protected void initViewData() {
        emptyView = getEmptyView();
        orderListAdapter = new OrderListAdapter(orderListBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(R.drawable.divider_drawable_8dp, mContext));
        recyclerView.setAdapter(orderListAdapter);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setEnableRefresh(false);
    }

    @Override
    public void initData() {
        getOrderListData();
    }

    private void initView(View view) {
        smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        emptyView = getEmptyView();
    }

    public static OrderFragment getInstance(int state) {
        OrderFragment fragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 获取订单列表
     */
    private void getOrderListData() {
        GetRequest getRequest = OkGo.<CommonReturnData<List<OrderListBean>>>get(Constants.URL_FOR_OTHER + "hot/queryOrderList")
                .params("mobile", getUserMobile());
        if (state >= 0) {
            getRequest.params("stateApp", state);
        }
        getRequest.execute(new DialogCallback<CommonReturnData<List<OrderListBean>>>(getActivity()) {
                    @Override
                    public void onSuccess(CommonReturnData<List<OrderListBean>> commonReturnData) {
                        orderListBeans.clear();
                        orderListBeans.addAll(commonReturnData.getData());
                        orderListAdapter.notifyDataSetChanged();
                        if (orderListBeans.size() == 0) {
                            orderListAdapter.setEmptyView(emptyView);
                        }
                    }
                });
    }
}
