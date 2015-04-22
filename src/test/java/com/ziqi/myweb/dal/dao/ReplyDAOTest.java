package com.ziqi.myweb.dal.dao;

import com.ziqi.myweb.BaseCase;
import com.ziqi.myweb.common.constants.ReplyConstants;
import com.ziqi.myweb.common.model.ReplyDTO;
import com.ziqi.myweb.dal.model.ReplyDO;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Description: ReplyDAOTest
 * User: qige
 * Date: 15/4/12
 * Time: 18:54
 */
public class ReplyDAOTest extends BaseCase {

    @Resource
    private ReplyDAO replyDAO;

    @Test
    public void testSelect() throws Exception {

    }

    @Test
    public void testSelectById() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {
        ReplyDO replyDO = new ReplyDO();
        replyDO.setAuthorId(0);
        replyDO.setContentPath("aslfjals");
        replyDO.setParentId(1);
        replyDO.setReplyCount(0);
        replyDO.setThreadId(1);
        replyDO.setReplyType(ReplyConstants.Type.REPLY_TOP);
        replyDAO.insert(replyDO);
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }
}