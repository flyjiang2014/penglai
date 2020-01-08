package com.penglai.haima.ui.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.adapter.ShopAdapter;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ShopDataBean;
import com.penglai.haima.callback.JsonFragmentCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.ui.shop.ShopDetailsActivity;
import com.penglai.haima.utils.PhoneUtil;
import com.penglai.haima.utils.ViewHWRateUtil;
import com.penglai.haima.widget.GlideRoundImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import static com.penglai.haima.base.BaseActivity.PULL_DOWN_TIME;

/**
 * Created by  on 2019/11/8.
 * 文件说明：商家列表展示
 */
public class ShopIndexFragment extends BaseFragmentV4 implements OnRefreshListener {

    private int state = -1;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private View view_top;
    private View emptyView;
    private Banner banner;
    private ShopAdapter shopAdapter;
    List<ShopDataBean> shopDataBeans = new ArrayList<>();

    @Override
    protected View initView(LayoutInflater inflater) {
        state = getArguments().getInt("state");
        View view = inflater.inflate(R.layout.fragment_shop_index, null);
        initView(view);
        initViewData();
        return view;
    }

    public static ShopIndexFragment getInstance(int state) {
        ShopIndexFragment fragment = new ShopIndexFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initViewData() {
        ViewGroup.LayoutParams params = view_top.getLayoutParams();
        //获取当前控件的布局对象
        params.height = PhoneUtil.getStatusHeight(getActivity()) + 5;//设置当前控件布局的高度
        view_top.setLayoutParams(params);
        ViewHWRateUtil.setHeightWidthRate(mContext, banner, 2.13);//640/300
        shopAdapter = new ShopAdapter(shopDataBeans);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(shopAdapter);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setEnableRefresh(false);
        smartRefreshLayout.setOnRefreshListener(this);
        List<String> images = new ArrayList<>();
        images.add(Constants.URL_FOR_PIC + "banner/banner1.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner2.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner3.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner4.png");
        images.add(Constants.URL_FOR_PIC + "banner/banner5.png");
        banner.setImageLoader(new GlideRoundImageLoader());
        banner.setImages(images);
        banner.start();
        shopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, ShopDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("shopDataBean", shopDataBeans.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void initData() {

        getShopList();
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        getShopList();
    }

    private void initView(View view) {
        smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        banner = view.findViewById(R.id.banner);
        view_top = view.findViewById(R.id.view);
        emptyView = getEmptyView();
    }

    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        refreshLayout.getLayout().postDelayed(new Runnable() {
            @Override
            public void run() {
                getShopList();
                refreshLayout.finishRefresh();
                refreshLayout.setNoMoreData(false);
            }
        }, PULL_DOWN_TIME);
    }

    /**
     * 获取商家列表
     */
    private void getShopList() {
//        if (!SharepreferenceUtil.getString(Constants.CURRENT_CITY).contains("无锡")) {
//            mLoadingLayout.setStatus(LoadingLayout.Error);
//            mLoadingLayout.setErrorText("当前城市暂未开放,\n敬请期待")
//                    .setErrorImageVisible(false)
//                    .setErrorTextSize(18)
//                    .setReloadButtonBackgroundResource(R.color.transparent)
//                    .setReloadButtonText("");
//            return;
//        }
        OkGo.<CommonReturnData<List<ShopDataBean>>>get(Constants.BASE_URL + "provider/getProviderInfo")
                .execute(new JsonFragmentCallback<CommonReturnData<List<ShopDataBean>>>(this, true, true) {
                    @Override
                    public void onSuccess(CommonReturnData<List<ShopDataBean>> commonReturnData) {
                        shopDataBeans.clear();
                        shopDataBeans.addAll(commonReturnData.getData());
                        shopAdapter.notifyDataSetChanged();
                        if (shopDataBeans.isEmpty()) {
                            shopAdapter.setEmptyView(emptyView);
                        }
                    }
                });
    }

}
