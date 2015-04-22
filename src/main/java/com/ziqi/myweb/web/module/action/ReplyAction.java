package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ReplyConstants;
import com.ziqi.myweb.common.model.ReplyDTO;
import com.ziqi.myweb.web.biz.ReplyBiz;
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

    private Logger logger = LoggerFactory.getLogger(ReplyAction.class);

    public void execute(Context context) {
        try {
            request.setCharacterEncoding("utf-8");
            String threadId = request.getParameter("threadId");
            String parentId = request.getParameter("parentId");
            if(StringUtils.isBlank(threadId) || StringUtils.isBlank(parentId)) {
                response.sendRedirect(getHostUrl() + "/thread.htm?threadId=" + threadId);
                return;
            }
            if(!checkLogin("/thread.htm?threadId=" + threadId)) return;

            String content = "<!DOCTYPE html><html><head>" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head>" +
                    "<style> img { max-width: 730px; } </style>" +
                    "<body>" + request.getParameter("reply") + "</body></html>";
            content = content.replace("<img", "\n<img");

            replyBiz.publishReplyTop(content, getUserId(), Integer.parseInt(threadId),
                    Integer.parseInt(parentId), getFilesRoot(), context);
            response.sendRedirect(getHostUrl() + "/thread.htm?threadId=" + threadId);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
