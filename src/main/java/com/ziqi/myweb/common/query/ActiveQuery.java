package com.ziqi.myweb.common.query;

import java.util.Date;

/**
 * Description: ActiveQuery
 * User: qige
 * Date: 15/5/6
 * Time: 12:43
 */
public class ActiveQuery extends BaseQuery {

    private Integer topBeautyId;

    private Integer ownerId;

    private Integer count;

    private Date startTime;

    private String address;

    private Integer status;

    private String description;

    public Integer getTopBeautyId() {
        return topBeautyId;
    }

    public void setTopBeautyId(Integer topBeautyId) {
        this.topBeautyId = topBeautyId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
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
