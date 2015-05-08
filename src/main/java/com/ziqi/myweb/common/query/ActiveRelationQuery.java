package com.ziqi.myweb.common.query;

import java.util.Date;

/**
 * Description: ActiveQuery
 * User: qige
 * Date: 15/5/6
 * Time: 12:43
 */
public class ActiveRelationQuery extends BaseQuery {

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
