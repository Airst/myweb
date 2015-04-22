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
                return;
            }

            String content = "<!DOCTYPE html><html><head>" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head>" +
                    "<style> img { max-width: 730px; } </style>" +
                    "<body>" + request.getParameter("reply") + "</body></html>";
            content = content.replace("<img", "\n<img");

            //build path
            String parentPath = getFilesRoot();
            File threadsDir = new File(parentPath + "/threads");
            if(!threadsDir.exists() && !threadsDir.mkdirs()) {
                return;
            }
            String filename = UUID.randomUUID() + "_" + getUserId() + ".html";
            String savePath = threadsDir.getAbsolutePath() + "/" + filename;
            String contentPath = savePath.replace(parentPath, "");
            //-

            ReplyDTO replyDTO = new ReplyDTO();
            replyDTO.setContent(content);
            replyDTO.setAuthorId(getUserId());
            replyDTO.setContentPath(contentPath);
            replyDTO.setParentId(Integer.parseInt(parentId));
            replyDTO.setReplyCount(0);
            replyDTO.setThreadId(Integer.parseInt(threadId));
            replyDTO.setReplyType(ReplyConstants.Type.REPLY_TOP);
            replyBiz.publishReply(replyDTO, savePath, context);
            response.sendRedirect(getHostUrl() + "/thread.htm?threadId=" + threadId);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
