package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ReplyConstants;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.model.ReplyDTO;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.query.ReplyQuery;
import com.ziqi.myweb.core.service.ReplyService;
import com.ziqi.myweb.dal.model.ReplyDO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Description: ReplyBiz
 * User: qige
 * Date: 15/4/16
 * Time: 22:11
 */
public class ReplyBiz extends BaseBiz<ReplyDTO, ReplyDO> {

    public int publishReplyTop(String content, Integer userId, Integer threadId, Integer parentId,
                               String parentPath, Context context) {
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
        return result(((ReplyService) baseService).publishReplyTop(replyDTO, savePath), context);
    }

    public boolean publishReplySub(String content, Integer userId, Integer threadId,
                                   Integer parentId, Context context) {

        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setContent(content);
        replyDTO.setAuthorId(userId);
        replyDTO.setContentPath(content);
        replyDTO.setParentId(parentId);
        replyDTO.setReplyCount(0);
        replyDTO.setThreadId(threadId);
        replyDTO.setReplyType(ReplyConstants.Type.REPLY_SUB);
        return save(replyDTO, context);
    }
    
    public List<ReplyDTO> listReplyTop(int threadId, int parentId, int pageIndex, boolean subReply, boolean isContent, Context context) {
        ReplyQuery replyQuery = new ReplyQuery();
        replyQuery.setThreadId(threadId);
        replyQuery.setParentId(parentId);
        replyQuery.setReplyType(ReplyConstants.Type.REPLY_TOP);
        replyQuery.addOrderField(TableConstants.Reply.floor, false);
        replyQuery.setPageSize(20);
        replyQuery.setPageIndex(pageIndex);
        ResultDTO<List<ReplyDTO>> resultDTO = baseService.query(replyQuery);
        if(!resultDTO.isSuccess()) {
            return new ArrayList<ReplyDTO>(0);
        }
        if(isContent) {
			((ReplyService) baseService).setReplyContent(resultDTO.getResult());
        }
        if(subReply) {
            for (ReplyDTO replyDTO : resultDTO.getResult()) {
                List<ReplyDTO> subReplyDTOs = listReplySub(replyDTO.getThreadId(), replyDTO.getId(), 1, context);
                replyDTO.setSubReplyDTOs(subReplyDTOs);
            }
        }
        context.put("pageIndex", resultDTO.getPageIndex());
        context.put("totalPage", resultDTO.getTotalPage());
        context.put("pageSize", resultDTO.getPageSize());
        return resultDTO.getResult();
    }

    public List<ReplyDTO> listReplySub(int threadId, int parentId, int pageIndex, Context context) {
        ReplyQuery replyQuery = new ReplyQuery();
        replyQuery.setThreadId(threadId);
        replyQuery.setParentId(parentId);
        replyQuery.setReplyType(ReplyConstants.Type.REPLY_SUB);
        replyQuery.addOrderField(TableConstants.Base.gmtCreate, false);
        replyQuery.setPageSize(20);
        replyQuery.setPageIndex(pageIndex);
        return result(baseService.query(replyQuery), context);
    }

}
