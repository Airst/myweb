package com.ziqi.myweb.core.service;

import com.ziqi.myweb.dal.model.ImageDO;
import com.ziqi.myweb.common.model.ImageDTO;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * Description: ImageService
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class ImageService extends BaseService<ImageDTO, ImageDO> {

    public ImageService() {
        setLogger(LoggerFactory.getLogger(ImageService.class));
    }

    @Override
    public ImageDTO DOToDTO(ImageDO imageDO) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(imageDO.getId());
        imageDTO.setFeature(imageDO.getFeature());
        imageDTO.setOptions(imageDO.getOptions());
        imageDTO.setIsDeleted(imageDO.getIsDeleted());
        imageDTO.setGmtCreate(imageDO.getGmtCreate());
        imageDTO.setGmtModified(imageDO.getGmtModified());
        imageDTO.setVersion(imageDO.getVersion());
        imageDTO.setFilepath(imageDO.getFilepath());
        imageDTO.setUserId(imageDO.getUserId());
        imageDTO.setType(imageDO.getType());
        imageDTO.setThreadId(imageDO.getThreadId());
        return imageDTO;
    }
    @Override
    public ImageDO DTOToDO(ImageDTO imageDTO) {
        ImageDO imageDO = new ImageDO();
        imageDO.setId(imageDTO.getId());
        imageDO.setFeature(imageDTO.getFeature());
        imageDO.setOptions(imageDTO.getOptions());
        imageDO.setIsDeleted(imageDTO.getIsDeleted());
        imageDO.setGmtCreate(imageDTO.getGmtCreate());
        imageDO.setGmtModified(imageDTO.getGmtModified());
        imageDO.setVersion(imageDTO.getVersion());
        imageDO.setFilepath(imageDTO.getFilepath());
        imageDO.setUserId(imageDTO.getUserId());
        imageDO.setType(imageDTO.getType());
        imageDO.setThreadId(imageDTO.getThreadId());
        return imageDO;
    }
    @Override
    public List<ImageDTO> DOsToDTOs(List<ImageDO> imageDOs) {
        List<ImageDTO> imageDTOs = new ArrayList<ImageDTO>();
        for(ImageDO imageDO : imageDOs) {
            imageDTOs.add(DOToDTO(imageDO));
        }
        return imageDTOs;
    }
    @Override
    public List<ImageDO> DTOsToDOs(List<ImageDTO> imageDTOs) {
        List<ImageDO> imageDOs = new ArrayList<ImageDO>();
        for(ImageDTO imageDTO : imageDTOs) {
            imageDOs.add(DTOToDO(imageDTO));
        }
        return imageDOs;
    }
}
