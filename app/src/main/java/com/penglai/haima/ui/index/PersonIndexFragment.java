package com.penglai.haima.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.UserInfoBean;
import com.penglai.haima.callback.JsonFragmentCallback;
import com.penglai.haima.config.GlideCircleTransformWithBorder;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.ui.SettingActivity;
import com.penglai.haima.ui.charge.ChargePayActivity;
import com.penglai.haima.ui.charge.ChargeRecordActivity;
import com.penglai.haima.ui.order.OrderListActivity;
import com.penglai.haima.ui.order.ServiceOrderListActivity;

/**
 * Created by ${flyjiang} on 2019/10/22.
 * 文件说明：
 */
public class PersonIndexFragment extends BaseFragmentV4 implements View.OnClickListener {
    ImageView imgHead;
    LinearLayout llPersonInfo;
    LinearLayout llCustomerManager;
    TextView tvPersonBalance;
    LinearLayout llPersonBalance;
    TextView tvFreeDelivery;
    LinearLayout llFreeDelivery;
    TextView tvPersonName;
    Button btnChargePay;
    LinearLayout llChargeRecord;
    RelativeLayout rlSetting;
    LinearLayout llMyOrders;
    LinearLayout llServiceOrders;
    private int state = -1;
    GlideCircleTransformWithBorder glideCircleTransformWithBorder;

    @Override
    protected View initView(LayoutInflater inflater) {
        state = getArguments().getInt("state");
        View view = inflater.inflate(R.layout.fragment_person_index, null);
        initView(view);
        initViewData();
        return view;
    }

    @Override
    protected void initViewData() {
        glideCircleTransformWithBorder = new GlideCircleTransformWithBorder(mContext, 1, getResources().getColor(R.color.white));

    }

    private void initView(View view) {
        imgHead = view.findViewById(R.id.img_head);
        llPersonInfo = view.findViewById(R.id.ll_person_info);
        llCustomerManager = view.findViewById(R.id.ll_customer_manager);
        tvPersonBalance = view.findViewById(R.id.tv_person_balance);
        llPersonBalance = view.findViewById(R.id.ll_person_balance);
        tvFreeDelivery = view.findViewById(R.id.tv_free_delivery);
        llFreeDelivery = view.findViewById(R.id.ll_free_delivery);
        tvPersonName = view.findViewById(R.id.tv_person_name);
        btnChargePay = view.findViewById(R.id.btn_charge_pay);
        llChargeRecord = view.findViewById(R.id.ll_charge_record);
        rlSetting = view.findViewById(R.id.rl_setting);
        llMyOrders = view.findViewById(R.id.ll_my_orders);
        llServiceOrders = view.findViewById(R.id.ll_service_orders);
        llPersonInfo.setOnClickListener(this);
        llCustomerManager.setOnClickListener(this);
        llChargeRecord.setOnClickListener(this);
        btnChargePay.setOnClickListener(this);
        rlSetting.setOnClickListener(this);
        llMyOrders.setOnClickListener(this);
        llServiceOrders.setOnClickListener(this);
    }

    @Override
    public void initData() {
        getIndexData();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getIndexData();
    }

    public static PersonIndexFragment getInstance(int state) {
        PersonIndexFragment fragment = new PersonIndexFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_person_info:
                startActivity(new Intent(mContext, PersonInfoActivity.class));
                break;
            case R.id.ll_customer_manager:
                startActivity(new Intent(mContext, CustomerManagerInfoActivity.class));
                break;
            case R.id.ll_charge_record:
                startActivity(new Intent(mContext, ChargeRecordActivity.class));
                break;
            case R.id.ll_my_orders:
                startActivity(new Intent(mContext, OrderListActivity.class));
                break;
            case R.id.ll_service_orders:
                startActivity(new Intent(mContext, ServiceOrderListActivity.class));
                break;
            case R.id.btn_charge_pay:
                startActivity(new Intent(mContext, ChargePayActivity.class));
                break;
            case R.id.rl_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }

    /**
     * 获取个人主页数据
     */
    private void getIndexData() {
        OkGo.<CommonReturnData<UserInfoBean>>get(Constants.BASE_URL + "getUserInfo")
                .execute(new JsonFragmentCallback<CommonReturnData<UserInfoBean>>(this, true, false) {
                    @Override
                    public void onSuccess(CommonReturnData<UserInfoBean> commonReturnData) {
                        UserInfoBean userInfo = commonReturnData.getData();
                        tvPersonName.setText(userInfo.getName());
                        tvPersonBalance.setText(userInfo.getBalance() + "元");
                    }
                });
    }
}
