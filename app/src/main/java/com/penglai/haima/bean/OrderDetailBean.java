package com.penglai.haima.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2019/11/6.
 * 文件说明：
 */
public class OrderDetailBean {
    private String amount = "";
    private String receiveName = "";
    private String receiveAddress = "";
    private String state = "";
    private String self_state = "";
    private String receiveNotes = "";
    private String receiveMobile = "";
    private String providerAddress = "";
    private String providerName = "";
    private String providerPhone = "";
    private List<ProductSelectBean> merclist = new ArrayList<>();

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReceiveNotes() {
        return receiveNotes;
    }

    public void setReceiveNotes(String receiveNotes) {
        this.receiveNotes = receiveNotes;
    }

    public String getReceiveMobile() {
        return receiveMobile;
    }

    public void setReceiveMobile(String receiveMobile) {
        this.receiveMobile = receiveMobile;
    }

    public List<ProductSelectBean> getMerclist() {
        return merclist;
    }

    public void setMerclist(List<ProductSelectBean> merclist) {
        this.merclist = merclist;
    }

    public String getSelf_state() {
        return self_state;
    }

    public void setSelf_state(String self_state) {
        this.self_state = self_state;
    }

    public String getProviderAddress() {
        return providerAddress;
    }

    public void setProviderAddress(String providerAddress) {
        this.providerAddress = providerAddress;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }
}
