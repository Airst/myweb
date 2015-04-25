package com.ziqi.myweb.core.service;

import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.constants.ImageConstants;
import com.ziqi.myweb.common.exception.MyException;
import com.ziqi.myweb.common.model.ImageDTO;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.dal.dao.ReplyDAO;
import com.ziqi.myweb.dal.model.ReplyDO;
import com.ziqi.myweb.common.model.ReplyDTO;
import com.ziqi.myweb.common.query.ReplyQuery;

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
 * Description: ReplyService
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class ReplyService extends BaseService<ReplyDTO, ReplyDO> {

    @Resource
    private ImageService imageService;

    @Resource
    private TransactionTemplate transactionTemplate;

    public ReplyService() {
        setLogger(LoggerFactory.getLogger(ReplyService.class));
    }
    public ResultDTO<Integer> publishReplyTop(final ReplyDTO replyDTO, final String savePath) {

        Object result = transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                try {
                    //更新parent帖子的数据（回复量）
                    ResultDTO<Integer> resultDTO = saveBasic(replyDTO).trySuccess();// auto throws
                    if(StringUtils.isNotBlank(replyDTO.getContent()) && StringUtils.isNotBlank(savePath)) {
                        saveContent(replyDTO.getContent(), savePath);
                        List<String> imagePaths = ImageService.findImagePaths(replyDTO.getContent());
                        for (String imagePath : imagePaths) {
                            ImageDTO imageDTO = new ImageDTO();
                            imageDTO.setFilepath(imagePath);
                            imageDTO.setParentId(resultDTO.getResult());
                            imageDTO.setUserId(replyDTO.getAuthorId());
                            imageDTO.setType(ImageConstants.Type.PUBLISH_REPLY);
                            imageService.saveBasic(imageDTO).trySuccess(); // auto throws
                        }
                    }
                    ((ReplyDAO) baseDAO).updateParent(DTOToDO(replyDTO));
                    return resultDTO;
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    return e;
                }
            }
        });

        return afterTransaction(result);
    }

    public ResultDTO<List<ReplyDTO>> selectReplyByThreadId(int threadId, int pageIndex) {
    	ResultDTO<List<ReplyDTO>> resultDTO = new ResultDTO<List<ReplyDTO>>();
        try {
            if(threadId <= 0) {
                throw new MyException(ErrorCode.ERR_AC_0002, "threadId <= 0");
            }
            ReplyQuery query = new ReplyQuery();
        	query.setThreadId(threadId);
        	query.setPageIndex(pageIndex);
        	query.setPageSize(20);
            resultDTO = query(query).trySuccess();
            setReplyContent(resultDTO.getResult());
        } catch (MyException e) {
            onMyException(e, "queryByThreadId", resultDTO, threadId);
        } catch (Exception e) {
            onException(e, "queryByThreadId", resultDTO, threadId);
        }
        return resultDTO;
    }
    
    private void setReplyContent(List<ReplyDTO> replyDTOs) throws Exception {
        for(ReplyDTO replyDTO : replyDTOs) {
            String classPath = this.getClass().getResource("").getPath();
            File file = new File(classPath.substring(0, classPath.indexOf("/webapps") + 8) + replyDTO.getContentPath());
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
            for (int i=start; i<=content.indexOf("</body>"); ++i) {
            	if(content.charAt(i) == '>')
            	{
            		start = i;
            	}
            	else if (content.charAt(i) == '<')
            		end = i;
            	if(start < end) {
            		str += content.substring(start+1, end).trim();
            		start = end = i;
            	}
            }
            replyDTO.setContent(str);
        }
    }

    @Override
    public ReplyDTO DOToDTO(ReplyDO replyDO) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setId(replyDO.getId());
        replyDTO.setFeature(replyDO.getFeature());
        replyDTO.setOptions(replyDO.getOptions());
        replyDTO.setIsDeleted(replyDO.getIsDeleted());
        replyDTO.setGmtCreate(replyDO.getGmtCreate());
        replyDTO.setGmtModified(replyDO.getGmtModified());
        replyDTO.setVersion(replyDO.getVersion());
        replyDTO.setContentPath(replyDO.getContentPath());
        replyDTO.setAuthorId(replyDO.getAuthorId());
        replyDTO.setAuthorAccount(replyDO.getAuthorAccount());
        replyDTO.setFloor(replyDO.getFloor());
        replyDTO.setThreadId(replyDO.getThreadId());
        replyDTO.setParentId(replyDO.getParentId());
        replyDTO.setReplyType(replyDO.getReplyType());
        replyDTO.setReplyCount(replyDO.getReplyCount());
        return replyDTO;
    }
    @Override
    public ReplyDO DTOToDO(ReplyDTO replyDTO) {
        ReplyDO replyDO = new ReplyDO();
        replyDO.setId(replyDTO.getId());
        replyDO.setFeature(replyDTO.getFeature());
        replyDO.setOptions(replyDTO.getOptions());
        replyDO.setIsDeleted(replyDTO.getIsDeleted());
        replyDO.setGmtCreate(replyDTO.getGmtCreate());
        replyDO.setGmtModified(replyDTO.getGmtModified());
        replyDO.setVersion(replyDTO.getVersion());
        replyDO.setContentPath(replyDTO.getContentPath());
        replyDO.setAuthorId(replyDTO.getAuthorId());
        replyDO.setAuthorAccount(replyDTO.getAuthorAccount());
        replyDO.setFloor(replyDTO.getFloor());
        replyDO.setThreadId(replyDTO.getThreadId());
        replyDO.setParentId(replyDTO.getParentId());
        replyDO.setReplyType(replyDTO.getReplyType());
        replyDO.setReplyCount(replyDTO.getReplyCount());
        return replyDO;
    }

}
