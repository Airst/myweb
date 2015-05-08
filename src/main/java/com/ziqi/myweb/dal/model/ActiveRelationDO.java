package com.ziqi.myweb.dal.model;

/**
 * Description: ActiveRelation
 * User: qige
 * Date: 15/5/6
 * Time: 12:58
 */
public class ActiveRelationDO extends BaseDO {

    private Integer activeId;

    private Integer userId;

    public Integer getActiveId() {
        return activeId;
    }

    public void setActiveId(Integer activeId) {
        this.activeId = activeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
