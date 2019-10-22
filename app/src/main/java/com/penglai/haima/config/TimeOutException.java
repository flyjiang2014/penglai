package com.penglai.haima.config;

/**
 * Created by ${flyjiang} on 2019/10/21.
 * 文件说明：
 */
public class TimeOutException extends Exception {
    public TimeOutException(String message) {
        super(message);
    }

    public TimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeOutException(Throwable cause) {
        super(cause);
    }
}
