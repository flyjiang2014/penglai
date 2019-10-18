package com.penglai.haima.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.ui.index.PersonIndexActivity;
import com.penglai.haima.utils.SharepreferenceUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    public int setBaseContentView() {
        setIsShowTitle(false);
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        initScheduler();
    }

    /**
     * 初始化
     */
    private void initScheduler() {
        mDisposable = Observable.timer(1, TimeUnit.SECONDS) //延时1秒执行
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Intent intent = new Intent();
                        if (SharepreferenceUtil.getBoolean(Constants.IS_GOLIN)) {
                            intent.setClass(SplashActivity.this, PersonIndexActivity.class);
                        } else {
                            intent.setClass(SplashActivity.this, LoginActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
