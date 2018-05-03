package com.ytzl.itrip.utils.exception;

/**
 * Created by sam on 2018/4/23.
 */
public class ItripException extends Exception {

    //错误码
    private String errorCode;


    public ItripException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ItripException() {
    }

    public ItripException(String message) {
        super(message);
        this.errorCode = "0";
    }

    public String getErrorCode() {
        return errorCode;
    }
}
