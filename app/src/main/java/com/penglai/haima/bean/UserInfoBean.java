package com.penglai.haima.bean;

/**
 * Created by ${flyjiang} on 2019/10/10.
 * 文件说明："data":{"id":36,"createdBy":-1,"creationDate":1566306168000,"lastUpdatedBy":-1,
 * "lastUpdateDate":1569658197000,"status":1,"openId":null,"realName":"fly","mobile":"13914038661","validationCode":"979562","cusManCode":"fly",
 * "province":null,"city":null,"district":null,"address":"无锡","score":0,"balance":0.00,"cashback":0.00,"version":0
 */
public class UserInfoBean {

    private String name;
    private String mobile;
    private String managerCode;
    private String address;
    private String balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
