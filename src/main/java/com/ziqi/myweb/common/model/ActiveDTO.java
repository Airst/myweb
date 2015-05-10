package com.ziqi.myweb.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description: ActiveDTO
 * User: qige
 * Date: 15/5/6
 * Time: 12:43
 */
public class ActiveDTO extends BaseDTO {

    private UserDTO topBeauty = new UserDTO();

    private UserDTO ownerDTO = new UserDTO();;

    private Integer count;

    private Date startTime;

    private String address;

    private Integer status;

    private String description;

    private List<UserDTO> userDTOs = new ArrayList<UserDTO>();

    public Integer getTopBeautyId() {
        return topBeauty.getId();
    }

    public void setTopBeautyId(Integer topBeautyId) {
        topBeauty.setId(topBeautyId);
    }

    public Integer getOwnerId() {
        return ownerDTO.getId();
    }

    public void setOwnerId(Integer ownerId) {
        ownerDTO.setId(ownerId);
    }

    public UserDTO getTopBeauty() {
        return topBeauty;
    }

    public void setTopBeauty(UserDTO topBeauty) {
        this.topBeauty = topBeauty;
    }

    public UserDTO getOwnerDTO() {
        return ownerDTO;
    }

    public void setOwnerDTO(UserDTO ownerDTO) {
        this.ownerDTO = ownerDTO;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<UserDTO> getUserDTOs() {
        return userDTOs;
    }

    public void setUserDTOs(List<UserDTO> userDTOs) {
        this.userDTOs = userDTOs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
