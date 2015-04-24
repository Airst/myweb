package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.constants.ReplyConstants;
import com.ziqi.myweb.common.model.ReplyDTO;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.core.service.ReplyService;
import com.ziqi.myweb.dal.model.ReplyDO;
import com.ziqi.myweb.web.constants.ContextConstants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Description: ReplyBiz
 * User: qige
 * Date: 15/4/16
 * Time: 22:11
 */
public class ReplyBiz extends BaseBiz<ReplyDTO, ReplyDO> {

    public boolean publishReplyTop(String content, Integer userId, Integer threadId, Integer parentId, String parentPath, Context context) {
        //build path
        String filename = UUID.randomUUID() + "_" + userId + ".html";
        String contentPath = "/threads/" + filename;
        String savePath = parentPath + contentPath;
        //-

        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setContent(content);
        replyDTO.setAuthorId(userId);
        replyDTO.setContentPath(contentPath);
        replyDTO.setParentId(parentId);
        replyDTO.setReplyCount(0);
        replyDTO.setThreadId(threadId);
        replyDTO.setReplyType(ReplyConstants.Type.REPLY_TOP);
        return resultBoolean(((ReplyService) baseService).publishReply(replyDTO, savePath), context);
    }
    
    public List<ReplyDTO> queryReplyByThreadId(int threadId, int pageIndex, Context context) {
    	ResultDTO<List<ReplyDTO>> resultDTO =  ((ReplyService) baseService).selectReplyByThreadId(threadId ,pageIndex);
    	if (!resultDTO.isSuccess()) {
            context.put(ContextConstants.ERROR_MSG, ErrorCode.ERR_WEB_0001);
            return new ArrayList<ReplyDTO>();
        }

        context.put("pageIndex", resultDTO.getPageIndex());
        context.put("totalPage", resultDTO.getTotalPage());
        context.put("pageSize", resultDTO.getPageSize());
        return resultDTO.getResult();
    }

}
