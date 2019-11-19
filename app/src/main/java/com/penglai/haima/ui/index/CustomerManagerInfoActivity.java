package com.penglai.haima.ui.index;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ManagerInfoBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.config.GlideApp;
import com.penglai.haima.config.GlideCircleTransformWithBorder;
import com.penglai.haima.okgomodel.CommonReturnData;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 客户经理详情
 */
public class CustomerManagerInfoActivity extends BaseActivity {

    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_manager_name)
    TextView tvManagerName;
    @BindView(R.id.tv_manager_mobile)
    TextView tvManagerMobile;
    @BindView(R.id.tv_manager_no)
    TextView tvManagerNo;
    GlideCircleTransformWithBorder glideCircleTransformWithBorder;
    @BindView(R.id.title_layout_left)
    RelativeLayout titleLayoutLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setBaseContentView() {
        setIsShowTitle(false);
        return R.layout.activity_customer_manager_info;
    }

    @Override
    public void init() {
        glideCircleTransformWithBorder = new GlideCircleTransformWithBorder(this, 1, getResources().getColor(R.color.white));
        getManagerInfoData();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getManagerInfoData();
    }

    /**
     * 获取客户经理数据
     */
    private void getManagerInfoData() {
        OkGo.<CommonReturnData<ManagerInfoBean>>post(Constants.URL + "getCusManInfoForApp")
                .execute(new DialogCallback<CommonReturnData<ManagerInfoBean>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<ManagerInfoBean> commonReturnData) {
                        ManagerInfoBean managerInfo = commonReturnData.getData();
                        tvManagerName.setText(managerInfo.getRealName());
                        tvManagerMobile.setText(managerInfo.getMobile());
                        tvManagerNo.setText("编号：" + managerInfo.getCode());
                        GlideApp.with(CustomerManagerInfoActivity.this).load(Constants.URL + managerInfo.getAvatarURL()).circleCrop()
                                .dontAnimate()
                                .defaultOptions()
                                .transform(glideCircleTransformWithBorder)
                                .into(imgHead);
                    }
                });
    }

    @OnClick(R.id.title_layout_left)
    public void onViewClicked() {
        finish();
    }
}
