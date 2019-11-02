package com.penglai.haima.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.UserInfoBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.config.GlideCircleTransformWithBorder;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.ui.SettingActivity;
import com.penglai.haima.ui.charge.ChargePayActivity;
import com.penglai.haima.ui.charge.ChargeRecordActivity;
import com.penglai.haima.ui.order.OrderListActivity;
import com.penglai.haima.utils.ActivityManager;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonIndexActivity extends BaseActivity {
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.ll_person_info)
    LinearLayout llPersonInfo;
    @BindView(R.id.ll_customer_manager)
    LinearLayout llCustomerManager;
    @BindView(R.id.tv_person_balance)
    TextView tvPersonBalance;
    @BindView(R.id.ll_person_balance)
    LinearLayout llPersonBalance;
    @BindView(R.id.tv_free_delivery)
    TextView tvFreeDelivery;
    @BindView(R.id.ll_free_delivery)
    LinearLayout llFreeDelivery;
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;
    @BindView(R.id.btn_charge_pay)
    Button btnChargePay;
    @BindView(R.id.title_layout_left)
    RelativeLayout titleLayoutLeft;
    @BindView(R.id.ll_charge_record)
    LinearLayout llChargeRecord;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.ll_my_orders)
    LinearLayout llMyOrders;
    GlideCircleTransformWithBorder glideCircleTransformWithBorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setBaseContentView() {
        setIsShowTitle(false);
        return R.layout.activity_person_index;
    }

    @Override
    public void init() {
        glideCircleTransformWithBorder = new GlideCircleTransformWithBorder(this, 1, getResources().getColor(R.color.white));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIndexData();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getIndexData();
    }

    /**
     * 获取个人主页数据
     */
    private void getIndexData() {
        OkGo.<CommonReturnData<UserInfoBean>>post(Constants.URL_FOR_OTHER + "getUserInfo")
                .execute(new DialogCallback<CommonReturnData<UserInfoBean>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<UserInfoBean> commonReturnData) {
                        UserInfoBean userInfo = commonReturnData.getData();
                        tvPersonName.setText(userInfo.getName());
                        tvPersonBalance.setText(userInfo.getBalance() + "元");
                    }
                });
    }

    @OnClick({R.id.ll_person_info, R.id.ll_customer_manager, R.id.ll_charge_record, R.id.btn_charge_pay,
            R.id.title_layout_left, R.id.rl_setting, R.id.ll_my_orders})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_layout_left:
                finish();
                break;
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
            case R.id.btn_charge_pay:
                startActivity(new Intent(mContext, ChargePayActivity.class));
                break;
            case R.id.rl_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }

    /**
     * 连续按两次返回键关闭程序
     */
    private long mExitClickTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitClickTime > 2000) {
            showToast("再按一次返回键关闭程序");
            mExitClickTime = System.currentTimeMillis();
        } else {
            ActivityManager.finishAllActivity();
            System.exit(0);
        }
    }
}
