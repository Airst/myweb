package com.ziqi.myweb.web.module.action;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.UserQuery;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.constants.ContextConstants;
import com.ziqi.myweb.web.module.BaseModule;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: LoginAction
 * User: qige
 * Date: 15/4/15
 * Time: 15:26
 */
public class AppUpdateHitAction extends BaseModule {

    @Resource
    private ThreadBiz threadBiz;               

    private static Logger logger = LoggerFactory.getLogger(AppUpdateHitAction.class);

    public void execute(Context context) {
        try {
        	String threadId = request.getParameter("threadId");
        	threadBiz.updateHit(Integer.parseInt(threadId));
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
