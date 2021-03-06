package com.ziqi.myweb.common.query;

/**
 * Description: ImageQuery
 * User: qige
 * Date: 15/4/19
 * Time: 12:22
 */
public class ImageQuery extends BaseQuery {

    private String filepath;

    private Integer userId;

    private Integer type;

    private Integer parentId;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
