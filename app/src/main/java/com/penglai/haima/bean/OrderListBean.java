package com.penglai.haima.bean;

import android.text.TextUtils;

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
    private String self_state = "";
    //    private String kd_no = "";
//    private String kd_company = "";
    private String receive_mobile = "";
    private List<TraceItemBean> kd_info = new ArrayList<>();

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
        return TextUtils.isEmpty(state) ? "" : state;
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

//    public String getKd_no() {
//        return kd_no;
//    }
//
//    public void setKd_no(String kd_no) {
//        this.kd_no = kd_no;
//    }
//
//    public String getKd_company() {
//        return kd_company;
//    }
//
//    public void setKd_company(String kd_company) {
//        this.kd_company = kd_company;
//    }

    public String getSelf_state() {
        return TextUtils.isEmpty(self_state) ? "" : self_state;
    }

    public String getReceive_mobile() {
        return receive_mobile;
    }

    public void setReceive_mobile(String receive_mobile) {
        this.receive_mobile = receive_mobile;
    }

    public List<TraceItemBean> getKd_info() {
        return kd_info == null ? new ArrayList<TraceItemBean>() : kd_info;
    }

    public void setKd_info(List<TraceItemBean> kd_info) {
        this.kd_info = kd_info;
    }
}
