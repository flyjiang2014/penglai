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
import com.penglai.haima.utils.AndroidWorkaround;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class Main2Activity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private PageNavigationView tab;
    private List<BaseFragmentV4> fragments = new ArrayList<>();

    @Override
    public int setBaseContentView() {
        setIsShowTitle(false);
        return R.layout.activity_main2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        }
        ProductIndexFragment productIndexFragment = ProductIndexFragment.getInstance(0);
        PersonIndexFragment personIndexFragment = PersonIndexFragment.getInstance(1);
        fragments.add(productIndexFragment);
        fragments.add(personIndexFragment);
    }

    @Override
    public void init() {
        tab = findViewById(R.id.tab);
        //注意这里调用了custom()方法
        final NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.index_unselect, R.drawable.index_select, "商品"))
                .addItem(newItem(R.drawable.me_unselect, R.drawable.me_select, "我的"))
                .build();
//         final  String s1 ="http://218.90.187.218:8888/upload/img/shopmaintype/20190529/6818b0e5d100480eaf1236c6142ee4e7.png";
//         final String s2 ="http://218.90.187.218:8888/upload/img/shopmaintype/20190529/d6b1099226ac4820aca27c5b66a18235.png";
//         Drawable d1 = loadImageFromNetwork(s1);
//         Drawable d2 = loadImageFromNetwork(s2);
//         navigationController.setDefaultDrawable(0,d1);
//         navigationController.setSelectedDrawable(0,d2);

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (fragments.get(position).getVisibleTimes() > 1 && position == 1) {
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

//    private Drawable loadImageFromNetwork(String imageUrl)
//    {
//        Drawable drawable = null;
//        try {
//            // 可以在这里通过文件名来判断，是否本地有此图片
//            drawable = Drawable.createFromStream(
//                    new URL(imageUrl).openStream(), "image.jpg");
//        } catch (IOException e) {
//            Log.d("test", e.getMessage());
//        }
//        if (drawable == null) {
//            Log.d("test", "null drawable");
//        } else {
//            Log.d("test", "not null drawable");
//        }
//
//        return drawable ;
//    }
//    public  Drawable getDrawable(String urlpath){
//        Drawable drawable = null;
//        try {
//            URL url = new URL(urlpath);
//            URLConnection conn = url.openConnection();
//            conn.connect();
//            InputStream in;
//            in = conn.getInputStream();
//            drawable = Drawable.createFromStream(in, "background");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return drawable;
//    }


    @Override
    protected void onRestart() { //返回列表页面刷新数据
        super.onRestart();
        if (viewPager.getCurrentItem() == 1) {
            fragments.get(viewPager.getCurrentItem()).initData();
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
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }
}
