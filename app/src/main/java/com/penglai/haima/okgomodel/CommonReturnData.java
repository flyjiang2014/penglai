package com.penglai.haima.okgomodel;
import java.io.Serializable;
/**
 * Created by flyjiang on 2016/11/30.
 */
public class CommonReturnData<T> implements Serializable {
    private int status;
    private String message;
    private String errorCode;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
