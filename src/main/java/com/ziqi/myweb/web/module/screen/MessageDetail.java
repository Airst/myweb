package com.ziqi.myweb.web.module.screen;

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
            String id = request.getParameter("id");//message id
            String toUserId = request.getParameter("toUserId");
            if(StringUtils.isNotBlank(id)) {
                if (!checkLogin("/messageDetail.htm?id=m" + id, context)) return;
                MessageDTO messageDTO = messageBiz.queryById(Integer.parseInt(id), context);//选定的消息，一定是登陆用户接收的

                List<MessageDTO> messageDTOs = messageBiz.listChatMessages(getUserId(), messageDTO.getFromUserId(), 1, context);
                context.put("messageDTOs", messageDTOs);
                //回信，角色互换
                context.put("toUserId", messageDTO.getFromUserId());
                context.put("toAccount", messageDTO.getFromAccount());
                //--
            } else if(StringUtils.isNotBlank(toUserId)) {
                if (!checkLogin("/messageDetail.htm?toUserId=" + toUserId, context)) return;

                List<MessageDTO> messageDTOs = messageBiz.listChatMessages(getUserId(), Integer.parseInt(toUserId), 1, context);
                context.put("messageDTOs", messageDTOs);
                //回信，角色互换
                context.put("toUserId", Integer.parseInt(toUserId));
                context.put("toAccount", userBiz.queryById(Integer.parseInt(toUserId), context).getAccount());
                //--
            }

        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
