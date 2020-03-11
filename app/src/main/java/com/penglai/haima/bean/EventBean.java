package com.penglai.haima.bean;

/**
 * Created by jpchen on 2017/6/12.
 */

public class EventBean {

    private int mEvent;
    private Object mData;

    public static final int TRADE_PAY_SUCCESS = 101;  //下单成功
    public static final int RECHARGE_PAY_SUCCESS = 102;  //充值成功
    public static final int ORDER_REPAY_SUCCESS = 103;  //服务支付成功
    public static final int SERVICE_COMMENT_SUCCESS = 104;  //服务评价成功
    public static final int TRADE_PAY_SUCCESS_FOR_SHOP = 105;  //自提商品下单成功
    public static final int SEARCH_ACTION = 106;  //首页搜索

    public EventBean(int event) {
        this.mEvent = event;
    }

    public EventBean(int event, Object data) {
        this(event);
        this.mData = data;
    }

    public int getEvent() {
        return this.mEvent;
    }

    public void setEvent(int event) {
        this.mEvent = event;
    }

    public Object getData() {
        return mData;
    }

    public void setData(Object data) {
        this.mData = data;
    }

}
