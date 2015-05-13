package com.ziqi.myweb.dal.dao;

import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.exception.MyException;
import com.ziqi.myweb.dal.model.MessageDO;
import com.ziqi.myweb.dal.query.QueryMap;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: MessageDAO
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class MessageDAO extends BaseDAO<MessageDO> {
    public MessageDAO() {
        super(MessageDAO.class);
    }

    public int updateRead(int toUserId, int fromUserId) throws MyException {
        try {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("fromUserId", fromUserId);
            map.put("toUserId", toUserId);
            return sqlMapClientTemplate.update(namespace + ".updateRead", map);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0050, e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<MessageDO> selectGroup(QueryMap queryMap) throws MyException {
        try {
            return sqlMapClientTemplate.queryForList(namespace + ".selectGroup", queryMap);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0051, e);
        }
    }
}
