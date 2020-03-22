package com.penglai.haima.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.azhon.appupdate.config.UpdateConfiguration;
import com.azhon.appupdate.listener.OnButtonClickListener;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lzy.okgo.OkGo;
import com.penglai.haima.BuildConfig;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.UpdateDataBean;
import com.penglai.haima.callback.JsonCallback;
import com.penglai.haima.dialog.UpdateShowingDialog;
import com.penglai.haima.okgomodel.CommonReturnData;
import com.penglai.haima.ui.index.PersonIndexFragment;
import com.penglai.haima.ui.index.ProductIndexFragment;
import com.penglai.haima.ui.index.ServiceIndexFragment;
import com.penglai.haima.ui.index.ShopIndexFragment;
import com.penglai.haima.utils.ActivityManager;
import com.penglai.haima.utils.AndroidWorkaround;
import com.penglai.haima.utils.PhoneUtil;
import com.penglai.haima.utils.SharepreferenceUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
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

public class MainActivity extends BaseActivity implements OnDownloadListener, OnButtonClickListener {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private PageNavigationView tab;
    private List<BaseFragmentV4> fragments = new ArrayList<>();
    LocationClient mLocationClient = null;
    BDLocationListener myListener = new MyLocationListener();
    UpdateShowingDialog updateShowingDialog;

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
        updateShowingDialog = new UpdateShowingDialog(this);
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
        postAppInfo();
        getAppUpdate();
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

    private void postAppInfo() {
        OkGo.<CommonReturnData<Object>>get(Constants.BASE_URL + "admin/insertUpdate")
                .params("brand", Build.BRAND)
                .params("model", PhoneUtil.getPhoneModel())
                .params("platform", "Android")
                .params("osVersion", PhoneUtil.getPhoneAndroidVERSION())
                .params("appVersion", BuildConfig.VERSION_NAME)
                .execute(new JsonCallback<CommonReturnData<Object>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> commonReturnData) {
                    }
                });
    }

    /**
     * 获取下载链接
     */
    private void getAppUpdate() {
        OkGo.<CommonReturnData<UpdateDataBean>>get(Constants.BASE_URL + "admin/queryUpdate")
                .params("updateId", BuildConfig.VERSION_CODE)
                .execute(new JsonCallback<CommonReturnData<UpdateDataBean>>(this) {
                    @Override
                    public void onSuccess(CommonReturnData<UpdateDataBean> commonReturnData) {
                        startUpdate(commonReturnData.getData());
                    }
                });
    }

    private DownloadManager manager;

    private void startUpdate(UpdateDataBean data) {
        /*
         * 整个库允许配置的内容
         * 非必选
         */
        UpdateConfiguration configuration = new UpdateConfiguration()
                //输出错误日志
                .setEnableLog(false)
                //设置自定义的下载
                //.setHttpManager()
                //下载完成自动跳动安装页面
                .setJumpInstallPage(true)
                //设置对话框背景图片 (图片规范参照demo中的示例图)
                //.setDialogImage(R.drawable.ic_dialog)
                //设置按钮的颜色
                //.setDialogButtonColor(Color.parseColor("#E743DA"))
                //设置对话框强制更新时进度条和文字的颜色
                //.setDialogProgressBarColor(Color.parseColor("#E743DA"))
                //设置按钮的文字颜色
                .setDialogButtonTextColor(Color.WHITE)
                //设置是否显示通知栏进度
                .setShowNotification(false)
                //设置是否提示后台下载toast
                .setShowBgdToast(false)
                //设置强制更新
                .setForcedUpgrade("2".equals(data.getUpdate_install()))
                //设置对话框按钮的点击监听
                .setButtonClickListener(this)
                //设置下载过程的监听
                .setOnDownloadListener(this);

        manager = DownloadManager.getInstance(this);
        manager.setApkName("haima.apk")
                .setApkUrl(data.getDownload_url())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowNewerToast(true)
                .setConfiguration(configuration)
                .setApkVersionCode(data.getUpdate_id())
                .setApkVersionName(data.getApp_verson())
                .setApkSize(data.getApp_size())
                .setApkDescription(data.getApp_content())
//                .setApkMD5("DC501F04BBAA458C9DC33008EFED5E7F")
                .download();
    }

    @Override
    public void onButtonClick(int id) {

    }

    @Override
    public void start() {
        if (!manager.getConfiguration().isForcedUpgrade()) {//非强制升级
            updateShowingDialog.show();
        }
    }

    @Override
    public void downloading(int max, int progress) {
        int curr = (int) (progress / (double) max * 100.0);
        updateShowingDialog.setProcess(curr);
    }

    @Override
    public void done(File apk) {
        updateShowingDialog.dismiss();
    }

    @Override
    public void cancel() {

    }

    @Override
    public void error(Exception e) {
        showToast("下载出错了");
        if (manager != null) {
            manager.getDefaultDialog().dismiss();
            manager.cancel();
        }
        updateShowingDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null) {
            manager.cancel();
        }
        // 退出时销毁定位
        mLocationClient.stop();
        //  unregisterReceiver(mInstallAppBroadcastReceiver);
    }
}
