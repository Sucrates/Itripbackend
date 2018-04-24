package com.ytzl.itrip.utils.exception;

/**
 * Created by Su_crates on 2018/4/23.
 */
public class ItripException extends Exception {
    //错误码
    private String errorCode;
    private String message;


    public ItripException(String message) {
        this.message = message;
    }

    public ItripException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
