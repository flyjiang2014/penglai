package com.penglai.haima.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.penglai.haima.R;
import com.penglai.haima.base.Constants;
import com.penglai.haima.bean.OrderListBean;
import com.penglai.haima.bean.OrderProductItemBean;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by  on 2019/10/30.
 * 文件说明：
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderListBean, BaseViewHolder> {

    public OrderListAdapter(List<OrderListBean> data) {
        super(R.layout.item_order_list_layout, data);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(final BaseViewHolder helper, OrderListBean item) {
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_state = helper.getView(R.id.tv_state);
        TextView tv_count_content = helper.getView(R.id.tv_count_content);
        TextView tv_traces = helper.getView(R.id.tv_traces);
        TextView tv_go_pay = helper.getView(R.id.tv_go_pay);
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        tv_time.setText(item.getInsert_time());
        tv_state.setText(getStateShow(item.getState()));
        String content = String.format("共计<font  color='#FF0000'>%s</font>件，<font  color='#FF0000'>￥%s</font>", item.getTotal_number(), item.getTotal_price());
        tv_count_content.setText(Html.fromHtml(content));
        tv_traces.setVisibility(TextUtils.isEmpty(item.getKd_no()) ? View.GONE : View.VISIBLE);
        tv_go_pay.setVisibility("0".equals(item.getState()) ? View.VISIBLE : View.GONE);
        helper.addOnClickListener(R.id.tv_traces);
        helper.addOnClickListener(R.id.tv_go_pay);
        helper.addOnClickListener(R.id.rl_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        OrderPicItemAdapter orderPicItemAdapter = new OrderPicItemAdapter(getPics(item.getDetail()));
        recyclerView.setAdapter(orderPicItemAdapter);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    helper.getView(R.id.ll_whole).performClick();//模拟父控件的点击
                }
                return false;
            }
        });
    }

    /**
     * 获取商品内容描述
     *
     * @param data
     * @return
     */
    private String getCountContent(List<OrderProductItemBean> data) {
        StringBuffer content = new StringBuffer();
        OrderProductItemBean orderProductItemBean;
        for (int i = 0, size = data.size(); i < size; i++) {
            orderProductItemBean = data.get(i);
            String itemDetail = orderProductItemBean.getContent() + " " + orderProductItemBean.getTitle() + orderProductItemBean.getModel();
            content.append(TextUtils.isEmpty(content) ? itemDetail : ("," + itemDetail));
        }
        return content.toString();
    }

    /**
     * 获取图片展示列表
     *
     * @return
     */
    private List<String> getPics(List<OrderProductItemBean> data) {
        List<String> pics = new ArrayList<>();
        OrderProductItemBean orderProductItemBean;
        for (int i = 0, size = data.size(); i < size; i++) {
            orderProductItemBean = data.get(i);
            pics.add(Constants.URL_FOR_PIC + orderProductItemBean.getImage_name() + Constants.PIC_JPG);
        }
        return pics;
    }

    /**
     * 获取订单状态
     *
     * @return
     */
    private String getStateShow(String state) {
        switch (state) {
            case "0":
                return "待支付";
            case "1":
                return "待发货";
            case "2":
                return "待收货";
            case "3":
                return "已完成";
        }
        return "";
    }
}
