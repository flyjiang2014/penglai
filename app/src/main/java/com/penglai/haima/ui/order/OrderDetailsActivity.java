package com.penglai.haima.ui.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.adapter.ProductBuyAdapter;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.EventBean;
import com.penglai.haima.bean.OrderDetailBean;
import com.penglai.haima.bean.ProductSelectBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.widget.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 商品订单详情
 */
public class OrderDetailsActivity extends BaseActivity {
    @BindView(R.id.tv_trade_no)
    TextView tvTradeNo;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_trade_state)
    TextView tvTradeState;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_note)
    TextView tvNote;
    @BindView(R.id.ll_note)
    LinearLayout llNote;
    @BindView(R.id.tv_go_pay)
    TextView tvGoPay;
    @BindView(R.id.ll_go_pay)
    LinearLayout llGoPay;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_shop_address)
    TextView tvShopAddress;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.img_call)
    ImageView imgCall;
    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    @BindView(R.id.tv_list)
    TextView tvList;
    private String tradeNo = "";
    private String totalMoney = "";
    private ProductBuyAdapter productBuyAdapter;
    private List<ProductSelectBean> mData = new ArrayList<>();//已购买商品
    private boolean isShopProduct;
    private String providerPhone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("订单详情");
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_order_details;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
        tradeNo = getIntent().getStringExtra("tradeNo");
        isShopProduct = getIntent().getBooleanExtra("isShopProduct", false);
        productBuyAdapter = new ProductBuyAdapter(mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext));
        recyclerView.setAdapter(productBuyAdapter);
        if (isShopProduct) {
            tvAddress.setVisibility(View.GONE);
            llShop.setVisibility(View.VISIBLE);
        } else {
            llShop.setVisibility(View.GONE);
        }
        getData();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getData();
    }

    public void getData() {
        OkGo.<CommonReturnData<OrderDetailBean>>get(Constants.BASE_URL + "hot/queryOrderSingle")
                .params("tradeNo", tradeNo)
                .params("type", isShopProduct ? "1" : "0")
                .execute(new DialogCallback<CommonReturnData<OrderDetailBean>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<OrderDetailBean> commonReturnData) {
                        OrderDetailBean orderDetailBean = commonReturnData.getData();
                        tvTradeNo.setText("订单编号：" + tradeNo);
                        totalMoney = orderDetailBean.getAmount();
                        tvPrice.setText("金额：￥" + orderDetailBean.getAmount());
                        if (isShopProduct) {
                            tvTradeState.setText(getSelfStateShow(orderDetailBean.getSelf_state()));
                            llGoPay.setVisibility("1".equals(orderDetailBean.getSelf_state()) ? View.VISIBLE : View.GONE);
                        } else {
                            tvTradeState.setText(getStateShow(orderDetailBean.getState()));
                            llGoPay.setVisibility("0".equals(orderDetailBean.getState()) ? View.VISIBLE : View.GONE);
                        }
                        providerPhone = orderDetailBean.getProviderPhone();
                        tvTel.setText("电话:" + providerPhone);//自提商品才有
                        tvShopAddress.setText("地址:" + orderDetailBean.getProviderAddress());//自提商品才有
                        tvShopName.setText(orderDetailBean.getProviderName());

                        tvAddress.setText(orderDetailBean.getReceiveAddress());
                        tvMobile.setText(orderDetailBean.getReceiveMobile());
                        tvName.setText(orderDetailBean.getReceiveName());
                        mData.clear();
                        mData.addAll(orderDetailBean.getMerclist());
                        productBuyAdapter.notifyDataSetChanged();
                        if (TextUtils.isEmpty(orderDetailBean.getReceiveNotes())) {
                            llNote.setVisibility(View.GONE);
                        } else {
                            llNote.setVisibility(View.VISIBLE);
                            tvNote.setText(orderDetailBean.getReceiveNotes());
                        }
                    }
                });
    }

    /**
     * 获取在线订单状态
     *
     * @return
     */
    private String getStateShow(String state) {
        switch (state) {
            case "0":
                return "待支付";
            case "1":
                return "待发货";
            case "2":
                return "待收货";
            case "3":
                return "已完成";
        }
        return "";
    }


    /**
     * 获取自提订单状态
     *
     * @return
     */
    private String getSelfStateShow(String state) {
        switch (state) {
            case "0":
                return "待确认";
            case "1":
                return "待支付";
            case "2":
                return "待提货";
            case "3":
                return "已完成";
        }
        return "";
    }

    @OnClick({R.id.tv_go_pay, R.id.img_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_call:
                if (!TextUtils.isEmpty(providerPhone)) {
                    callAction(providerPhone);
                }
                break;
            case R.id.tv_go_pay:
                Intent intent = new Intent(mContext, TradePayActivity.class);
                intent.putExtra("tradeNo", tradeNo);
                intent.putExtra("isShopProduct", isShopProduct);
                if (!TextUtils.isEmpty(totalMoney)) {
                    intent.putExtra("totalMoney", totalMoney);
                }
                intent.putExtra("hasNoBalance", true);
                startActivity(intent);
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean data) {
        switch (data.getEvent()) {
            case EventBean.ORDER_REPAY_SUCCESS:
                getData();
                break;
        }
    }

    /**
     * 拨打电话
     *
     * @param mobileNumber
     */
    public void callAction(String mobileNumber) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobileNumber));
        startActivity(callIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
