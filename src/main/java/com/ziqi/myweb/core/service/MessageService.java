package com.ziqi.myweb.core.service;

import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.exception.MyException;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.MessageQuery;
import com.ziqi.myweb.dal.dao.MessageDAO;
import com.ziqi.myweb.dal.model.MessageDO;
import com.ziqi.myweb.common.model.MessageDTO;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: MessageService
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class MessageService extends BaseService<MessageDTO, MessageDO> {

    @Resource
    private UserService userService;

    public MessageService() {
        setLogger(LoggerFactory.getLogger(MessageService.class));
    }

    public ResultDTO<Integer> updateRead(int toUserId, int fromUserId) {
        ResultDTO<Integer> resultDTO = new ResultDTO<Integer>();
        try {
            if(toUserId <= 0) {
                throw new MyException(ErrorCode.ERR_AC_0002, "toUserId <= 0");
            }
            if(fromUserId <= 0) {
                throw new MyException(ErrorCode.ERR_AC_0002, "fromUserId <= 0");
            }

            resultDTO.setResult(((MessageDAO) baseDAO).updateRead(toUserId, fromUserId));
            resultDTO.setIsSuccess(true);
        } catch (MyException e) {
            onMyException(e, "updateRead", resultDTO, toUserId, fromUserId);
        } catch (Exception e) {
            onException(e, "updateRead", resultDTO, toUserId, fromUserId);
        }
        return resultDTO;
    }

    public ResultDTO<List<MessageDTO>> listMessages(MessageQuery query) {
        ResultDTO<List<MessageDTO>> resultDTO = new ResultDTO<List<MessageDTO>>();
        try {
            if(query == null) {
                throw new MyException(ErrorCode.ERR_AC_0001, "query==null");
            }
            List<MessageDO> messageDOs = ((MessageDAO) baseDAO).selectGroup(QueryToMap(query));
            List<MessageDTO> messageDTOs = DOsToDTOs(messageDOs);
            resultDTO.setResult(messageDTOs);
            resultDTO.setIsSuccess(true);
        } catch (MyException e) {
            onMyException(e, "listMessages", resultDTO, query);
        } catch (Exception e) {
            onException(e, "listMessages", resultDTO, query);
        }
        return resultDTO;
    }

    @Override
    public MessageDTO DOToDTO(MessageDO messageDO) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(messageDO.getId());
        messageDTO.setFeature(messageDO.getFeature());
        messageDTO.setOptions(messageDO.getOptions());
        messageDTO.setIsDeleted(messageDO.getIsDeleted());
        messageDTO.setGmtCreate(messageDO.getGmtCreate());
        messageDTO.setGmtModified(messageDO.getGmtModified());
        messageDTO.setVersion(messageDO.getVersion());
        messageDTO.setFromUserId(messageDO.getFromUserId());
        messageDTO.setFromAccount(messageDO.getFromAccount());
        if(messageDO.getFromUserId() != 0) {
            ResultDTO<UserDTO> resultDTO = userService.queryById(messageDO.getFromUserId());
            if (resultDTO.getResult() != null) {
                messageDTO.setFromUserDTO(resultDTO.getResult());
            }
        }
        messageDTO.setToUserId(messageDO.getToUserId());
        messageDTO.setToAccount(messageDO.getToAccount());
        messageDTO.setContent(messageDO.getContent());
        messageDTO.setType(messageDO.getType());
        messageDTO.setStatus(messageDO.getStatus());
        return messageDTO;
    }
    @Override
    public MessageDO DTOToDO(MessageDTO messageDTO) {
        MessageDO messageDO = new MessageDO();
        messageDO.setId(messageDTO.getId());
        messageDO.setFeature(messageDTO.getFeature());
        messageDO.setOptions(messageDTO.getOptions());
        messageDO.setIsDeleted(messageDTO.getIsDeleted());
        messageDO.setGmtCreate(messageDTO.getGmtCreate());
        messageDO.setGmtModified(messageDTO.getGmtModified());
        messageDO.setVersion(messageDTO.getVersion());
        messageDO.setFromUserId(messageDTO.getFromUserId());
        messageDO.setFromAccount(messageDTO.getFromAccount());
        messageDO.setToUserId(messageDTO.getToUserId());
        messageDO.setToAccount(messageDTO.getToAccount());
        messageDO.setContent(messageDTO.getContent());
        messageDO.setType(messageDTO.getType());
        messageDO.setStatus(messageDTO.getStatus());
        return messageDO;
    }

}
