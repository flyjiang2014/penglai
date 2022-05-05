package com.penglai.haima.ui.charge;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.adapter.ChargeRecordAdapter;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ChargeRecordBean;
import com.penglai.haima.bean.WithdrawDetailBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.dialog.WithdrawInfoShowDialog;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.widget.DividerItemDecoration;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class ChargeRecordActivity extends BaseActivity implements OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    ChargeRecordAdapter chargeRecordAdapter;
    private View emptyView;
    private List<ChargeRecordBean> chargeRecordBeanList = new ArrayList<>();
    private WithdrawInfoShowDialog withdrawInfoShowDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("交易记录");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_charge_record;
    }

    @Override
    public void init() {
        emptyView = getEmptyView();
        chargeRecordAdapter = new ChargeRecordAdapter(chargeRecordBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext));
        recyclerView.setAdapter(chargeRecordAdapter);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableLoadMore(false);
        chargeRecordAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if ("2".equals(chargeRecordBeanList.get(position).getType()) && !TextUtils.isEmpty(chargeRecordBeanList.get(position).getWithdraw_id())) { //提现操作
                    getApplyRecordData(chargeRecordBeanList.get(position).getWithdraw_id());
                }
            }
        });
        getChargeRecordData();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getChargeRecordData();
    }

    /**
     * 获取交易记录
     */
    private void getChargeRecordData() {
        OkGo.<CommonReturnData<List<ChargeRecordBean>>>get(Constants.BASE_URL + "trans/getList")
                .execute(new DialogCallback<CommonReturnData<List<ChargeRecordBean>>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<List<ChargeRecordBean>> commonReturnData) {
                        chargeRecordBeanList.clear();
                        chargeRecordBeanList.addAll(commonReturnData.getData());
                        chargeRecordAdapter.notifyDataSetChanged();
                        if (chargeRecordBeanList.size() == 0) {
                            chargeRecordAdapter.setEmptyView(emptyView);
                        }
                    }
                });
    }

    /**
     * 获取提现详情
     */
    private void getApplyRecordData(String withdrawId) {
        OkGo.<CommonReturnData<WithdrawDetailBean>>get(Constants.BASE_URL + "withdraw/getWithdrawInfo")
                .params("withdrawId", withdrawId)
                .execute(new DialogCallback<CommonReturnData<WithdrawDetailBean>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<WithdrawDetailBean> commonReturnData) {
                        withdrawInfoShowDialog = new WithdrawInfoShowDialog(ChargeRecordActivity.this, commonReturnData.getData());
                        withdrawInfoShowDialog.show();
                    }
                });
    }

    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                getChargeRecordData();
                refreshLayout.finishRefresh();
                refreshLayout.setNoMoreData(false);
            }
        }, PULL_DOWN_TIME);
    }
}
