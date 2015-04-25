package com.ziqi.myweb.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: ReplyDTO
 * User: qige
 * Date: 15/4/12
 * Time: 12:37
 */
public class ReplyDTO extends BaseDTO {

    private String contentPath;

    private String content;

    private Integer authorId;

    private String authorAccount;

    private Integer floor;

    private Integer threadId;

    private Integer parentId;

    private Integer replyType;

    private Integer replyCount;

    private List<ReplyDTO> subReplyDTOs = new ArrayList<ReplyDTO>(0);

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorAccount() {
        return authorAccount;
    }

    public void setAuthorAccount(String authorAccount) {
        this.authorAccount = authorAccount;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
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

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public List<ReplyDTO> getSubReplyDTOs() {
        return subReplyDTOs;
    }

    public void setSubReplyDTOs(List<ReplyDTO> subReplyDTOs) {
        this.subReplyDTOs = subReplyDTOs;
    }
}
