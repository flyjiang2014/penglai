package com.penglai.haima.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.adapter.ProductSelectAdapter;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.EventBean;
import com.penglai.haima.bean.ProductSelectBean;
import com.penglai.haima.bean.TradeBean;
import com.penglai.haima.bean.UserInfoBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.ClickUtil;
import com.penglai.haima.utils.StringUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 商品订单提交
 */
public class ProductOrderSubmitActivity extends BaseActivity {

    @BindView(R.id.rv_select_product)
    RecyclerView rvSelectProduct;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.tv_price_note)
    TextView tvPriceNote;
    @BindView(R.id.tv_final_price)
    TextView tvFinalPrice;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    String totalMoney = "";
    List<ProductSelectBean> mData = new ArrayList<>();
    ProductSelectAdapter productSelectAdapter;
    @BindView(R.id.tv_details)
    TextView tvDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("订单提交");
    }


    @Override
    public int setBaseContentView() {
        return R.layout.activity_product_order_submit;
    }

    @Override
    public void init() {
        mData = (List<ProductSelectBean>) getIntent().getSerializableExtra("mData");
        totalMoney = getIntent().getStringExtra("totalMoney");
        tvFinalPrice.setText("￥" + totalMoney);
        String details = String.format("共计<font  color='#FF0000'>%s</font>件，<font  color='#FF0000'>￥%s</font>", getCount(), getTotalPrice());
        tvDetails.setText(Html.fromHtml(details));
        productSelectAdapter = new ProductSelectAdapter(mData);
        rvSelectProduct.setLayoutManager(new LinearLayoutManager(this));
        rvSelectProduct.setAdapter(productSelectAdapter);
        getIndexData();
    }

    /**
     * 获取购买件数
     *
     * @return
     */
    public int getCount() {
        int count = 0;
        for (ProductSelectBean productSelectBean : mData) {
            count += productSelectBean.getChoose_number();
        }
        return count;
    }

    /**
     * 获取购买件数
     *
     * @return
     */
    public int getTotalPrice() {
        int price = 0;
        for (ProductSelectBean productSelectBean : mData) {
            price += productSelectBean.getChoose_number() * Integer.parseInt(productSelectBean.getPrice());
        }
        return price;
    }

    /**
     * 获取个人主页数据
     */
    private void getIndexData() {
        OkGo.<CommonReturnData<UserInfoBean>>post(Constants.BASE_URL + "getUserInfo")
                .execute(new DialogCallback<CommonReturnData<UserInfoBean>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<UserInfoBean> commonReturnData) {
                        UserInfoBean userInfo = commonReturnData.getData();
                        etAddress.setText(userInfo.getAddress());
                        etMobile.setText(userInfo.getMobile());
                        etName.setText(userInfo.getName());
                    }
                });
    }

    /**
     * 创建订单
     *
     * @param merclist
     * @param receiveName
     * @param receiveMobile
     * @param receiveAddress
     * @param receiveNotes
     */
    private void createOrder(String merclist, String receiveName, String receiveMobile, String receiveAddress, String receiveNotes) {
        OkGo.<CommonReturnData<TradeBean>>post(Constants.BASE_URL + "hot/insertOrderList")
                .params("mobile", getUserMobile())
                .params("merclist", merclist)
                .params("amount", totalMoney)
                .params("receiveName", receiveName)
                .params("receiveMobile", receiveMobile)
                .params("receiveAddress", receiveAddress)
                .params("receiveNotes", receiveNotes)
                .execute(new DialogCallback<CommonReturnData<TradeBean>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<TradeBean> commonReturnData) {
                        Intent intent = new Intent(mContext, TradePayActivity.class);
                        intent.putExtra("tradeNo", commonReturnData.getData().getTradeNo());
                        intent.putExtra("balance", commonReturnData.getData().getBalance());
                        intent.putExtra("totalMoney", totalMoney);
                        startActivity(intent);
                        EventBus.getDefault().post(new EventBean(EventBean.TRADE_PAY_SUCCESS));
                        finish();
                    }
                });
    }

    @OnClick({R.id.tv_submit})
    public void onViewClicked(View view) {
        String mobile = etMobile.getText().toString().trim();
        String realName = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        switch (view.getId()) {
            case R.id.tv_submit:
                if (ClickUtil.isFastDoubleClick()) {
                    return;  //防止快速多次点击
                }
                if (TextUtils.isEmpty(realName)) {
                    showToast("请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    showToast("请输入手机号");
                    return;
                }
                if (!StringUtil.isMobile(mobile)) {
                    showToast("手机号输入不正确");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    showToast("请输入联系地址");
                    return;
                }
                String notes = etNote.getText().toString().trim();
                createOrder(new Gson().toJson(mData), realName, mobile, address, notes);
                break;
        }
    }
}
