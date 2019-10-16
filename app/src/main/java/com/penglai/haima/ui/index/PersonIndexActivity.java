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
import com.penglai.haima.ui.charge.ChargePayActivity;
import com.penglai.haima.ui.charge.ChargeRecordActivity;

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
    GlideCircleTransformWithBorder glideCircleTransformWithBorder;
    @BindView(R.id.btn_charge_pay)
    Button btnChargePay;
    @BindView(R.id.title_layout_left)
    RelativeLayout titleLayoutLeft;

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
        OkGo.<CommonReturnData<UserInfoBean>>post(Constants.URL + "getUserInfo")
                .execute(new DialogCallback<CommonReturnData<UserInfoBean>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<UserInfoBean> commonReturnData) {
                        UserInfoBean userInfo = commonReturnData.getData();
                        tvPersonName.setText(userInfo.getRealName());
                        tvPersonBalance.setText(userInfo.getBalance() + "元");
                        tvFreeDelivery.setText(userInfo.getScore() + "次");
                    }
                });
    }

    @OnClick({R.id.ll_person_info, R.id.ll_customer_manager, R.id.ll_person_balance, R.id.btn_charge_pay, R.id.title_layout_left})
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
            case R.id.ll_person_balance:
                startActivity(new Intent(mContext, ChargeRecordActivity.class));
                break;
            case R.id.btn_charge_pay:
                startActivity(new Intent(mContext, ChargePayActivity.class));
                break;
        }
    }
}
