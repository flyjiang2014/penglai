package com.penglai.haima.bean;

/**
 * Created by  on 2019/11/6.
 * 文件说明：
 */
public class OrderDetailBean {
    private String amount = "";
    private String receiveName = "";
    private String receiveAddress = "";
    private String state = "";
    private String receiveNotes = "";
    private String receiveMobile = "";
    //  private List<ProductSelectBean> merclist = new ArrayList<>();

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

//    public List<ProductSelectBean> getMerclist() {
//        return merclist;
//    }
//
//    public void setMerclist(List<ProductSelectBean> merclist) {
//        this.merclist = merclist;
//    }
}
