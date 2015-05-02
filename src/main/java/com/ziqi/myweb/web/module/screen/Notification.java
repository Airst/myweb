package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.MessageDTO;
import com.ziqi.myweb.web.biz.MessageBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: Notification
 * User: qige
 * Date: 15/4/30
 * Time: 11:20
 */
public class Notification extends BaseModule {

    @Resource
    private MessageBiz messageBiz;

    private Logger logger = LoggerFactory.getLogger(Notification.class);

    public void execute(Context context) {
        try {
            if(!checkLogin("notification.htm")) return;

            String p = request.getParameter("pageIndex");
            int pageIndex = 1;
            if(StringUtils.isNotBlank(p)) {
                pageIndex = Integer.parseInt(p);
            }
            List<MessageDTO> messageDTOs = messageBiz.listNotifications(getUserId(), pageIndex, context);
            context.put("messageDTOs", messageDTOs);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }


}
