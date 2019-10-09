package com.penglai.haima.ui.index;

import android.os.Bundle;

import com.lzy.okgo.OkGo;
import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;
import com.penglai.haima.base.Constants;
import com.penglai.haima.callback.DialogCallback;
import com.penglai.haima.okgomodel.CommonReturnData;

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
        getIndexData();
    }

    private void getIndexData(){
        OkGo.<CommonReturnData<Object>>post(Constants.URL + "getUserInfo")
                .execute(new DialogCallback<CommonReturnData<Object>>(this,true) {
                    @Override
                    public void onSuccess(CommonReturnData<Object> objectCommonReturnData) {
                        showToast("成功");
                    }
                });
    }
}
