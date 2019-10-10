package com.penglai.haima.bean;

/**
 * Created by ${flyjiang} on 2019/10/10.
 * 文件说明："data":{"id":36,"createdBy":-1,"creationDate":1566306168000,"lastUpdatedBy":-1,
 * "lastUpdateDate":1569658197000,"status":1,"openId":null,"realName":"fly","mobile":"13914038661","validationCode":"979562","cusManCode":"fly",
 * "province":null,"city":null,"district":null,"address":"无锡","score":0,"balance":0.00,"cashback":0.00,"version":0
 */
public class UserInfoBean {
    private String id;
    private String creationDate;
    private String realName;
    private String mobile;
    private String validationCode;
    private String cusManCode;
    private String address;
    private String score;
    private String balance;

    public String getId() {
        return id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getRealName() {
        return realName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public String getCusManCode() {
        return cusManCode;
    }

    public String getAddress() {
        return address;
    }

    public String getScore() {
        return score;
    }

    public String getBalance() {
        return balance;
    }
}
