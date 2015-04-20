package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: Publish
 * User: qige
 * Date: 15/4/18
 * Time: 22:20
 */
public class Publish extends BaseModule {

    private Logger logger = LoggerFactory.getLogger(Publish.class);

    public void execute(Context context) {
        try {
            checkLogin(request.getContextPath() + "/publish.htm");
            defaultExecute(context);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
