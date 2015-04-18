package com.ziqi.myweb.core.service;

import com.ziqi.myweb.dal.model.ContentDO;
import com.ziqi.myweb.common.model.ContentDTO;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * Description: ContentService
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class ContentService extends BaseService<ContentDTO, ContentDO> {

    public ContentService() {
        setLogger(LoggerFactory.getLogger(ContentService.class));
    }

    @Override
    public ContentDTO DOToDTO(ContentDO contentDO) {
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setId(contentDO.getId());
        contentDTO.setFeature(contentDO.getFeature());
        contentDTO.setOptions(contentDO.getOptions());
        contentDTO.setIsDeleted(contentDO.getIsDeleted());
        contentDTO.setGmtCreate(contentDO.getGmtCreate());
        contentDTO.setGmtModified(contentDO.getGmtModified());
        contentDTO.setVersion(contentDO.getVersion());
        return contentDTO;
    }
    @Override
    public ContentDO DTOToDO(ContentDTO contentDTO) {
        ContentDO contentDO = new ContentDO();
        contentDO.setId(contentDTO.getId());
        contentDO.setFeature(contentDTO.getFeature());
        contentDO.setOptions(contentDTO.getOptions());
        contentDO.setIsDeleted(contentDTO.getIsDeleted());
        contentDO.setGmtCreate(contentDTO.getGmtCreate());
        contentDO.setGmtModified(contentDTO.getGmtModified());
        contentDO.setVersion(contentDTO.getVersion());
        return contentDO;
    }
    @Override
    public List<ContentDTO> DOsToDTOs(List<ContentDO> contentDOs) {
        List<ContentDTO> contentDTOs = new ArrayList<ContentDTO>();
        for(ContentDO contentDO : contentDOs) {
            contentDTOs.add(DOToDTO(contentDO));
        }
        return contentDTOs;
    }
    @Override
    public List<ContentDO> DTOsToDOs(List<ContentDTO> contentDTOs) {
        List<ContentDO> contentDOs = new ArrayList<ContentDO>();
        for(ContentDTO contentDTO : contentDTOs) {
            contentDOs.add(DTOToDO(contentDTO));
        }
        return contentDOs;
    }
}
