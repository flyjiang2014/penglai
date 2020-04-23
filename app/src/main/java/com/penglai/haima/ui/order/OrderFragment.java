package com.penglai.haima.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.penglai.haima.R;
import com.penglai.haima.adapter.OrderListAdapter;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.OrderListBean;
import com.penglai.haima.bean.TraceBean;
import com.penglai.haima.bean.TraceItemBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.callback.JsonFragmentCallback;
import com.penglai.haima.dialog.TraceChooseDialog;
import com.penglai.haima.dialog.TraceFlowDialog;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.penglai.haima.base.BaseActivity.PULL_DOWN_TIME;

/**
 * Created by  on 2019/10/30.
 * 文件说明：订单列表fragment
 */
public class OrderFragment extends BaseFragmentV4 implements OnRefreshListener {

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private View emptyView;
    private int state = -1; // 0：待上线，1:上线中,2:已下线  -1 全部（自定义状态）
    List<OrderListBean> orderListBeans = new ArrayList<>();
    OrderListAdapter orderListAdapter;
    TraceFlowDialog traceFlowDialog;
    TraceChooseDialog traceChooseDialog;

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
        orderListAdapter = new OrderListAdapter(orderListBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //   recyclerView.addItemDecoration(new DividerItemDecoration(R.drawable.divider_drawable_8dp, mContext));
        recyclerView.setAdapter(orderListAdapter);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setOnRefreshListener(this);
        orderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra("tradeNo", orderListBeans.get(position).getTrade_no());
                startActivity(intent);
            }
        });

        orderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_traces) {
                    int size = orderListBeans.get(position).getKd_info().size();
                    if (size > 0) {
                        if (size == 1) {
                            getTracesInfo(orderListBeans.get(position).getKd_info().get(0).getKd_company(), orderListBeans.get(position).getKd_info().get(0).getKd_no());
                        } else {
                            traceChooseDialog = new TraceChooseDialog((BaseActivity) getActivity(), orderListBeans.get(position).getKd_info(), new TraceChooseDialog.ItemChooseListener() {
                                @Override
                                public void ItemChoose(TraceItemBean traceItemBean) {
                                    getTracesInfo(traceItemBean.getKd_company(), traceItemBean.getKd_no());
                                }
                            });
                            traceChooseDialog.show();
                        }
                    }
                    // getTracesInfo("YTO", "YT2018589953982");
                } else if (view.getId() == R.id.tv_go_pay) {
                    Intent intent = new Intent(mContext, TradePayActivity.class);
                    intent.putExtra("tradeNo", orderListBeans.get(position).getTrade_no());
                    intent.putExtra("totalMoney", orderListBeans.get(position).getTotal_price());
                    intent.putExtra("hasNoBalance", true);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 获取物流信息
     *
     * @param
     */
    private void getTracesInfo(String company, final String no) {
        OkGo.<CommonReturnData<TraceBean>>get(Constants.BASE_URL + "express/query")
                .params("company", company)
                .params("no", no)
                .execute(new DialogCallback<CommonReturnData<TraceBean>>(getActivity()) {
                    @Override
                    public void onSuccess(CommonReturnData<TraceBean> commonReturnData) {
                        traceFlowDialog = new TraceFlowDialog((BaseActivity) getActivity(), commonReturnData.getData(), no);
                        traceFlowDialog.show();
                    }
                });
    }

    @Override
    public void initData() {
        getOrderListData();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getOrderListData();
    }

    private void initView(View view) {
        smartRefreshLayout = view.findViewById(R.id.smartRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerView);
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
        GetRequest<CommonReturnData<List<OrderListBean>>> request = OkGo.<CommonReturnData<List<OrderListBean>>>get(Constants.BASE_URL + "hot/queryOrderList")
                .params("mobile", getUserMobile())
                .params("type", "0");
        if (state >= 0) {
            request.params("stateApp", state);
        }
        request.execute(new JsonFragmentCallback<CommonReturnData<List<OrderListBean>>>(this, true, true) {
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

    @Override
    public void onRefresh(final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                getOrderListData();
                refreshLayout.finishRefresh();
                refreshLayout.setNoMoreData(false);
            }
        }, PULL_DOWN_TIME);
    }
}
