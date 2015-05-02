package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.biz.MessageBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: SendMessageAction
 * User: qige
 * Date: 15/4/30
 * Time: 12:03
 */
public class SendMessageAction extends BaseModule {

    @Resource
    private MessageBiz messageBiz;

    private Logger logger = LoggerFactory.getLogger(SendMessageAction.class);

    public void execute(Context context) {
        try {
            String content = request.getParameter("content");
            String toUserId = request.getParameter("toUserId");

            if(StringUtils.isBlank(toUserId)) {
                response.sendRedirect(getHostUrl() + "/message.htm");
                return;
            }

            messageBiz.sendMessage(Integer.parseInt(toUserId), getUserId(), content, context);
            response.sendRedirect(getHostUrl() + "/messageDetail.htm?toUserId=" + toUserId);
        } catch(Exception e) {
            onException(context, logger, e);
        }
    }
}
