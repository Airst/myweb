package com.ziqi.myweb.core.service;

import com.ziqi.myweb.common.constants.ImageConstants;
import com.ziqi.myweb.common.model.ImageDTO;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.dal.model.ThreadDO;
import com.ziqi.myweb.common.model.ThreadDTO;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;

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

                    saveContent(threadDTO.getContent(), savePath);

                    List<String> imagePaths = findImagePaths(threadDTO.getContent());
                    for (String imagePath : imagePaths) {
                        ImageDTO imageDTO = new ImageDTO();
                        imageDTO.setFilepath(imagePath);
                        imageDTO.setThreadId(threadDTO.getId());
                        imageDTO.setUserId(threadDTO.getAuthorId());
                        imageDTO.setType(ImageConstants.Type.PUBLISH);
                        imageService.saveBasic(imageDTO).trySuccess(); // auto throws
                    }

                    return saveBasic(threadDTO).trySuccess(); // auto throws
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    return e;
                }
            }
        });

        return afterTransaction(result);
    }

    private boolean saveContent(String content, String savePath) throws Exception {
        File contentFile = new File(savePath);
        if(contentFile.exists() || contentFile.createNewFile()) {
            FileOutputStream outputStream = new FileOutputStream(contentFile);
            outputStream.write(content.getBytes());
            outputStream.close();
            return true;
        }
        return false;
    }

    private List<String> findImagePaths(String data) {
        List<String> imageSrcs = new ArrayList<String>();
        String[] lines = data.split("\n");
        for(String line : lines) {
            line = line.trim();
            if(line.contains("<img")) {
                String[] items = line.split(" ");
                if(items[1].startsWith("src")) {
                    imageSrcs.add(items[1].substring(5, items[1].length() - 1));
                }
            }
        }
        return imageSrcs;
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
    @Override
    public List<ThreadDTO> DOsToDTOs(List<ThreadDO> threadDOs) {
        List<ThreadDTO> threadDTOs = new ArrayList<ThreadDTO>();
        for(ThreadDO threadDO : threadDOs) {
            threadDTOs.add(DOToDTO(threadDO));
        }
        return threadDTOs;
    }
    @Override
    public List<ThreadDO> DTOsToDOs(List<ThreadDTO> threadDTOs) {
        List<ThreadDO> threadDOs = new ArrayList<ThreadDO>();
        for(ThreadDTO threadDTO : threadDTOs) {
            threadDOs.add(DTOToDO(threadDTO));
        }
        return threadDOs;
    }
}
