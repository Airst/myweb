package com.ziqi.myweb.common.exception;

import com.ziqi.myweb.common.constants.ErrorCode;

/**
 * Description: MyException
 * User: qige
 * Date: 15/4/12
 * Time: 19:00
 */
public class MyException extends Exception {

    private ErrorCode errorCode;

    private String errorMessage;

    public MyException(ErrorCode errorCode, String errorMessage, Throwable e) {
        super(e);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public MyException(ErrorCode errorCode, Throwable e) {
        super(e);
        this.errorCode = errorCode;
    }

    public MyException(ErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public MyException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "[" + errorCode + "," + errorMessage + "]";
    }
}
