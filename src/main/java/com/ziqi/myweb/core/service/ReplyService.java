package com.ziqi.myweb.core.service;

import com.ziqi.myweb.dal.model.ReplyDO;
import com.ziqi.myweb.common.model.ReplyDTO;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * Description: ReplyService
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class ReplyService extends BaseService<ReplyDTO, ReplyDO> {

    public ReplyService() {
        setLogger(LoggerFactory.getLogger(ReplyService.class));
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
        replyDTO.setContentId(replyDO.getContentId());
        replyDTO.setAuthorId(replyDO.getAuthorId());
        replyDTO.setFloor(replyDO.getFloor());
        replyDTO.setParentId(replyDO.getParentId());
        replyDTO.setReplyType(replyDO.getReplyType());
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
        replyDO.setContentId(replyDTO.getContentId());
        replyDO.setAuthorId(replyDTO.getAuthorId());
        replyDO.setFloor(replyDTO.getFloor());
        replyDO.setParentId(replyDTO.getParentId());
        replyDO.setReplyType(replyDTO.getReplyType());
        return replyDO;
    }
    @Override
    public List<ReplyDTO> DOsToDTOs(List<ReplyDO> replyDOs) {
        List<ReplyDTO> replyDTOs = new ArrayList<ReplyDTO>();
        for(ReplyDO replyDO : replyDOs) {
            replyDTOs.add(DOToDTO(replyDO));
        }
        return replyDTOs;
    }
    @Override
    public List<ReplyDO> DTOsToDOs(List<ReplyDTO> replyDTOs) {
        List<ReplyDO> replyDOs = new ArrayList<ReplyDO>();
        for(ReplyDTO replyDTO : replyDTOs) {
            replyDOs.add(DTOToDO(replyDTO));
        }
        return replyDOs;
    }
}
