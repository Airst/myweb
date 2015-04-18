package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.constants.ContextConstants;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
            defaultExecute(context, logger, "index");
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
