package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.MessageConstants;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.model.MessageDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.MessageQuery;
import com.ziqi.myweb.core.service.MessageService;
import com.ziqi.myweb.dal.model.MessageDO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Description: MessageBiz
 * User: qige
 * Date: 15/4/16
 * Time: 22:11
 */
public class MessageBiz extends BaseBiz<MessageDTO, MessageDO> {

    @Resource
    private UserBiz userBiz;

    public List<MessageDTO> listMessages(int userId, int pageIndex, Context context) {
        MessageQuery query = new MessageQuery();
        query.setToUserId(userId);
        query.setPageIndex(pageIndex);
        query.setType(MessageConstants.type.USER_MSG);
        //仅type，toUserId和分页参数有效
        return result(((MessageService) baseService).listMessages(query), context);
    }

    public List<MessageDTO> listChatMessages(int myUserId, int userId, int pageIndex, Context context) {
        List<MessageDTO> messageDTOs = new ArrayList<MessageDTO>();
        MessageQuery query = new MessageQuery();
        //我发出的
        query.setFromUserId(myUserId);
        query.setToUserId(userId);
        query.setPageIndex(pageIndex);
        query.setType(MessageConstants.type.USER_MSG);
        query.addOrderField(TableConstants.Base.gmtCreate, true);
        messageDTOs.addAll(query(query, context));

        //我接收的
        query.setFromUserId(userId);
        query.setToUserId(myUserId);
        messageDTOs.addAll(query(query, context));
        //排序
        Collections.sort(messageDTOs, new Comparator<MessageDTO>() {
            @Override
            public int compare(MessageDTO o1, MessageDTO o2) {
                return o2.getGmtCreate().compareTo(o1.getGmtCreate());
            }
        });

        //将我接收的消息标为已读
        ((MessageService) baseService).updateRead(myUserId, userId);
        return messageDTOs;
    }

    public List<MessageDTO> listNotifications(int userId, int pageIndex, Context context) {
        MessageQuery query = new MessageQuery();
        query.setToUserId(userId);
        query.setPageIndex(pageIndex);
        query.setType(MessageConstants.type.SYSTEM_MSG);
        query.addOrderField(TableConstants.Message.status, false);//未读为0，已读为1
        query.addOrderField(TableConstants.Base.gmtCreate, true);
        return query(query, context);
    }

    public void sendMessage(int toUserId, int fromUserId, String content, Context context) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(content);
        messageDTO.setFromUserId(fromUserId);
        UserDTO fromUser = userBiz.queryById(fromUserId, context);
        messageDTO.setFromAccount(fromUser.getAccount());
        messageDTO.setToUserId(toUserId);
        UserDTO toUser = userBiz.queryById(toUserId, context);
        messageDTO.setToAccount(toUser.getAccount());
        messageDTO.setStatus(0);
        messageDTO.setType(MessageConstants.type.USER_MSG);
        save(messageDTO, context);
    }

    public void sendNotification(int toUserId, String content, String url, Context context) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(content);
        messageDTO.setFromUserId(0);
        messageDTO.setFromAccount("系统");
        messageDTO.setToUserId(toUserId);
        UserDTO toUser = userBiz.queryById(toUserId, context);
        messageDTO.setToAccount(toUser.getAccount());
        messageDTO.setStatus(0);
        messageDTO.setType(MessageConstants.type.SYSTEM_MSG);
        messageDTO.addFeature("url", url);
        save(messageDTO, context);
    }
}
