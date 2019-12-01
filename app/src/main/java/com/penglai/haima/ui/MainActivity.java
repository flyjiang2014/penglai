package com.penglai.haima.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.base.Constants;
import com.penglai.haima.ui.index.PersonIndexFragment;
import com.penglai.haima.ui.index.ProductIndexFragment;
import com.penglai.haima.ui.index.ServiceIndexFragment;
import com.penglai.haima.ui.index.ShopIndexFragment;
import com.penglai.haima.utils.ActivityManager;
import com.penglai.haima.utils.AndroidWorkaround;
import com.penglai.haima.utils.SharepreferenceUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import in.srain.cube.util.CLog;
import io.reactivex.functions.Consumer;
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
    LocationClient mLocationClient = null;
    BDLocationListener myListener = new MyLocationListener();

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

        Set<String> tags = new HashSet<>();
        tags.add("tag1");
        JPushInterface.setTags(mContext, 0, tags);
    }

    @Override
    public void init() {
        tab = findViewById(R.id.tab);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener);
        permissionApply();
        //注意这里调用了custom()方法
        final NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.index_unselect, R.drawable.index_select, "在线"))
                .addItem(newItem(R.drawable.shop_unselect, R.drawable.shop_select, "自提"))
                .addItem(newItem(R.drawable.service_unselect, R.drawable.service_select, "服务"))
                .addItem(newItem(R.drawable.me_unselect, R.drawable.me_select, "我的"))
                .build();
        ProductIndexFragment productIndexFragment = ProductIndexFragment.getInstance(0);
        ShopIndexFragment shopIndexFragment = ShopIndexFragment.getInstance(1);
        ServiceIndexFragment serviceIndexFragment = ServiceIndexFragment.getInstance(2);
        PersonIndexFragment personIndexFragment = PersonIndexFragment.getInstance(3);
        fragments.add(productIndexFragment);
        fragments.add(shopIndexFragment);
        fragments.add(serviceIndexFragment);
        fragments.add(personIndexFragment);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        navigationController.setupWithViewPager(viewPager);
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
        if (viewPager.getCurrentItem() == 3) {
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

    /**
     * 定位初始化
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        // option.setNeedNewVersionRgc(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            int locType = location.getLocType();
            if (locType == BDLocation.TypeGpsLocation || locType == BDLocation.TypeNetWorkLocation || locType == BDLocation.TypeOffLineLocation || locType == BDLocation.TypeCacheLocation) {
                String city = location.getCity();
                SharepreferenceUtil.saveString(Constants.CURRENT_CITY, city);
            } else {
            }
        }
    }

    /**
     * permission申请
     */
    @SuppressLint("CheckResult")
    public void permissionApply() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            initLocation();
                            mLocationClient.start();
                            // 用户已经同意该权限
                            CLog.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            CLog.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            showToast("定位失败,权限被关闭,");
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        mLocationClient.stop();
    }
}
