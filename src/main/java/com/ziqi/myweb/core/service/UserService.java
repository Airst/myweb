package com.ziqi.myweb.core.service;

import com.ziqi.myweb.dal.model.UserDO;
import com.ziqi.myweb.common.model.UserDTO;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * Description: UserService
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class UserService extends BaseService<UserDTO, UserDO> {

    public UserService() {
        setLogger(LoggerFactory.getLogger(UserService.class));
    }

    @Override
    public UserDTO DOToDTO(UserDO userDO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userDO.getId());
        userDTO.setFeature(userDO.getFeature());
        userDTO.setOptions(userDO.getOptions());
        userDTO.setIsDeleted(userDO.getIsDeleted());
        userDTO.setGmtCreate(userDO.getGmtCreate());
        userDTO.setGmtModified(userDO.getGmtModified());
        userDTO.setVersion(userDO.getVersion());
        userDTO.setAccount(userDO.getAccount());
        userDTO.setPassword(userDO.getPassword());
        userDTO.setName(userDO.getName());
        userDTO.setEmail(userDO.getEmail());
        userDTO.setPhone(userDO.getPhone());
        userDTO.setAge(userDO.getAge());
        userDTO.setGender(userDO.getGender());
        userDTO.setLevel(userDO.getLevel());
        return userDTO;
    }
    @Override
    public UserDO DTOToDO(UserDTO userDTO) {
        UserDO userDO = new UserDO();
        userDO.setId(userDTO.getId());
        userDO.setFeature(userDTO.getFeature());
        userDO.setOptions(userDTO.getOptions());
        userDO.setIsDeleted(userDTO.getIsDeleted());
        userDO.setGmtCreate(userDTO.getGmtCreate());
        userDO.setGmtModified(userDTO.getGmtModified());
        userDO.setVersion(userDTO.getVersion());
        userDO.setAccount(userDTO.getAccount());
        userDO.setPassword(userDTO.getPassword());
        userDO.setName(userDTO.getName());
        userDO.setEmail(userDTO.getEmail());
        userDO.setPhone(userDTO.getPhone());
        userDO.setAge(userDTO.getAge());
        userDO.setGender(userDTO.getGender());
        userDO.setLevel(userDTO.getLevel());
        return userDO;
    }
    @Override
    public List<UserDTO> DOsToDTOs(List<UserDO> userDOs) {
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        for(UserDO userDO : userDOs) {
            userDTOs.add(DOToDTO(userDO));
        }
        return userDTOs;
    }
    @Override
    public List<UserDO> DTOsToDOs(List<UserDTO> userDTOs) {
        List<UserDO> userDOs = new ArrayList<UserDO>();
        for(UserDTO userDTO : userDTOs) {
            userDOs.add(DTOToDO(userDTO));
        }
        return userDOs;
    }
}
