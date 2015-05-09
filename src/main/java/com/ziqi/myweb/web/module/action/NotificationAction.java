package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.MessageConstants;
import com.ziqi.myweb.common.model.MessageDTO;
import com.ziqi.myweb.web.biz.MessageBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: NotificationAction
 * User: qige
 * Date: 15/5/9
 * Time: 11:47
 */
public class NotificationAction extends BaseModule {

    @Resource
    private MessageBiz messageBiz;

    private Logger logger = LoggerFactory.getLogger(NotificationAction.class);

    public void execute(Context context) {
        try {
            if(!checkLogin("notification.htm", context)) return;

            String id = request.getParameter("id");
            if(StringUtils.isBlank(id)) {
                response.sendRedirect(getHostUrl() + "/notification.htm");
                return;
            }
            MessageDTO messageDTO = messageBiz.queryById(Integer.parseInt(id), context);
            messageDTO.setStatus(MessageConstants.status.READ);
            messageBiz.update(messageDTO, context);

            String url = request.getParameter("url");
            if(StringUtils.isBlank(url)) {
                url = "";
            }
            response.sendRedirect(getHostUrl() + url);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
