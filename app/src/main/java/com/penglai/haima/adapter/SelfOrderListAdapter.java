package com.penglai.haima.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
public class SelfOrderListAdapter extends BaseQuickAdapter<OrderListBean, BaseViewHolder> {

    public SelfOrderListAdapter(List<OrderListBean> data) {
        super(R.layout.item_self_order_list_layout, data);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void convert(final BaseViewHolder helper, OrderListBean item) {
        TextView tv_time = helper.getView(R.id.tv_time);
        TextView tv_state = helper.getView(R.id.tv_state);
        TextView tv_count_content = helper.getView(R.id.tv_count_content);
        TextView tv_go_pay = helper.getView(R.id.tv_go_pay);
        TextView tv_code_check = helper.getView(R.id.tv_code_check);
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        tv_time.setText(item.getInsert_time());
        tv_state.setText(getStateShow(item.getSelf_state()));
        String content = String.format("共计<font  color='#FF0000'>%s</font>件，<font  color='#FF0000'>￥%s</font>", item.getTotal_number(), item.getTotal_price());
        tv_count_content.setText(Html.fromHtml(content));
        tv_code_check.setVisibility("2".equals(item.getSelf_state()) ? View.VISIBLE : View.GONE);//查看提货码
        tv_go_pay.setVisibility("1".equals(item.getSelf_state()) ? View.VISIBLE : View.GONE);//待支付
        helper.addOnClickListener(R.id.tv_go_pay);
        helper.addOnClickListener(R.id.tv_code_check);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        OrderPicItemAdapter orderPicItemAdapter = new OrderPicItemAdapter(getPics(item.getDetail()));
        recyclerView.setAdapter(orderPicItemAdapter);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            float init_x = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        init_x = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(init_x - event.getX()) < 10) {//说明是点击，而不是滑动
                            helper.getView(R.id.ll_whole).performClick();
                        }
                        break;
                }

                return false;
            }
        });
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
                return "待确认";
            case "1":
                return "待支付";
            case "2":
                return "待提货";
            case "3":
                return "已完成";
        }
        return "";
    }
}
