package com.penglai.haima.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.adapter.ServiceAdapter;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ServiceBean;
import com.penglai.haima.callback.JsonFragmentCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.ui.order.ServiceDetailsActivity;
import com.penglai.haima.widget.DividerItemDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.penglai.haima.base.BaseActivity.PULL_DOWN_TIME;

/**
 * Created by  on 2019/11/8.
 * 文件说明：服务列表展示
 */
public class ServiceItemFragment extends BaseFragmentV4 implements OnRefreshListener {

    private String service_type = "";
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private View emptyView;
    private ServiceAdapter serviceAdapter;
    List<ServiceBean> serviceBeans = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater) {
        service_type = getArguments().getString("service_type");
        View view = inflater.inflate(R.layout.fragment_service_item, null);
        initView(view);
        initViewData();
        return view;
    }

    public static ServiceItemFragment getInstance(String service_type) {
        ServiceItemFragment fragment = new ServiceItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("service_type", service_type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViewData() {
        serviceAdapter = new ServiceAdapter(serviceBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext));
        recyclerView.setAdapter(serviceAdapter);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setEnableRefresh(true);
        smartRefreshLayout.setOnRefreshListener(this);
        serviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, ServiceDetailsActivity.class);
                intent.putExtra("orgType", serviceBeans.get(position).getOrgtype());
                intent.putExtra("serviceId", serviceBeans.get(position).getId());
                intent.putExtra("address", serviceBeans.get(position).getAddress());
                intent.putExtra("ind_price", serviceBeans.get(position).getInd_price());
                intent.putExtra("detail", serviceBeans.get(position).getDetail());
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {
        getDataList();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getDataList();
    }

    private void initView(View view) {
        smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        emptyView = getEmptyView();
    }

    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataList();
                refreshLayout.finishRefresh();
                refreshLayout.setNoMoreData(false);
            }
        }, PULL_DOWN_TIME);
    }

    /**
     * 获取服务内容
     */
    private void getDataList() {
        OkGo.<CommonReturnData<List<ServiceBean>>>get(Constants.BASE_URL + "service/getService")
                .params("serviceType", service_type)
                .execute(new JsonFragmentCallback<CommonReturnData<List<ServiceBean>>>(this, true, true) {
                    @Override
                    public void onSuccess(CommonReturnData<List<ServiceBean>> commonReturnData) {
                        serviceBeans.clear();
                        serviceBeans.addAll(commonReturnData.getData());
                        serviceAdapter.notifyDataSetChanged();
                        if (serviceBeans.size() == 0) {
                            serviceAdapter.setEmptyView(emptyView);
                        }
                    }
                });
    }

}
