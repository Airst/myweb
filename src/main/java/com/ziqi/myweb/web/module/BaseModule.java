package com.ziqi.myweb.web.module;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.web.constants.ContextConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Description: BaseAction
 * User: qige
 * Date: 15/4/16
 * Time: 23:01
 */
public class BaseModule {

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;

    @Resource
    protected HttpSession session;

    protected void defaultExecute(Context context, Logger logger, String name) {
        flushUserId(context);
        context.put("active", name);
    }

    public void onException(Context context, Logger logger, Exception e) {
        context.put(ContextConstants.ERROR_MSG, ErrorCode.ERR_WEB_0001);
        logger.error("failed@execute", e);
    }

    public boolean checkLogin(String url) throws Exception {
        Integer userId = (Integer) session.getAttribute(ContextConstants.USER_ID);

        if(userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.htm?url=" + url);
            return false;
        }
        return true;
    }

    public void flushUserId(Context context) {
        Integer userId = (Integer) session.getAttribute(ContextConstants.USER_ID);
        String account = (String) session.getAttribute(ContextConstants.ACCOUNT);

        if(userId != null) {
            context.put(ContextConstants.USER_ID, userId);
        }
        if(StringUtils.isNotBlank(account)) {
            context.put(ContextConstants.ACCOUNT, account);
        }
    }

    public boolean hasError(Context context) {
        return context.containsKey(ContextConstants.ERROR_MSG);
    }
}
