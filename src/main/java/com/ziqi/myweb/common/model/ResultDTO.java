package com.ziqi.myweb.common.model;

import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.exception.MyException;

/**
 * Description: Result
 * User: qige
 * Date: 15/4/13
 * Time: 11:09
 */
public class ResultDTO<T> extends Pagination {

    private boolean isSuccess = false;

    private T result;

    private ErrorCode errorCode;

    private String errorMessage;

    public boolean isSuccess() {
        return isSuccess;
    }

    public ResultDTO<T> setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
        return this;
    }

    public T getResult() {
        return result;
    }

    public ResultDTO<T> setResult(T result) {
        this.result = result;
        return this;
    }

    public String getErrorCode() {
        return errorCode.getErrorCode();
    }

    public ResultDTO<T> setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorTips() {
        return errorCode.getErrorTips();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ResultDTO<T> setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public ResultDTO<T> trySuccess() throws Exception{
        if(!isSuccess) throw new MyException(errorCode, errorMessage);
        return this;
    }
}
