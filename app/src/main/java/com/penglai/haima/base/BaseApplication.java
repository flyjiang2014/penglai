package com.penglai.haima.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.penglai.haima.BuildConfig;
import com.penglai.haima.R;
import com.penglai.haima.utils.DynamicTimeFormat;
import com.penglai.haima.utils.FileUtil;
import com.penglai.haima.utils.SharepreferenceUtil;
import com.penglai.haima.utils.ToastUtil;
import com.penglai.haima.widget.loading.LoadingLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import in.srain.cube.util.CLog;
import okhttp3.OkHttpClient;

/**
 * 作者：flyjiang
 * 时间: 2015/5/26 16:30
 * ......................我佛慈悲......................
 *                       _oo0oo_                     *
 *                      o8888888o                    *
 *                      88" . "88                    *
 *                      (| -_- |)                    *
 *                      0\  =  /0                    *
 *                    ___/`---'\___                  *
 *                  .' \\|     |// '.                *
 *                 / \\|||  :  |||// \               *
 *                / _||||| -卍-|||||- \              *
 *               |   | \\\  -  /// |   |             *
 *               | \_|  ''\---/''  |_/ |             *
 *               \  .-\__  '-'  ___/-. /             *
 *             ___'. .'  /--.--\  `. .'___           *
 *          ."" '<  `.___\_<|>_/___.' >' "".         *
 *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       *
 *         \  \ `_.   \_ __\ /__ _/   .-` /  /       *
 *     =====`-.____`.___ \_____/___.-`___.-'=====    *
 *                       `=---='                     *
 *                                                   *
 *..................佛祖开光 ,永无BUG.................*
 */

public class BaseApplication extends Application {
    private static Context mApplicationContext;

    static { //设置刷新控件刷新head and foot
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);//启用矢量图兼容
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "已加载全部数据";
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.bg_app, R.color.text_grey666);//全局设置主题颜色
                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setEnableLoadMoreWhenContentNotFull(false);
                return new ClassicsFooter(context);
            }
        });
    }
    @Override
    public void onCreate() {
        mApplicationContext = this;
        super.onCreate();
        //基础信息初始化
        initBase();
    }

    public static Context getmApplicationContext() {
        return mApplicationContext;
    }

    /**
     * 各种初始化的信息
     */
    private void initBase() {
        //初始化打印日志
        if (BuildConfig.DEBUG) {
            // 开发模式
            CLog.setLogLevel(CLog.LEVEL_VERBOSE);
        } else {
            CLog.setLogLevel(CLog.LEVEL_ERROR);
        }
        //初始化OkGo
        initOkGo();
        //初始化LoadingLayout配置
        initLoadingLayout();
        //初始化文件夹
        FileUtil.init(this);
        //初始化闪退日志
    //    CrashUtil.getInstance().init(this);
        //初始化SharedPreferences
        SharepreferenceUtil.init(this);
        //初始化Toast
        ToastUtil.init(this);
//        //初始化工具类
//        Utils.init(this);
}
    /**
     * OkGo的初始化
     */
    private void initOkGo() {
        //---------这里给出的是示例代码,告诉你可以这么传,实际使用的时候,根据需要传,不需要就不传-------------//
        HttpHeaders headers = new HttpHeaders();
        HttpParams params = new HttpParams();
        //----------------------------------------------------------------------------------------//

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
        //builder.addInterceptor(new ChuckInterceptor(this));

        //超时时间设置，默认60秒
        builder.readTimeout(45000, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(45000, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(45000, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
     //   HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
    //    builder.hostnameVerifier(new SafeHostnameVerifier());

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/


        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //必须设置OkHttpClient
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(1)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数
    }

    /**
     * LoadingLayout默认配置
     */
    private void initLoadingLayout(){
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.drawable.define_error)
                .setEmptyImage(R.drawable.define_empty)
                .setNoNetworkImage(R.drawable.define_nonetwork)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150,40);
    }
}
