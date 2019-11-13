package com.penglai.haima.ui;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.ui.index.PersonIndexFragment;
import com.penglai.haima.ui.index.ProductIndexFragment;
import com.penglai.haima.ui.index.ServiceIndexFragment;
import com.penglai.haima.utils.ActivityManager;
import com.penglai.haima.utils.AndroidWorkaround;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private PageNavigationView tab;
    private List<BaseFragmentV4> fragments = new ArrayList<>();

    @Override
    public int setBaseContentView() {
        setIsShowTitle(false);
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }

    }

    @Override
    public void init() {
        tab = findViewById(R.id.tab);
        //注意这里调用了custom()方法
        final NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.index_unselect, R.drawable.index_select, "商品"))
                .addItem(newItem(R.drawable.service_unselect, R.drawable.service_select, "服务"))
                .addItem(newItem(R.drawable.me_unselect, R.drawable.me_select, "我的"))
                .build();
        ProductIndexFragment productIndexFragment = ProductIndexFragment.getInstance(0);
        ServiceIndexFragment serviceIndexFragment = ServiceIndexFragment.getInstance(1);
        PersonIndexFragment personIndexFragment = PersonIndexFragment.getInstance(2);
        fragments.add(productIndexFragment);
        fragments.add(serviceIndexFragment);
        fragments.add(personIndexFragment);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        navigationController.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (fragments.get(position).getVisibleTimes() > 1) {
                    fragments.get(position).initData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                viewPager.setCurrentItem(index);
            }

            @Override
            public void onRepeat(int index) {
                //重复选中时触发
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (viewPager.getCurrentItem() == 2) {
            fragments.get(viewPager.getCurrentItem()).initData();
        }
    }

    /**
     * 连续按两次返回键关闭程序
     */
    private long mExitClickTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitClickTime > 2000) {
            showToast("再按一次返回键关闭程序");
            mExitClickTime = System.currentTimeMillis();
        } else {
            ActivityManager.finishAllActivity();
            System.exit(0);
        }
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF1296DB);
        return normalItemView;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
