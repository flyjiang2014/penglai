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
    public int status;
    public String message;
    public String errorCode;

    public CommonReturnData toCommonReturnData() {
        CommonReturnData commonReturnData = new CommonReturnData();
        commonReturnData.setStatus(status);
        commonReturnData.setMessage(message);
        commonReturnData.setErrorCode(errorCode);
        return commonReturnData;
    }
}