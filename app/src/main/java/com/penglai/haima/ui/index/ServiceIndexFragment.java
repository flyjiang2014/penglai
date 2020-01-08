package com.penglai.haima.ui.index;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.ServiceTypeBean;
import com.penglai.haima.callback.JsonFragmentCallback;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.utils.PhoneUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${flyjiang} on 2019/10/22.
 * 文件说明：服务列表模块
 */
public class ServiceIndexFragment extends BaseFragmentV4 implements OnTabSelectListener {

    private int state = -1;
    private SlidingTabLayout tab_layout;
    private ArrayList<BaseFragmentV4> mFragments = new ArrayList<>();
    private ViewPager viewPager;

    List<ServiceTypeBean> types = new ArrayList();
    private MyFragmentAdapter mAdapter;
    private View view_top;


    @Override
    protected View initView(LayoutInflater inflater) {
        state = getArguments().getInt("state");
        View view = inflater.inflate(R.layout.fragment_service_index, null);
        initView(view);
        initViewData();
        return view;
    }

    @Override
    protected void initViewData() {
        ViewGroup.LayoutParams params = view_top.getLayoutParams();
        //获取当前控件的布局对象
        params.height = PhoneUtil.getStatusHeight(getActivity()) + 5;//设置当前控件布局的高度
        view_top.setLayoutParams(params);
        mAdapter = new MyFragmentAdapter(getChildFragmentManager(), mFragments);
        viewPager.setAdapter(mAdapter);
    }

    private void initView(View view) {
        view_top = view.findViewById(R.id.view);
        viewPager = view.findViewById(R.id.viewPager);
        tab_layout = view.findViewById(R.id.tab_layout);
    }

    @Override
    public void initData() {
        if (types.size() <= 0) {
            getServiceTypes();
        }
    }


    public static ServiceIndexFragment getInstance(int state) {
        ServiceIndexFragment fragment = new ServiceIndexFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        if (types.size() <= 0) {
            getServiceTypes();
        }
    }

    /**
     * 获取标题
     */
    private void getServiceTypes() {
//        if (!SharepreferenceUtil.getString(Constants.CURRENT_CITY).contains("无锡")) {
//            mLoadingLayout.setStatus(LoadingLayout.Error);
//            mLoadingLayout.setErrorText("当前城市暂未开放,\n敬请期待")
//                    .setErrorImageVisible(false)
//                    .setErrorTextSize(18)
//                    .setReloadButtonBackgroundResource(R.color.transparent)
//                    .setReloadButtonText("");
//            return;
//        }
        OkGo.<CommonReturnData<List<ServiceTypeBean>>>get(Constants.BASE_URL + "service/getServiceType")
                .execute(new JsonFragmentCallback<CommonReturnData<List<ServiceTypeBean>>>(this, true, true) {
                    @Override
                    public void onSuccess(CommonReturnData<List<ServiceTypeBean>> commonReturnData) {
                        types.clear();
                        types.addAll(commonReturnData.getData());
                        mFragments.clear();
                        for (ServiceTypeBean serviceTypeBean : types) {
                            ServiceItemFragment fragment = ServiceItemFragment.getInstance(serviceTypeBean.getId());
                            mFragments.add(fragment);
                            mAdapter.notifyDataSetChanged();
                            tab_layout.setViewPager(viewPager);
//                            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                                @Override
//                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                                }
//
//                                @Override
//                                public void onPageSelected(int position) {
//                                    if (mFragments.get(position).getVisibleTimes() > 1) {
//                                        mFragments.get(position).initData();
//                                    }
//                                }
//
//                                @Override
//                                public void onPageScrollStateChanged(int state) {
//                                }
//                            });

                        }
                    }
                });
    }

    @Override
    public void onTabSelect(int i) {
    }

    @Override
    public void onTabReselect(int i) {
    }

    public class MyFragmentAdapter extends FragmentStatePagerAdapter {
        private ArrayList<BaseFragmentV4> mFragments;//碎片数组
        FragmentManager fm;

        public MyFragmentAdapter(FragmentManager fm, ArrayList<BaseFragmentV4> mFragments) {
            super(fm);
            this.fm = fm;
            this.mFragments = mFragments;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE; //这个是必须的
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return types.get(position).getType();
        }

        @Override
        public Fragment getItem(int position) {
            int size = mFragments.size();
            if (position >= size && size > 0)
                return mFragments.get(size - 1);
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
