package com.penglai.haima.okgomodel;

import java.io.Serializable;

/**
 * Created by flyjiang on 2016/11/30.
 */
public class CommonReturnData<T> implements Serializable {
    public int success;
    public String message;
    public T data;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
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
