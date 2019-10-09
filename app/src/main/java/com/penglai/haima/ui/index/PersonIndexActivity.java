package com.penglai.haima.ui.index;

import android.os.Bundle;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;

public class PersonIndexActivity  extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int setBaseContentView() {
        setIsShowTitle(false);
        return R.layout.activity_person_index;
    }

    @Override
    public void init() {

    }
}
