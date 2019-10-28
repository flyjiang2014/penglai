package com.penglai.haima.bean;

/**
 * Created by jpchen on 2017/6/12.
 */

public class EventBean {

    private int mEvent;
    private Object mData;

    public static final int TRADE_PAY_SUCCESS = 101;  //下单成功

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
