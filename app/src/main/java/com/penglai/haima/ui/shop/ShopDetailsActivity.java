package com.penglai.haima.ui.shop;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.adapter.ShopProductAdapter;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ShopDataBean;
import com.penglai.haima.bean.ShopProductBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.config.GlideApp;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.ViewHWRateUtil;
import com.penglai.haima.widget.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopDetailsActivity extends BaseActivity {
    ShopDataBean shopDataBean;
    @BindView(R.id.img_pic)
    ImageView imgPic;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ShopProductAdapter shopProductAdapter;
    List<ShopProductBean> shopProductBeans = new ArrayList<>();
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_shop_details;
    }

    @Override
    public void init() {
        emptyView = getEmptyView();
        ViewHWRateUtil.setHeightWidthRate(mContext, rlTop, 2.13);//640/300
        shopDataBean = (ShopDataBean) getIntent().getSerializableExtra("shopDataBean");
        setTitleMiddleText(shopDataBean.getProvider_name());
        tvTel.setText("电话:" + shopDataBean.getProvider_phone());
        tvAddress.setText("地址:" + shopDataBean.getProvider_address());
        GlideApp.with(mContext).load(Constants.URL_FOR_PIC2 + shopDataBean.getProvider_cover_image() + Constants.PIC_JPG).defaultOptions().into(imgPic);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this, R.drawable.divider_drawable01));
        shopProductAdapter = new ShopProductAdapter(shopProductBeans);
        recyclerView.setAdapter(shopProductAdapter);
        getShopProductList();
    }


    /**
     * 获取商家列表
     */
    private void getShopProductList() {
        OkGo.<CommonReturnData<List<ShopProductBean>>>get(Constants.BASE_URL + "hot/getHotListByProvider")
                .params("providerId", shopDataBean.getProvider_id())
                .execute(new DialogCallback<CommonReturnData<List<ShopProductBean>>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<List<ShopProductBean>> commonReturnData) {
                        shopProductBeans.clear();
                        shopProductBeans.addAll(commonReturnData.getData());
                        shopProductBeans.addAll(commonReturnData.getData());
                        shopProductBeans.addAll(commonReturnData.getData());
                        shopProductAdapter.notifyDataSetChanged();
                        if (shopProductBeans.isEmpty()) {
                            shopProductAdapter.setEmptyView(emptyView);
                        }
                    }
                });
    }
}
