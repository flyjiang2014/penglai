package com.penglai.haima.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.bean.ChargeRecordBean;

import java.util.List;

/**
 * Created by ${flyjiang} on 2019/10/17.
 * 文件说明：交易记录
 */
public class ChargeRecordAdapter extends BaseQuickAdapter<ChargeRecordBean, BaseViewHolder> {

    public ChargeRecordAdapter(@Nullable List<ChargeRecordBean> data) {
        super(R.layout.item_charge_record_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChargeRecordBean item) {
        TextView tv_use_money = helper.getView(R.id.tv_use_money);
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_left_money = helper.getView(R.id.tv_left_money);
        TextView tv_date = helper.getView(R.id.tv_date);

        tv_date.setText(item.getCreate_date());
        tv_left_money.setText("余额：" + item.getBalance() + "元");
        tv_use_money.setText(item.getAmount() + "元");
        if (TextUtils.equals("0", item.getType())) {
            tv_type.setText("充值");
        } else if (TextUtils.equals("1", item.getType())) {
            tv_type.setText("消费");
        } else if (TextUtils.equals("2", item.getType())) {
            tv_type.setText("提现");
        }

    }
}
