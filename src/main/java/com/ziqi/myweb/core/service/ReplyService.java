package com.ziqi.myweb.core.service;

import com.ziqi.myweb.common.constants.ImageConstants;
import com.ziqi.myweb.common.model.ImageDTO;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.dal.dao.ReplyDAO;
import com.ziqi.myweb.dal.model.ReplyDO;
import com.ziqi.myweb.common.model.ReplyDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
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
    public ResultDTO<Integer> publishReply(final ReplyDTO replyDTO, final String savePath) {

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
        replyDO.setFloor(replyDTO.getFloor());
        replyDO.setThreadId(replyDTO.getThreadId());
        replyDO.setParentId(replyDTO.getParentId());
        replyDO.setReplyType(replyDTO.getReplyType());
        replyDO.setReplyCount(replyDTO.getReplyCount());
        return replyDO;
    }

}
