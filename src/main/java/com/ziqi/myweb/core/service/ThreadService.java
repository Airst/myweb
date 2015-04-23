package com.ziqi.myweb.core.service;

import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.constants.ImageConstants;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.constants.ThreadConstants;
import com.ziqi.myweb.common.exception.MyException;
import com.ziqi.myweb.common.model.ImageDTO;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.query.ImageQuery;
import com.ziqi.myweb.common.query.ThreadQuery;
import com.ziqi.myweb.dal.model.ThreadDO;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.dal.query.QueryMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * Description: ThreadService
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class ThreadService extends BaseService<ThreadDTO, ThreadDO> {

    @Resource
    private ImageService imageService;

    @Resource
    private TransactionTemplate transactionTemplate;

    public ThreadService() {
        setLogger(LoggerFactory.getLogger(ThreadService.class));
    }

    public ResultDTO<Integer> publishThread(final ThreadDTO threadDTO, final String savePath) {

        Object result = transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                try {
                    ResultDTO<Integer> resultDTO = saveBasic(threadDTO).trySuccess();
                    if(StringUtils.isNotBlank(threadDTO.getContent()) && StringUtils.isNotBlank(savePath)) {
                        saveContent(threadDTO.getContent(), savePath);
                        List<String> imagePaths = ImageService.findImagePaths(threadDTO.getContent());
                        for (String imagePath : imagePaths) {
                            ImageDTO imageDTO = new ImageDTO();
                            imageDTO.setFilepath(imagePath);
                            imageDTO.setParentId(resultDTO.getResult());
                            imageDTO.setUserId(threadDTO.getAuthorId());
                            imageDTO.setType(ImageConstants.Type.PUBLISH_THREAD);
                            imageService.saveBasic(imageDTO).trySuccess(); // auto throws
                        }
                    }

                    return resultDTO; // auto throws
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    return e;
                }
            }
        });

        return afterTransaction(result);
    }

    public ResultDTO<List<ThreadDTO>> listThreads(int pageIndex, int pageSize, boolean imagePath, boolean content) {

        ThreadQuery threadQuery = new ThreadQuery();
        threadQuery.setPageIndex(pageIndex);
        threadQuery.setPageSize(pageSize);
        threadQuery.setLevel(ThreadConstants.Level.NORMAL);
        threadQuery.addOrderField(TableConstants.Thread.lastReplyDate, true);
        ResultDTO<List<ThreadDTO>> threadResult = query(threadQuery);

        if (!threadResult.isSuccess()) {
            return threadResult;
        }
        try {
            if(imagePath) {
                setImagePaths(threadResult.getResult());
            }

            if(content) {
                setThreadContent(threadResult.getResult());
            }
        } catch (Exception e) {
            logger.error("failed@listThreads, ", e);
        }
        return threadResult;
    }

    private void setThreadContent(List<ThreadDTO> threadDTOs) throws Exception {
        for(ThreadDTO threadDTO : threadDTOs) {
            String classPath = this.getClass().getResource("").getPath();
            File file = new File(classPath.substring(0, classPath.indexOf("/webapps") + 8) + threadDTO.getContentPath());
            InputStream inputStream = new FileInputStream(file);
            int count = (int) file.length();
            byte[] data = new byte[count];
            int readCount = 0;
            while (readCount < count) {
                readCount += inputStream.read(data, readCount, count - readCount);

            }
            inputStream.close();
            threadDTO.setContent(new String(data, "utf-8"));
        }
    }

    private void setImagePaths(List<ThreadDTO> threadDTOs) {
        for(ThreadDTO threadDTO : threadDTOs) {
            ImageQuery imageQuery = new ImageQuery();
            imageQuery.setParentId(threadDTO.getId());
            imageQuery.setType(ImageConstants.Type.PUBLISH_THREAD);
            imageQuery.setPageSize(3);
            ResultDTO<List<ImageDTO>> imageResult = imageService.query(imageQuery);
            if(imageResult.isSuccess()) {
                for(ImageDTO imageDTO : imageResult.getResult()) {
                    threadDTO.addImagePath(imageDTO.getFilepath());
                }
            }
        }
    }

    @Override
    public ThreadDTO DOToDTO(ThreadDO threadDO) {
        ThreadDTO threadDTO = new ThreadDTO();
        threadDTO.setId(threadDO.getId());
        threadDTO.setFeature(threadDO.getFeature());
        threadDTO.setOptions(threadDO.getOptions());
        threadDTO.setIsDeleted(threadDO.getIsDeleted());
        threadDTO.setGmtCreate(threadDO.getGmtCreate());
        threadDTO.setGmtModified(threadDO.getGmtModified());
        threadDTO.setVersion(threadDO.getVersion());
        threadDTO.setTitle(threadDO.getTitle());
        threadDTO.setAuthorId(threadDO.getAuthorId());
        threadDTO.setHit(threadDO.getHit());
        threadDTO.setReplyCount(threadDO.getReplyCount());
        threadDTO.setLikeCount(threadDO.getLikeCount());
        threadDTO.setLastReplyDate(threadDO.getLastReplyDate());
        threadDTO.setLevel(threadDO.getLevel());
        threadDTO.setTags(threadDO.getTags());
        threadDTO.setContentPath(threadDO.getContentPath());
        return threadDTO;
    }
    @Override
    public ThreadDO DTOToDO(ThreadDTO threadDTO) {
        ThreadDO threadDO = new ThreadDO();
        threadDO.setId(threadDTO.getId());
        threadDO.setFeature(threadDTO.getFeature());
        threadDO.setOptions(threadDTO.getOptions());
        threadDO.setIsDeleted(threadDTO.getIsDeleted());
        threadDO.setGmtCreate(threadDTO.getGmtCreate());
        threadDO.setGmtModified(threadDTO.getGmtModified());
        threadDO.setVersion(threadDTO.getVersion());
        threadDO.setTitle(threadDTO.getTitle());
        threadDO.setAuthorId(threadDTO.getAuthorId());
        threadDO.setHit(threadDTO.getHit());
        threadDO.setReplyCount(threadDTO.getReplyCount());
        threadDO.setLikeCount(threadDTO.getLikeCount());
        threadDO.setLastReplyDate(threadDTO.getLastReplyDate());
        threadDO.setLevel(threadDTO.getLevel());
        threadDO.setTags(threadDTO.getTags());
        threadDO.setContentPath(threadDTO.getContentPath());
        return threadDO;
    }

}
