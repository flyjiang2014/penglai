package com.penglai.haima.ui.order;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.penglai.haima.R;
import com.penglai.haima.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductOrderSubmitActivity extends BaseActivity {

    @BindView(R.id.rv_select_product)
    RecyclerView rvSelectProduct;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.tv_price_note)
    TextView tvPriceNote;
    @BindView(R.id.tv_final_price)
    TextView tvFinalPrice;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int setBaseContentView() {
        return R.layout.activity_product_order_submit;
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                break;
        }
    }
}
