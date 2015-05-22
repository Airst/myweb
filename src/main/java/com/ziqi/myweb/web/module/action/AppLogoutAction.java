package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: LogoutAction
 * User: qige
 * Date: 15/4/19
 * Time: 15:50
 */
public class AppLogoutAction extends BaseModule {

    private static Logger logger = LoggerFactory.getLogger(AppLogoutAction.class);

    public void execute(Context context) {
        try {
            String userId = request.getParameter("userId");
            AppLoginAction.registrationMap.remove(Integer.parseInt(userId));
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
