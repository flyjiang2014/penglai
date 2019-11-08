package com.penglai.haima.ui.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.adapter.ServiceAdapter;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ServiceBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.widget.DividerItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.penglai.haima.base.BaseActivity.PULL_DOWN_TIME;

/**
 * Created by  on 2019/11/8.
 * 文件说明：
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
        recyclerView.addItemDecoration(new DividerItemDecoration(R.drawable.divider_drawable_8dp, mContext));
        recyclerView.setAdapter(serviceAdapter);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setEnableRefresh(true);
        serviceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void initData() {
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
        OkGo.<CommonReturnData<List<ServiceBean>>>get(Constants.URL_FOR_OTHER + "service/getService")
                .params("serviceType", service_type)
                .execute(new DialogCallback<CommonReturnData<List<ServiceBean>>>(getActivity()) {
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
