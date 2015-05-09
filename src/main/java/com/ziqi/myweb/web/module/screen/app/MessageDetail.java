package com.ziqi.myweb.web.module.screen.app;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.MessageDTO;
import com.ziqi.myweb.web.biz.MessageBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: MessageDetail
 * User: qige
 * Date: 15/4/30
 * Time: 11:55
 */
public class MessageDetail extends BaseModule {

    @Resource
    private MessageBiz messageBiz;

    @Resource
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(MessageDetail.class);

    public void execute(Context context) {
        try {
            String toUserId = request.getParameter("toUserId");
            String fromUserId = request.getParameter("fromUserId");
            List<MessageDTO> messageDTOs = messageBiz.listChatMessages(Integer.parseInt(toUserId), Integer.parseInt(fromUserId), 1, context);
            context.put("messageDTOs", messageDTOs);

        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
