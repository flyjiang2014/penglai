package com.penglai.haima.ui.order;

import android.content.Intent;
import android.os.Bundle;
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
import com.penglai.haima.dialog.TwoCodeShowDialog;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.widget.DividerItemDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 服务列表
 */
public class ServiceOrderListActivity extends BaseActivity implements OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private ServiceListOrderAdapter serviceListOrderAdapter;
    private List<ServiceOrderDataBean> serviceOrderDataBeans = new ArrayList<>();
    private View emptyView;
    TwoCodeShowDialog twoCodeShowDialog;

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
                intent.putExtra("state", serviceOrderDataBeans.get(position).getState());
                intent.putExtra("amount", serviceOrderDataBeans.get(position).getAmount());
                startActivity(intent);
            }
        });
        serviceListOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                twoCodeShowDialog = new TwoCodeShowDialog(ServiceOrderListActivity.this, "haima&service&" + serviceOrderDataBeans.get(position).getTrade_no() + "&" + serviceOrderDataBeans.get(position).getUser_mobile());
                twoCodeShowDialog.show();
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
            case EventBean.SERVICE_COMMENT_SUCCESS:
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
