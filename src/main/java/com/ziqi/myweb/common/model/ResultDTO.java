package com.ziqi.myweb.common.model;

import com.ziqi.myweb.common.constants.ErrorCode;

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

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode.getErrorCode();
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorTips() {
        return errorCode.getErrorTips();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
