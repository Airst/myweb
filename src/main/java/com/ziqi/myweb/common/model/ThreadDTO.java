package com.ziqi.myweb.common.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description: ArticleDTO
 * User: qige
 * Date: 15/4/11
 * Time: 01:33
 */
public class ThreadDTO extends BaseDTO {

    private String title;

    private String contentPath;

    private String content;

    private UserDTO userDTO = new UserDTO();

    private Integer hit;

    private Integer replyCount;

    private Integer likeCount;

    private Date lastReplyDate;

    private Integer level;

    private Integer tags;

    private List<String> imagePaths = new ArrayList<String>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
        return userDTO.getId();
    }

    public void setAuthorId(Integer id) {
        userDTO.setId(id);
    }

    public String getAuthorAccount() {
        return userDTO.getAccount();
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Integer getHit() {
        return hit;
    }

    public void setHit(Integer hit) {
        this.hit = hit;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Date getLastReplyDate() {
        return lastReplyDate;
    }

    public void setLastReplyDate(Date lastReplyDate) {
        this.lastReplyDate = lastReplyDate;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getTags() {
        return tags;
    }

    public void setTags(Integer tags) {
        this.tags = tags;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public void addImagePath(String imagePath) {
        this.imagePaths.add(imagePath);
    }
}
