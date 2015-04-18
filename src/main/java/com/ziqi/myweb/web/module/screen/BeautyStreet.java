package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: BeautyStreet
 * User: qige
 * Date: 15/4/19
 * Time: 00:12
 */
public class BeautyStreet extends BaseModule {

    private Logger logger = LoggerFactory.getLogger(BeautyStreet.class);

    public void execute(Context context) {
        try {
            defaultExecute(context, logger, "beautyStreet");
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
