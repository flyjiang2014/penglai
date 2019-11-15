package com.penglai.haima.ui.order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.bean.EventBean;
import com.penglai.haima.widget.PagerSlidingTabStrip;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class OrderListActivity extends BaseActivity {

    @BindView(R.id.my_tabs)
    PagerSlidingTabStrip myTabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private DisplayMetrics dm;
    private List<OrderFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleMiddleText("我的订单");
        for (int i = 0; i < 5; i++) { //添加fragment
            OrderFragment fragment = OrderFragment.getInstance(i - 1);
            fragments.add(fragment);
        }
    }

    @Override
    public int setBaseContentView() {
        return R.layout.activity_order_list;
    }


    @Override
    public void init() {
        EventBus.getDefault().register(this);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        myTabs.setViewPager(viewPager);
        setTabsValue();
    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        myTabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        myTabs.setDividerColor(getResources().getColor(R.color.bg_app));
        myTabs.setDividerPadding(15);
//        // 设置Tab底部线的高度
        myTabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        myTabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, dm));
        // 设置Tab标题文字的大小
        myTabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 10, dm));
        // 设置Tab Indicator的颜色
        myTabs.setIndicatorColor(getResources().getColor(
                R.color.orange));
        // 设置选中Tab文字的颜色
        myTabs.setSelectedTextColor(getResources().getColor(
                R.color.orange));
        // 设置文字最底部横线颜色
        myTabs.setUnderlineColor(getResources().getColor(R.color.bg_app));
        // 设置底部横线MatchParent
        myTabs.setIndicatorMatchTab(true);

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"全部", "待支付", "待发货", "待收货", "已完成"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBean data) {
        switch (data.getEvent()) {
            case EventBean.ORDER_REPAY_SUCCESS:
                fragments.get(viewPager.getCurrentItem()).initData();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
