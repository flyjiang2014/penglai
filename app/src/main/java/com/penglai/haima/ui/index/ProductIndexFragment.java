package com.penglai.haima.ui.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseFragmentV4;
import com.penglai.haima.utils.ToastUtil;

/**
 * Created by ${flyjiang} on 2019/10/22.
 * 文件说明：
 */
public class ProductIndexFragment extends BaseFragmentV4 {
    private int state = -1;

    @Override
    protected View initView(LayoutInflater inflater) {
        state = getArguments().getInt("state");
        View view = inflater.inflate(R.layout.fragment_product_index, null);
        initView(view);
        initViewData();
        return view;
    }

    @Override
    protected void initViewData() {

    }

    private void initView(View view) {

    }

    @Override
    public void initData() {
        ToastUtil.showToast("jiang");
    }

    public static ProductIndexFragment getInstance(int state) {
        ProductIndexFragment fragment = new ProductIndexFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }
}
