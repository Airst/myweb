package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.constants.ContextConstants;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Description: LogoutAction
 * User: qige
 * Date: 15/4/17
 * Time: 01:19
 */
public class Logout extends BaseModule {

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Resource
    private HttpSession session;

    private static Logger logger = LoggerFactory.getLogger(Logout.class);

    public void execute(Context context) {
        try {
            session.removeAttribute(ContextConstants.USER_ID);
            session.removeAttribute(ContextConstants.ACCOUNT);
            flushUserId(context);
            response.sendRedirect(request.getContextPath() + "/index.htm");
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
