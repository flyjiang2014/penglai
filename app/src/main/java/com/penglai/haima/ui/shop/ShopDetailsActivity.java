package com.penglai.haima.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.adapter.ShopProductAdapter;
import com.penglai.haima.adapter.ShopProductForCarAdapter;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.EventBean;
import com.penglai.haima.bean.ProductSelectBean;
import com.penglai.haima.bean.ShopDataBean;
import com.penglai.haima.bean.ShopProductBean;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.config.GlideApp;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.ui.order.ProductOrderSubmitActivity;
import com.penglai.haima.utils.MathUtil;
import com.penglai.haima.utils.ToastUtil;
import com.penglai.haima.utils.ViewHWRateUtil;
import com.penglai.haima.widget.DividerGridItemDecoration;
import com.penglai.haima.widget.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    @BindView(R.id.tv_car)
    TextView tvCar;
    @BindView(R.id.tv_show_num)
    TextView tvShowNum;
    @BindView(R.id.ll_shop_car)
    LinearLayout llShopCar;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_go_charge)
    TextView tvGoCharge;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.bottomSheetLayout)
    BottomSheetLayout bottomSheetLayout;
    private View emptyView;
    private SparseArray<ShopProductBean> selectedList;
    private ShopProductForCarAdapter shopProductForCarAdapter;//底部购物车的adapter
    private View bottomSheet;
    private Double totalMoney = 0d;

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
        EventBus.getDefault().register(this);
        emptyView = getEmptyView();
        selectedList = new SparseArray<>();
        ViewHWRateUtil.setHeightWidthRate(mContext, rlTop, 2.13);//640/300
        shopDataBean = (ShopDataBean) getIntent().getSerializableExtra("shopDataBean");
        setTitleMiddleText(shopDataBean.getProvider_name());
        tvTel.setText("电话:" + shopDataBean.getProvider_phone());
        tvAddress.setText("地址:" + shopDataBean.getProvider_address());
        GlideApp.with(mContext).load(Constants.URL_FOR_PIC2 + shopDataBean.getProvider_cover_image() + Constants.PIC_JPG).defaultOptions().into(imgPic);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this, R.drawable.divider_drawable01));
        shopProductAdapter = new ShopProductAdapter(this, shopProductBeans);
        recyclerView.setAdapter(shopProductAdapter);
        getShopProductList();
    }

    /**
     * 获取商品列表
     */
    private void getShopProductList() {
        OkGo.<CommonReturnData<List<ShopProductBean>>>get(Constants.BASE_URL + "hot/getHotListByProvider")
                .params("providerId", shopDataBean.getProvider_id())
                .execute(new DialogCallback<CommonReturnData<List<ShopProductBean>>>(this, true) {
                    @Override
                    public void onSuccess(CommonReturnData<List<ShopProductBean>> commonReturnData) {
                        shopProductBeans.clear();
                        shopProductBeans.addAll(commonReturnData.getData());
                        shopProductAdapter.notifyDataSetChanged();
                        if (shopProductBeans.isEmpty()) {
                            shopProductAdapter.setEmptyView(emptyView);
                        }
                    }
                });
    }

    @OnClick({R.id.ll_shop_car, R.id.tv_go_charge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shop_car:
                showBottomSheet();
                break;
            case R.id.tv_go_charge:
                if (selectedList.size() == 0) {
                    ToastUtil.showToast("至少选择一件商品");
                    return;
                }
                List<ProductSelectBean> mData = new ArrayList<>();
                ProductSelectBean data;
                ShopProductBean productBean;
                for (int i = 0; i < selectedList.size(); i++) {
                    productBean = selectedList.valueAt(i);
                    data = new ProductSelectBean(productBean.getId(), productBean.getContent(), productBean.getImage_name(), productBean.getModel(),
                            productBean.getPrice(), productBean.getTitle(), productBean.getChoose_number());
                    mData.add(data);
                }
                Intent intent = new Intent(mContext, ProductOrderSubmitActivity.class);
                intent.putExtra("totalMoney", MathUtil.round_half_up(String.valueOf(totalMoney), 0));
                intent.putExtra("isShopProduct", true);
                intent.putExtra("providerId", shopDataBean.getProvider_id());
                intent.putExtra("mData", (Serializable) mData);
                startActivity(intent);
                break;
        }
    }

    //创建购物车view
    private void showBottomSheet() {
        bottomSheet = createBottomSheetView();
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            if (selectedList.size() != 0) {
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
    }

    //查看购物车布局
    private View createBottomSheetView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_sheet, (ViewGroup) this.getWindow().getDecorView(), false);
        MyListView lv_product = (MyListView) view.findViewById(R.id.lv_product);
        TextView clear = (TextView) view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCart();
            }
        });
        shopProductForCarAdapter = new ShopProductForCarAdapter(this, shopProductAdapter, selectedList);
        lv_product.setAdapter(shopProductForCarAdapter);
        return view;
    }


    //清空购物车
    public void clearCart() {
        selectedList.clear();
        for (ShopProductBean productBean : shopProductBeans) {
            productBean.setChoose_number(0);
        }
        shopProductAdapter.notifyDataSetChanged();
        update(true);
    }

    public void handlerCarNum(int type, ShopProductBean productBean, boolean refreshGoodList) {
        if (type == 0) {
            ShopProductBean temp = selectedList.get(productBean.getId());
            if (temp != null) {
                if (temp.getChoose_number() < 2) {
                    productBean.setChoose_number(0);
                    selectedList.remove(productBean.getId());
                } else {
                    int i = productBean.getChoose_number();
                    productBean.setChoose_number(--i);
                }
            }

        } else if (type == 1) {
            ShopProductBean temp = selectedList.get(productBean.getId());
            if (temp == null) {
                productBean.setChoose_number(1);
                selectedList.append(productBean.getId(), productBean);
            } else {
                int i = productBean.getChoose_number();
                productBean.setChoose_number(++i);
            }
        }
        update(refreshGoodList);
    }

    //根据商品id获取当前商品的采购数量
    public int getSelectedItemCountById(int id) {
        ShopProductBean temp = selectedList.get(id);
        if (temp == null) {
            return 0;
        }
        return temp.getChoose_number();
    }


    //刷新布局 总价、购买数量等
    private void update(boolean refreshGoodList) {
        int size = selectedList.size();
        int count = 0;
        totalMoney = 0.00;
        for (int i = 0; i < size; i++) {
            ShopProductBean item = selectedList.valueAt(i);
            count += item.getChoose_number();
            totalMoney += item.getChoose_number() * Double.parseDouble(item.getPrice());
        }
        tvTotalMoney.setText("￥" + MathUtil.round_half_down(String.valueOf(totalMoney), 0));

        if (count < 1) {
            tvShowNum.setVisibility(View.GONE);
        } else {
            tvShowNum.setVisibility(View.VISIBLE);
        }

        tvShowNum.setText(String.valueOf(count));

        if (shopProductAdapter != null) {
            shopProductAdapter.notifyDataSetChanged();
        }

        if (shopProductForCarAdapter != null) {
            shopProductForCarAdapter.notifyDataSetChanged();
        }

        if (bottomSheetLayout.isSheetShowing() && selectedList.size() < 1) {
            bottomSheetLayout.dismissSheet();
        }
    }


    /**
     * @param
     * @return void
     * @throws
     * @Description: 创建动画层
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    private ViewGroup anim_mask_layout;//动画层

    public void setAnim(final View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        tvCar.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean data) {
        switch (data.getEvent()) {
            case EventBean.TRADE_PAY_SUCCESS_FOR_SHOP:
                clearCart();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
