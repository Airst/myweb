package com.ziqi.myweb.core.service;

import com.ziqi.myweb.dal.model.ThreadDO;
import com.ziqi.myweb.common.model.ThreadDTO;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * Description: ThreadService
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class ThreadService extends BaseService<ThreadDTO, ThreadDO> {

    public ThreadService() {
        setLogger(LoggerFactory.getLogger(ThreadService.class));
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
        threadDTO.setContentId(threadDO.getContentId());
        threadDTO.setAuthorId(threadDO.getAuthorId());
        threadDTO.setHit(threadDO.getHit());
        threadDTO.setReplyCount(threadDO.getReplyCount());
        threadDTO.setLastReplyDate(threadDO.getLastReplyDate());
        threadDTO.setLevel(threadDO.getLevel());
        threadDTO.setTags(threadDO.getTags());
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
        threadDO.setContentId(threadDTO.getContentId());
        threadDO.setAuthorId(threadDTO.getAuthorId());
        threadDO.setHit(threadDTO.getHit());
        threadDO.setReplyCount(threadDTO.getReplyCount());
        threadDO.setLastReplyDate(threadDTO.getLastReplyDate());
        threadDO.setLevel(threadDTO.getLevel());
        threadDO.setTags(threadDTO.getTags());
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
