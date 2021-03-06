package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ReplyConstants;
import com.ziqi.myweb.common.model.ReplyDTO;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.web.biz.ReplyBiz;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;
import java.util.UUID;

/**
 * Description: ReplyAction
 * User: qige
 * Date: 15/4/21
 * Time: 13:59
 */
public class ReplyAction extends BaseModule {

    @Resource
    private ReplyBiz replyBiz;

    @Resource
    private UserBiz userBiz;

    @Resource
    private ThreadBiz threadBiz;

    private Logger logger = LoggerFactory.getLogger(ReplyAction.class);

    private static final String REPLY_TOP = "replyTop";
    private static final String REPLY_SUB = "replySub";

    public void execute(Context context) {
        try {
            request.setCharacterEncoding("utf-8");
            String threadId = request.getParameter("threadId");
            String parentId = request.getParameter("parentId");
            String replyType = request.getParameter("replyType");
            if(StringUtils.isBlank(threadId) || StringUtils.isBlank(parentId)) {
                response.sendRedirect(getHostUrl() + "/thread.htm?threadId=" + threadId);
                return;
            }
            if(!checkLogin("/thread.htm?threadId=" + threadId, context)) return;
            if(StringUtils.isBlank(replyType)) {
                replyType = REPLY_TOP;
            }
            if(replyType.equals(REPLY_TOP)) {
                String content = "<!DOCTYPE html><html><head>" +
                        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head>" +
                        "<style> img { max-width: 730px; } </style>" +
                        "<body>" + request.getParameter("reply") + "</body></html>";
                content = content.replace("<img", "\n<img");

                replyBiz.publishReplyTop(content, getUserId(), Integer.parseInt(threadId),
                        Integer.parseInt(parentId), getFilesRoot(), context);
                //被评论，升级
                ThreadDTO threadDTO = threadBiz.queryById(Integer.parseInt(threadId), context);
                userBiz.pointForReply(getUserId(), threadDTO.getAuthorId(), context);
            } else if(replyType.equals(REPLY_SUB)) {
                String content = request.getParameter("comment");
                replyBiz.publishReplySub(content, getUserId(), Integer.parseInt(threadId),
                        Integer.parseInt(parentId), context);
                //作者回复评论，升级
                ReplyDTO replyDTO = replyBiz.queryById(Integer.parseInt(parentId), context);
                ThreadDTO threadDTO = threadBiz.queryById(Integer.parseInt(threadId), context);
                userBiz.levelForReply(threadDTO.getAuthorId(), replyDTO.getAuthorId(), getUserId(), context);
            }
            response.sendRedirect(getHostUrl() + "/thread.htm?threadId=" + threadId);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
