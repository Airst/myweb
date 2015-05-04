package com.ziqi.myweb.common.model;

/**
 * Description: MessageDTO
 * User: qige
 * Date: 15/4/26
 * Time: 11:15
 */
public class MessageDTO extends BaseDTO {

    private Integer fromUserId;

    private String fromAccount;

    private UserDTO fromUserDTO = new UserDTO();

    private Integer toUserId;

    private String toAccount;

    private String content;

    private Integer type;

    private Integer status;

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public UserDTO getFromUserDTO() {
        return fromUserDTO;
    }

    public void setFromUserDTO(UserDTO fromUserDTO) {
        this.fromUserDTO = fromUserDTO;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
