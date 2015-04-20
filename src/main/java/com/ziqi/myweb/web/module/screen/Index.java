package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: Index
 * User: qige
 * Date: 15/4/17
 * Time: 00:58
 */
public class Index extends BaseModule {

    public Logger logger = LoggerFactory.getLogger(Index.class);

    public void execute(Context context) {
        try {
            defaultExecute(context);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
