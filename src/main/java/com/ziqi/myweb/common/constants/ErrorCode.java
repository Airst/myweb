package com.ziqi.myweb.common.constants;

/**
 * Description: ErrorCodeConstants
 * User: qige
 * Date: 15/4/12
 * Time: 19:01
 */
public enum ErrorCode {

    //BaseDAO DataAccessException
    ERR_DAE_0001("ERR_DAE_0001", "select failed"),
    ERR_DAE_0002("ERR_DAE_0002", "selectById failed"),
    ERR_DAE_0003("ERR_DAE_0003", "count failed"),
    ERR_DAE_0004("ERR_DAE_0004", "insert failed"),
    ERR_DAE_0005("ERR_DAE_0005", "update failed"),
    ERR_DAE_0006("ERR_DAE_0006", "delete failed"),

    //ReplyDAO DataAccessException
    ERR_DAE_0010("ERR_DAE_0010", "reply update parent failed"),

    //ThreadDAO DataAccessException
    ERR_DAE_0030("ERR_DAE_0030", "update thread hit failed"),
    ERR_DAE_0031("ERR_DAE_0031", "search threads failed"),
    ERR_DAE_0032("ERR_DAE_0032", "select threads top user"),

    //ThreadDAO DataAccessException
    ERR_DAE_0050("ERR_DAE_0050", "update message for read failed"),
    ERR_DAE_0051("ERR_DAE_0051", "select and group by fromUserId failed"),

    //ActiveDAO DataAccessException
    ERR_DAE_0070("ERR_DAE_0070", "update count failed"),
    ERR_DAE_0071("ERR_DAE_0071", "select by relations failed"),

    //BaseService ArgsCheck
    ERR_AC_0001("ERR_AC_0001", "parameters can't be null"),
    ERR_AC_0002("ERR_AC_0002", "parameters illegal"),

    //BaseService Exception
    ERR_UNKNOWN("ERR_UNKNOWN", "unknown exception"),

    //Web ERR_MSG
    ERR_WEB_0001("ERR_WEB_0001", "服务器内部错误"),
    //LoginAction
    ERR_WEB_0002("ERR_WEB_0002", "账号或密码错误"),
    ;

    private String errorCode;

    private String errorTips;

    ErrorCode(String errorCode, String errorTips) {
        this.errorCode = errorCode;
        this.errorTips = errorTips;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorTips() {
        return errorTips;
    }

    public void setErrorTips(String errorTips) {
        this.errorTips = errorTips;
    }

    @Override
    public String toString() {
        return "[" + errorCode + ", " + errorTips + "]";
    }

}
