package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ReplyConstants;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.query.ReplyQuery;
import com.ziqi.myweb.web.biz.ReplyBiz;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: Thread
 * User: qige
 * Date: 15/4/19
 * Time: 23:40
 */
public class Thread extends BaseModule {

    @Resource
    private ThreadBiz threadBiz;

    @Resource
    private ReplyBiz replyBiz;

    @Resource
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(Thread.class);

    public void execute(Context context) {
        try {
            String threadId = request.getParameter("threadId");
            String page = request.getParameter("pageIndex");
            Integer pageIndex = 1;
            if(StringUtils.isBlank(threadId)) {
                response.sendRedirect(getHostUrl() + "/beautyStreet.htm");
                return;
            }
            if(StringUtils.isNotBlank(page)) {
                pageIndex = Integer.valueOf(page);
            }
            int id = Integer.parseInt(threadId);
            threadBiz.updateHit(id);
            ThreadDTO threadDTO = threadBiz.queryById(id, context);
            if (threadDTO == null) {
                response.sendRedirect(getHostUrl() + "/beautyStreet.htm");
                return;
            }

            if(pageIndex == 1) {
                context.put("threadDTO", threadDTO);
                context.put("author", userBiz.queryById(threadDTO.getAuthorId(), context));
            }

            ReplyQuery replyQuery = new ReplyQuery();
            replyQuery.setThreadId(threadDTO.getId());
            replyQuery.setParentId(threadDTO.getId());
            replyQuery.setReplyType(ReplyConstants.Type.REPLY_TOP);
            replyQuery.addOrderField(TableConstants.Reply.floor, false);
            replyQuery.setPageSize(20);
            replyQuery.setPageIndex(pageIndex);
            context.put("replyDTOs", replyBiz.query(replyQuery, context));

            defaultExecute(context);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
