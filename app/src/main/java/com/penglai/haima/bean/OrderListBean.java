package com.penglai.haima.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2019/10/30.
 * 文件说明：
 */
public class OrderListBean {
    private String total_number = "";
    private String total_price = "";
    private String trade_no = "";
    private String insert_time = "";
    private String state = "";
    private List<OrderProductItemBean> detail = new ArrayList<>();

    public String getTotal_number() {
        return total_number;
    }

    public void setTotal_number(String total_number) {
        this.total_number = total_number;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<OrderProductItemBean> getDetail() {
        return detail;
    }

    public void setDetail(List<OrderProductItemBean> detail) {
        this.detail = detail;
    }
}
