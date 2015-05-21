package com.ziqi.myweb.web.module.control;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.biz.MessageBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: Head
 * User: qige
 * Date: 15/4/26
 * Time: 11:53
 */
public class Head extends BaseModule {

    @Resource
    private MessageBiz messageBiz;

    private Logger logger = LoggerFactory.getLogger(Head.class);

    public void execute(Context context) {
        try {
            flushUserId(context);
            if(getUserId() != null) {
                Integer unread = messageBiz.unreadMessages(getUserId(), context);
                unread += messageBiz.unreadNotifications(getUserId(), context);
                if(unread != null && unread != 0) {
                    context.put("unread", unread);
                }
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
