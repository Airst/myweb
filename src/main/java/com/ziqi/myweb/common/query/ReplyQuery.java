package com.ziqi.myweb.common.query;

/**
 * Description: ReplyQuery
 * User: qige
 * Date: 15/4/12
 * Time: 12:37
 */
public class ReplyQuery extends BaseQuery {

    private Integer contentId;

    private Integer authorId;

    private Integer floor;

    private Integer parentId;

    private Integer replyType;

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }

}
