package com.penglai.haima.ui;

import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.ui.index.PersonIndexFragment;
import com.penglai.haima.ui.index.ProductIndexFragment;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

@Deprecated
public class MainActivity extends BaseActivity {
    private ProductIndexFragment productIndexFragment;
    private PersonIndexFragment personIndexFragment;
    private BaseFragmentV4 mBaseFragment;
    private FragmentTransaction mTransaction;
    private PageNavigationView tab;
    @Override
    public int setBaseContentView() {
        setIsShowTitle(false);
        return R.layout.activity_main;
    }



    @Override
    public void init() {
        tab = findViewById(R.id.tab);
        //注意这里调用了custom()方法
        NavigationController navigationController = tab.custom()
                .addItem(newItem(R.drawable.index_unselect, R.drawable.index_select, "商品"))
                .addItem(newItem(R.drawable.me_unselect, R.drawable.me_select, "我的"))
                .build();
        productIndexFragment = ProductIndexFragment.getInstance(0);
        personIndexFragment = PersonIndexFragment.getInstance(1);
        mBaseFragment = productIndexFragment;
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.fragment_container, mBaseFragment).commit();
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                //选中时触发
                switch (index) {
                    case 0:
                        switchContent(mBaseFragment, productIndexFragment);
                        break;
                    case 1:
                        switchContent(mBaseFragment, personIndexFragment);
                        break;
                }
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
        mBaseFragment.initData();
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text) {
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable, checkedDrawable, text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(0xFF1296DB);
        return normalItemView;
    }

    /**
     * 切换Fragment
     *
     * @param from 当前显示的Fragment
     * @param to   需要显示的Fragment
     */
    public void switchContent(BaseFragmentV4 from, BaseFragmentV4 to) {
        if (mBaseFragment != to) {
            mBaseFragment = to;
            mTransaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                mTransaction.hide(from).add(R.id.fragment_container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                mTransaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
}
