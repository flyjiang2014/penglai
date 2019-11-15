package com.penglai.haima.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.adapter.ServiceListOrderAdapter;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.EventBean;
import com.penglai.haima.bean.ServiceOrderDataBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.widget.DividerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ServiceOrderListActivity extends BaseActivity implements OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private ServiceListOrderAdapter serviceListOrderAdapter;
    private List<ServiceOrderDataBean> serviceOrderDataBeans = new ArrayList<>();
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("服务订单");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_service_order_list;
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getOrderListData();
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        emptyView = getEmptyView();
        serviceListOrderAdapter = new ServiceListOrderAdapter(serviceOrderDataBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext));
        recyclerView.setAdapter(serviceListOrderAdapter);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setOnRefreshListener(this);
        serviceListOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, ServiceOrderActivity.class);
                intent.putExtra("serviceId", serviceOrderDataBeans.get(position).getService_id());
                intent.putExtra("trade_no", serviceOrderDataBeans.get(position).getTrade_no());
                startActivity(intent);
            }
        });
        getOrderListData();
    }

    /**
     * 获取订单列表数据
     */
    private void getOrderListData() {
        OkGo.<CommonReturnData<List<ServiceOrderDataBean>>>get(Constants.BASE_URL + "service/queryServiceOrderInfoList")
                .execute(new DialogCallback<CommonReturnData<List<ServiceOrderDataBean>>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<List<ServiceOrderDataBean>> commonReturnData) {
                        serviceOrderDataBeans.clear();
                        serviceOrderDataBeans.addAll(commonReturnData.getData());
                        serviceListOrderAdapter.notifyDataSetChanged();
                        if (serviceOrderDataBeans.size() == 0) {
                            serviceListOrderAdapter.setEmptyView(emptyView);
                        }
                    }
                });

    }

    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                getOrderListData();
                refreshLayout.finishRefresh();
                refreshLayout.setNoMoreData(false);
            }
        }, PULL_DOWN_TIME);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean data) {
        switch (data.getEvent()) {
            case EventBean.ORDER_REPAY_SUCCESS:
                getOrderListData();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
