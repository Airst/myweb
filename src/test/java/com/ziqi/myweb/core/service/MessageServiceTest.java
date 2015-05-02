package com.ziqi.myweb.core.service;

import com.ziqi.myweb.BaseCase;
import com.ziqi.myweb.common.constants.MessageConstants;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.model.MessageDTO;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.query.MessageQuery;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Description: MessageServiceTest
 * User: qige
 * Date: 15/4/30
 * Time: 18:53
 */
public class MessageServiceTest extends BaseCase {

    @Resource
    private MessageService messageService;

    @Test
    public void testUpdateRead() throws Exception {
        ResultDTO<Integer> resultDTO = messageService.updateRead(1, 1);
    }

    @Test
    public void testQuery() throws Exception {
        MessageQuery query = new MessageQuery();
        query.setToUserId(1);
        query.setPageIndex(1);
        query.setType(MessageConstants.type.USER_MSG);
        query.addOrderField(TableConstants.Message.status, false); //未读在前
        query.addOrderField(TableConstants.Base.gmtCreate, true);
        query.setGroupField(TableConstants.Message.fromUserId);
        ResultDTO<List<MessageDTO>> resultDTO = messageService.query(query);
    }
}