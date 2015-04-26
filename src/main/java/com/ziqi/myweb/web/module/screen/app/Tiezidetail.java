package com.ziqi.myweb.web.module.screen.app;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ReplyDTO;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.BaseQuery;
import com.ziqi.myweb.web.biz.ReplyBiz;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: Index
 * User: qige
 * Date: 15/4/17
 * Time: 00:58
 */
public class Tiezidetail extends BaseModule {

	@Resource
	private ThreadBiz threadBiz;
	
	@Resource
	private UserBiz userBiz;
	
	@Resource
	private ReplyBiz replyBiz;

    public Logger logger = LoggerFactory.getLogger(Tiezidetail.class);

    public void execute(Context context) {
        try {
            defaultExecute(context);
            int threadId = Integer.parseInt(request.getParameter("threadId"));
            //List<ReplyDTO> replyDTOs = replyBiz.queryReplyByThreadId(threadId, 1, true, context);
            List<ReplyDTO> replyDTOs = replyBiz.listReplyTop(threadId, threadId, 1, true, true, context);
            List<UserDTO> userDTOs = new ArrayList<UserDTO>();
            for(ReplyDTO replyDTO : replyDTOs) {
            	userDTOs.add(userBiz.queryById(replyDTO.getAuthorId(), context));
            }
            context.put("userDTOs", userDTOs);
            context.put("replyDTOs", replyDTOs);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
