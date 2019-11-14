package com.penglai.haima.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.bean.ServiceOrderDataBean;

import java.util.List;

/**
 * Created by  on 2019/11/14.
 * 文件说明：
 */
public class ServiceListOrderAdapter extends BaseQuickAdapter<ServiceOrderDataBean, BaseViewHolder> {

    public ServiceListOrderAdapter(@Nullable List<ServiceOrderDataBean> data) {
        super(R.layout.item_service_order_layout, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ServiceOrderDataBean item) {
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_state = helper.getView(R.id.tv_state);
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_content_type = helper.getView(R.id.tv_content_type);
        TextView tv_content = helper.getView(R.id.tv_content);
        tv_type.setText(item.getType());
        tv_state.setText(getStateShow(item.getState()));
        tv_time.setText(item.getInsert_time());
        if ("0".equals(item.getOrgtype())) {//个人
            tv_content_type.setText(item.getName());
            tv_content.setText(item.getTitle());

        } else if ("1".equals(item.getOrgtype())) {//机构
            tv_content_type.setText(item.getTitle());
            tv_content.setText(item.getOrg_summary());
        }
    }

    /**
     * 获取订单状态
     *
     * @return
     */
    private String getStateShow(String state) {
        switch (state) {
            case "0":
                return "待商家确认";
            case "1":
                return "待支付";
            case "2":
                return "待服务";
            case "3":
                return "已完成";
            case "4":
                return "已评价";
            case "5":
                return "已关闭";
        }
        return "";
    }

}
