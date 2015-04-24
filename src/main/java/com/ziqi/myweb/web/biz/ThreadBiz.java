package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.constants.ThreadConstants;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.query.ThreadQuery;
import com.ziqi.myweb.core.service.ImageService;
import com.ziqi.myweb.core.service.ThreadService;
import com.ziqi.myweb.dal.model.ThreadDO;
import com.ziqi.myweb.web.constants.ContextConstants;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Description: ThreadBiz
 * User: qige
 * Date: 15/4/19
 * Time: 16:33
 */
public class ThreadBiz extends BaseBiz<ThreadDTO, ThreadDO> {

    public List<ThreadDTO> listThread(int pageIndex, int pageSize,boolean imagePath, boolean content , Context context) {
        ResultDTO<List<ThreadDTO>> resultDTO = ((ThreadService) baseService).listThreads(pageIndex, pageSize, imagePath, content);
        if (!resultDTO.isSuccess()) {
            context.put(ContextConstants.ERROR_MSG, ErrorCode.ERR_WEB_0001);
            return new ArrayList<ThreadDTO>();
        }

        context.put("pageIndex", resultDTO.getPageIndex());
        context.put("totalPage", resultDTO.getTotalPage());
        context.put("pageSize", resultDTO.getPageSize());
        return resultDTO.getResult();
    }

    public List<ThreadDTO> listTopThread(int pageIndex, int pageSize, Context context) {
        ThreadQuery threadQuery = new ThreadQuery();
        threadQuery.setPageIndex(pageIndex);
        threadQuery.setPageSize(pageSize);
        threadQuery.setLevel(ThreadConstants.Level.TOP);
        threadQuery.addOrderField(TableConstants.Thread.lastReplyDate, true);
        return query(threadQuery, context);
    }

    public boolean publishThread(String title, String content, String parentPath, Integer userId, Context context) {

        String filename = UUID.randomUUID() + "_" + userId + ".html";
        String contentPath =  "/threads/" + filename;
        String savePath = parentPath + contentPath;

        ThreadDTO threadDTO = new ThreadDTO();
        threadDTO.setContent(content);
        threadDTO.setAuthorId(userId);
        threadDTO.setContentPath(contentPath);
        threadDTO.setHit(0);
        threadDTO.setLikeCount(0);
        threadDTO.setReplyCount(0);
        threadDTO.setTitle(title);
        threadDTO.setLevel(ThreadConstants.Level.NORMAL);
        threadDTO.setLastReplyDate(new Date());
        return resultBoolean(((ThreadService) baseService).publishThread(threadDTO, savePath), context);

    }

    public void updateHit(int threadId) {
        ((ThreadService) baseService).updateHit(threadId);
    }

}
