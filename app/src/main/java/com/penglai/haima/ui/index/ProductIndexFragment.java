package com.penglai.haima.ui.index;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.adapter.ProductAdapter;
import com.penglai.haima.adapter.ProductForCarAdapter;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.EventBean;
import com.penglai.haima.bean.ProductBean;
import com.penglai.haima.bean.ProductDataBean;
import com.penglai.haima.bean.ProductSelectBean;
import com.penglai.haima.callback.JsonFragmentCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.ui.SearchActivity;
import com.penglai.haima.ui.order.ProductOrderSubmitActivity;
import com.penglai.haima.utils.DensityUtil;
import com.penglai.haima.utils.MathUtil;
import com.penglai.haima.utils.PhoneUtil;
import com.penglai.haima.utils.ToastUtil;
import com.penglai.haima.utils.ViewHWRateUtil;
import com.penglai.haima.widget.DividerItemDecoration;
import com.penglai.haima.widget.GlideRoundImageLoader;
import com.penglai.haima.widget.MyListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.penglai.haima.base.BaseActivity.PULL_DOWN_TIME;
import static com.penglai.haima.base.BaseActivity.PULL_UP_TIME;

/**
 * Created by ${flyjiang} on 2019/10/22.
 * 文件说明：商品展示列表
 */
public class ProductIndexFragment extends BaseFragmentV4 implements OnRefreshListener, OnLoadMoreListener {
    private int state = -1;
    RecyclerView recyclerView;
    SmartRefreshLayout smartRefreshLayout;
    ProductAdapter productAdapter;
    private List<ProductBean> productBeanList = new ArrayList<>();
    private View emptyView;
    private LinearLayout ll_shop_car;
    private View bottomSheet;
    private View view_top;
    private BottomSheetLayout bottomSheetLayout;
    private TextView tv_car;
    private TextView tv_go_charge, tv_total_money, tv_search_key;
    private Banner banner;
    private Double totalMoney = 0d;
    private TextView tv_show_num;
    private ImageView img_delete;
    private LinearLayout ll_search;
    private SparseArray<ProductBean> selectedList;
    private ProductForCarAdapter productForCarAdapter;//底部购物车的adapter
    private View headView;
    private int currentPage = 1;
    private int end = 1;
    private String key_word;
    private LinearLayout ll_empty_head;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected View initView(LayoutInflater inflater) {
        state = getArguments().getInt("state");
        View view = inflater.inflate(R.layout.fragment_product_index, null);
        initView(view);
        initViewData();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initViewData() {
        ViewGroup.LayoutParams params = view_top.getLayoutParams();
        //获取当前控件的布局对象
        params.height = PhoneUtil.getStatusHeight(getActivity()) + 5;//设置当前控件布局的高度
        view_top.setLayoutParams(params);
        ViewHWRateUtil.setHeightWidthRate(mContext, banner, 2.13);//640/300
        selectedList = new SparseArray<>();
        productAdapter = new ProductAdapter(this, productBeanList);
        productAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                updateShopCarFromList(productBeanList.get(position), view);
            }
        });
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.addItemDecoration(new DividerItemDecoration(mContext));
        //, R.drawable.divider_drawable01)
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, R.drawable.divider_drawable).setHasHeadView(true).setForSpecial(true));
        productAdapter.addHeaderView(headView);
        recyclerView.setAdapter(productAdapter);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
//        smartRefreshLayout.setEnableLoadMore(true);
//        smartRefreshLayout.setEnableRefresh(true);
        List<String> images = new ArrayList<>();
        images.add(Constants.URL_FOR_PIC + "banner/banner1.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner2.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner3.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner4.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner5.png");
        banner.setImageLoader(new GlideRoundImageLoader());
        banner.setImages(images);
        banner.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initView(View view) {
        headView = LayoutInflater.from(mContext).inflate(R.layout.banner_head_view_layout, null);
        emptyView = getEmptyView();
        ll_empty_head = emptyView.findViewById(R.id.ll_head);
        TextView tv_text = emptyView.findViewById(R.id.tv_text);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, DensityUtil.dp2px(mContext, 80), 0, 0);
        tv_text.setLayoutParams(lp);
        recyclerView = view.findViewById(R.id.recyclerView);
        banner = headView.findViewById(R.id.banner);
        view_top = view.findViewById(R.id.view);
        img_delete = view.findViewById(R.id.img_delete);
        smartRefreshLayout = view.findViewById(R.id.smartRefreshLayout);
        bottomSheetLayout = view.findViewById(R.id.bottomSheetLayout);
        tv_total_money = view.findViewById(R.id.tv_total_money);
        tv_show_num = view.findViewById(R.id.tv_show_num);
        ll_search = view.findViewById(R.id.ll_search);
        tv_search_key = view.findViewById(R.id.tv_search_key);
        tv_car = view.findViewById(R.id.tv_car);
        tv_go_charge = view.findViewById(R.id.tv_go_charge);
        ll_shop_car = view.findViewById(R.id.ll_shop_car);
        ll_shop_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet();
            }
        });
        tv_go_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedList.size() == 0) {
                    ToastUtil.showToast("至少选择一件商品");
                    return;
                }
                List<ProductSelectBean> mData = new ArrayList<>();
                ProductSelectBean data;
                ProductBean productBean;
                for (int i = 0; i < selectedList.size(); i++) {
                    productBean = selectedList.valueAt(i);
                    data = new ProductSelectBean(productBean.getId(), productBean.getContent(), productBean.getImage_name(), productBean.getModel(),
                            productBean.getPrice(), productBean.getTitle(), productBean.getAmount());
                    mData.add(data);
                }
                Intent intent = new Intent(mContext, ProductOrderSubmitActivity.class);
                intent.putExtra("totalMoney", MathUtil.round_half_up(String.valueOf(totalMoney), 0));
                intent.putExtra("mData", (Serializable) mData);
                startActivity(intent);
            }
        });
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, SearchActivity.class));
            }
        });
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_delete.setVisibility(View.GONE);
                key_word = "";
                tv_search_key.setText("");
                getProductListData(true);
            }
        });

    }

    @Override
    public void initData() {
        if (productBeanList.size() <= 0) {
            getProductListData(true);
            getShopCarData();//待确认
        }
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getProductListData(true);
        getShopCarData();
    }

    public static ProductIndexFragment getInstance(int state) {
        ProductIndexFragment fragment = new ProductIndexFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 获取商品列表
     */
    private void getProductListData(boolean isInit) {
        if (isInit) {
            currentPage = 1;
            // clearCart();
            recyclerView.smoothScrollToPosition(0);
            smartRefreshLayout.setNoMoreData(false);
        }
        OkGo.<CommonReturnData<ProductDataBean>>get(Constants.BASE_URL + "hot/getHotList")
                .params("needBreak", 1)
                .params("content", key_word)
                .params("isSearch", TextUtils.isEmpty(key_word) ? "0" : "1")
                .params("pageNum", currentPage)
                .params("singleNum", Constants.PAGE_SIZE)
                .execute(new JsonFragmentCallback<CommonReturnData<ProductDataBean>>(this, true, true) {
                    @Override
                    public void onSuccess(CommonReturnData<ProductDataBean> commonReturnData) {
                        if (currentPage == 1) productBeanList.clear();
                        productBeanList.addAll(commonReturnData.getData().getData());
                        productAdapter.notifyDataSetChanged();
                        end = Integer.parseInt(commonReturnData.getData().getEnd());
                        currentPage = commonReturnData.getData().getCurrentPage();
                        currentPage++;
//                        if (productBeanList.size() == 0) {
//                            productAdapter.setEmptyView(emptyView);
//                        }
                        if (productBeanList.size() == 0) {
                            productAdapter.setEmptyView(emptyView);
                            productAdapter.removeAllHeaderView();
                            if (ll_empty_head.getChildCount() == 0) {
                                ll_empty_head.addView(headView);
                            }
                        } else {
                            ll_empty_head.removeAllViews();
                            if (productAdapter.getHeaderLayoutCount() == 0) {
                                productAdapter.addHeaderView(headView);
                            }
                        }
                        if ("1".equals(end) && productBeanList.size() <= Constants.PAGE_SIZE) {
                            smartRefreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                        }
                    }
                });
    }


    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                getProductListData(true);
                refreshLayout.finishRefresh();
                refreshLayout.setNoMoreData(false);
            }
        }, PULL_DOWN_TIME);
    }

    @Override
    public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (end == 0) {
                    //  currentPage++;
                    getProductListData(false);
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                }
            }
        }, PULL_UP_TIME);

    }

    /**
     * 获取购物车数据
     */
    private void getShopCarData() {
        OkGo.<CommonReturnData<List<ProductBean>>>get(Constants.BASE_URL + "hot/queryCart")
                .execute(new JsonFragmentCallback<CommonReturnData<List<ProductBean>>>(this, true, true) {
                    @Override
                    public void onSuccess(CommonReturnData<List<ProductBean>> commonReturnData) {
                        selectedList.clear();
                        List<ProductBean> lists = commonReturnData.getData();
                        for (int i = 0, size = lists.size(); i < size; i++) {
                            selectedList.append(lists.get(i).getId(), lists.get(i));
                        }
                        update(true);
                    }
                });
    }

    /**
     * 从列表新增商品
     */
    public void updateShopCarFromList(final ProductBean productBean, final View view) {
        OkGo.<CommonReturnData<Object>>get(Constants.BASE_URL + "hot/updateCart")
                .params("id", productBean.getId())
                .params("oper", 1)
                .execute(new JsonFragmentCallback<CommonReturnData<Object>>(this, false, false) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> commonReturnData) {
                        int[] loc = new int[2];
                        view.getLocationInWindow(loc);
                        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                        view.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                        ImageView ball = new ImageView(mContext);
                        ball.setImageResource(R.drawable.number);
                        setAnim(ball, startLocation);// 开始执行动
                        handlerCarNum(1, productBean, true);//1 表示新增
                    }
                });
    }

    /**
     * 从购物车新增减少商品
     */
    public void updateShopCarFromBasic(final boolean isAdd, final ProductBean productBean, final View view) {
        OkGo.<CommonReturnData<Object>>get(Constants.BASE_URL + "hot/updateCart")
                .params("id", productBean.getId())
                .params("oper", isAdd ? 1 : 0)
                .execute(new JsonFragmentCallback<CommonReturnData<Object>>(this, false, false) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> commonReturnData) {
                        if (isAdd) {
                            handlerCarNum(1, productBean, true);
                        } else {
                            handlerCarNum(0, productBean, true);
                        }
                    }
                });
    }

    /**
     * 清空购物车
     */
    private void cleatShopCar() {
        OkGo.<CommonReturnData<Object>>get(Constants.BASE_URL + "hot/deleteCart")
                .execute(new JsonFragmentCallback<CommonReturnData<Object>>(this, false, true) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> commonReturnData) {
                        clearCart();
                    }
                });
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_sheet, (ViewGroup) getActivity().getWindow().getDecorView(), false);
        MyListView lv_product = (MyListView) view.findViewById(R.id.lv_product);
        TextView clear = (TextView) view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleatShopCar();
                // clearCart();
            }
        });
        productForCarAdapter = new ProductForCarAdapter(this, productAdapter, selectedList);
        lv_product.setAdapter(productForCarAdapter);
        return view;
    }


    //清空购物车
    public void clearCart() {
        selectedList.clear();
        for (ProductBean productBean : productBeanList) {
            productBean.setAmount(0);
        }
        productAdapter.notifyDataSetChanged();
        update(true);
    }

    public void handlerCarNum(int type, ProductBean productBean, boolean refreshGoodList) {
        if (type == 0) {
            ProductBean temp = selectedList.get(productBean.getId());
            if (temp != null) {
                if (temp.getAmount() < 2) {
                    temp.setAmount(0);
                    selectedList.remove(productBean.getId());
                } else {
                    int i = temp.getAmount();
                    temp.setAmount(--i);
                }
            }

        } else if (type == 1) {
            ProductBean temp = selectedList.get(productBean.getId());
            if (temp == null) {
                productBean.setAmount(1);
                selectedList.append(productBean.getId(), productBean);
            } else {
                int i = temp.getAmount();
                temp.setAmount(++i);
            }
        }
        update(refreshGoodList);
    }


    //根据商品id获取当前商品的采购数量
    public int getSelectedItemCountById(int id) {
        ProductBean temp = selectedList.get(id);
        if (temp == null) {
            return 0;
        }
        return temp.getAmount();
    }


    //刷新布局 总价、购买数量等
    private void update(boolean refreshGoodList) {
        int size = selectedList.size();
        int count = 0;
        totalMoney = 0.00;
        for (int i = 0; i < size; i++) {
            ProductBean item = selectedList.valueAt(i);
            count += item.getAmount();
            totalMoney += item.getAmount() * Double.parseDouble(item.getPrice());
        }
        tv_total_money.setText("￥" + MathUtil.round_half_down(String.valueOf(totalMoney), 0));

        if (count < 1) {
            tv_show_num.setVisibility(View.GONE);
        } else {
            tv_show_num.setVisibility(View.VISIBLE);
        }

        tv_show_num.setText(String.valueOf(count));

//        if (productAdapter != null) {
//            productAdapter.notifyDataSetChanged();
//        }

        if (productForCarAdapter != null) {
            productForCarAdapter.notifyDataSetChanged();
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
        ViewGroup rootView = (ViewGroup) this.getActivity().getWindow().getDecorView();
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
        tv_car.getLocationInWindow(endLocation);
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
            case EventBean.TRADE_PAY_SUCCESS:
                //  clearCart();
                getProductListData(true);
                break;
            case EventBean.SEARCH_ACTION:
                key_word = (String) data.getData();
                img_delete.setVisibility(View.VISIBLE);
                tv_search_key.setText(key_word);
                getProductListData(true);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
