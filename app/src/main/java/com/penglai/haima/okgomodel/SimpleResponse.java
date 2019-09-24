package com.penglai.haima.okgomodel;

import java.io.Serializable;

/**
 * ================================================
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class SimpleResponse implements Serializable {

    private static final long serialVersionUID = -1477609349345966116L;

    public int success;
    public String message;

    public int code;
    public String msg;

    public CommonReturnData toCommonReturnData() {
        CommonReturnData commonReturnData = new CommonReturnData();
        commonReturnData.success = success;
        commonReturnData.message = message;
        return commonReturnData;
    }

    public LzyResponse toLzyResponse() {
        LzyResponse lzyResponse = new LzyResponse();
        lzyResponse.code = code;
        lzyResponse.msg = msg;
        return lzyResponse;
    }
}