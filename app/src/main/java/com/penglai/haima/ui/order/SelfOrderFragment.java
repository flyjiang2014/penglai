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
import com.penglai.haima.adapter.SelfOrderListAdapter;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.OrderListBean;
import com.penglai.haima.callback.JsonFragmentCallback;
import com.penglai.haima.dialog.TwoCodeShowDialog;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.penglai.haima.base.BaseActivity.PULL_DOWN_TIME;

/**
 * Created by  on 2019/10/30.
 * 文件说明：自提订单列表fragment
 */
public class SelfOrderFragment extends BaseFragmentV4 implements OnRefreshListener {

    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private View emptyView;
    private int state = -1;
    List<OrderListBean> orderListBeans = new ArrayList<>();
    SelfOrderListAdapter selfOrderListAdapter;
    TwoCodeShowDialog twoCodeShowDialog;

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
        selfOrderListAdapter = new SelfOrderListAdapter(orderListBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //   recyclerView.addItemDecoration(new DividerItemDecoration(R.drawable.divider_drawable_8dp, mContext));
        recyclerView.setAdapter(selfOrderListAdapter);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setOnRefreshListener(this);
        selfOrderListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra("tradeNo", orderListBeans.get(position).getTrade_no());
                intent.putExtra("isShopProduct", true);
                startActivity(intent);
            }
        });

        selfOrderListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_code_check) {
                    twoCodeShowDialog = new TwoCodeShowDialog((BaseActivity) getActivity(), orderListBeans.get(position).getTrade_no() + "&" + orderListBeans.get(position).getReceive_mobile());
                    twoCodeShowDialog.show();
                } else if (view.getId() == R.id.tv_go_pay) {
                    Intent intent = new Intent(mContext, TradePayActivity.class);
                    intent.putExtra("tradeNo", orderListBeans.get(position).getTrade_no());
                    intent.putExtra("totalMoney", orderListBeans.get(position).getTotal_price());
                    intent.putExtra("hasNoBalance", true);
                    intent.putExtra("isShopProduct", true);
                    startActivity(intent);
                }
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
        smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        emptyView = getEmptyView();
    }

    public static SelfOrderFragment getInstance(int state) {
        SelfOrderFragment fragment = new SelfOrderFragment();
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
                .params("type", "1");
        if (state >= 0) {
            request.params("stateApp", state);
        }
        request.execute(new JsonFragmentCallback<CommonReturnData<List<OrderListBean>>>(this, true, true) {
            @Override
            public void onSuccess(CommonReturnData<List<OrderListBean>> commonReturnData) {
                orderListBeans.clear();
                orderListBeans.addAll(commonReturnData.getData());
                selfOrderListAdapter.notifyDataSetChanged();
                if (orderListBeans.size() == 0) {
                    selfOrderListAdapter.setEmptyView(emptyView);
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
