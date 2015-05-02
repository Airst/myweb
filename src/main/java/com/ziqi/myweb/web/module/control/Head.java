package com.ziqi.myweb.web.module.control;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: Head
 * User: qige
 * Date: 15/4/26
 * Time: 11:53
 */
public class Head extends BaseModule {

    private Logger logger = LoggerFactory.getLogger(Head.class);

    public void execute(Context context) {
        try {
            flushUserId(context);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
