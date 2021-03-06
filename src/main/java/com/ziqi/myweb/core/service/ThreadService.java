package com.ziqi.myweb.core.service;

import com.ziqi.myweb.common.constants.ImageConstants;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.constants.ThreadConstants;
import com.ziqi.myweb.common.exception.MyException;
import com.ziqi.myweb.common.model.ImageDTO;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.ImageQuery;
import com.ziqi.myweb.common.query.ThreadQuery;
import com.ziqi.myweb.dal.dao.ThreadDAO;
import com.ziqi.myweb.dal.model.ThreadDO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
    private UserService userService;

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
                    if (StringUtils.isNotBlank(threadDTO.getContent()) && StringUtils.isNotBlank(savePath)) {
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

    public ResultDTO<Integer> updateHit(int threadId) {
        ResultDTO<Integer> resultDTO = new ResultDTO<Integer>();
        try {
            Integer update = ((ThreadDAO) baseDAO).updateHit(threadId);
            resultDTO.setIsSuccess(true);
            resultDTO.setResult(update);
        } catch (MyException e) {
            onMyException(e, "updateHit", resultDTO, threadId);
        } catch (Exception e) {
            onException(e, "updateHit", resultDTO, threadId);
        }
        return resultDTO;
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
            if (imagePath) {
                setImagePaths(threadResult.getResult());
            }

            if (content) {
                setThreadContent(threadResult.getResult());
            }
        } catch (Exception e) {
            logger.error("failed@listThreads, ", e);
        }
        return threadResult;
    }
    
    public ResultDTO<List<ThreadDTO>> listThreadsByUserId(int userId, int pageIndex, int pageSize, boolean imagePath, boolean content) {

        ThreadQuery threadQuery = new ThreadQuery();
        threadQuery.setAuthorId(userId);
        threadQuery.setPageIndex(pageIndex);
        threadQuery.setPageSize(pageSize);
        threadQuery.setLevel(ThreadConstants.Level.NORMAL);
        threadQuery.addOrderField(TableConstants.Thread.lastReplyDate, true);
        ResultDTO<List<ThreadDTO>> threadResult = query(threadQuery);

        if (!threadResult.isSuccess()) {
            return threadResult;
        }
        try {
            if (imagePath) {
                setImagePaths(threadResult.getResult());
            }

            if (content) {
                setThreadContent(threadResult.getResult());
            }
        } catch (Exception e) {
            logger.error("failed@listThreads, ", e);
        }
        return threadResult;
    }

    public ResultDTO<List<ThreadDTO>> searchThreads(int pageIndex, int pageSize, String key) {
        ResultDTO<List<ThreadDTO>> threadResult = new ResultDTO<List<ThreadDTO>>();
        try {
            ThreadQuery threadQuery = new ThreadQuery();
            threadQuery.setPageIndex(pageIndex);
            threadQuery.setPageSize(pageSize);
            threadQuery.setTitle(key);
            threadQuery.setLevel(ThreadConstants.Level.NORMAL);
            threadQuery.addOrderField(TableConstants.Thread.lastReplyDate, true);

            List<ThreadDO> threadDOs = ((ThreadDAO) baseDAO).search(QueryToMap(threadQuery));

            threadResult.setResult(DOsToDTOs(threadDOs));
            setImagePaths(threadResult.getResult());
            threadResult.setIsSuccess(true);
        } catch (MyException e) {
            onMyException(e, "searchThreads", threadResult, pageIndex, pageSize, key);
        } catch (Exception e) {
            onException(e, "searchThreads", threadResult, pageIndex, pageSize, key);
        }
        return threadResult;
    }

    public ResultDTO<List<Integer>> listTopUser() {
        ResultDTO<List<Integer>> resultDTO = new ResultDTO<List<Integer>>();
        try {
            List<Integer> list = ((ThreadDAO) baseDAO).selectUser();
            resultDTO.setResult(list);
            resultDTO.setIsSuccess(true);
        } catch (MyException e) {
            onMyException(e, "listTopUser", resultDTO);
        } catch (Exception e) {
            onException(e, "listTopUser", resultDTO);
        }
        return resultDTO;
    }

    private void setThreadContent(List<ThreadDTO> threadDTOs) throws Exception {
        for (ThreadDTO threadDTO : threadDTOs) {
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
            String content = new String(data, "utf-8").trim();
            String str = "";
            int start = content.indexOf("<body>"), end = 0;
            for (int i = start; i <= content.indexOf("</body>"); ++i) {
                if (content.charAt(i) == '>') {
                    start = i;
                } else if (content.charAt(i) == '<')
                    end = i;
                if (start < end) {
                    str += content.substring(start + 1, end).trim();
                    start = end = i;
                }
            }
            threadDTO.setContent(str);
        }
    }

    private void setImagePaths(List<ThreadDTO> threadDTOs) {
        for (ThreadDTO threadDTO : threadDTOs) {
            ImageQuery imageQuery = new ImageQuery();
            imageQuery.setParentId(threadDTO.getId());
            imageQuery.setType(ImageConstants.Type.PUBLISH_THREAD);
            imageQuery.setPageSize(3);
            ResultDTO<List<ImageDTO>> imageResult = imageService.query(imageQuery);
            if (imageResult.isSuccess()) {
                for (ImageDTO imageDTO : imageResult.getResult()) {
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
        ResultDTO<UserDTO> resultDTO = userService.queryById(threadDO.getAuthorId());
        if (resultDTO.getResult() != null) {
            threadDTO.setUserDTO(resultDTO.getResult());
        }
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
