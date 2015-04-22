package com.ziqi.myweb.dal.dao;

import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.exception.MyException;
import com.ziqi.myweb.dal.model.ReplyDO;
import org.springframework.dao.DataAccessException;

/**
 * Description: ReplyDAO
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class ReplyDAO extends BaseDAO<ReplyDO> {
    public ReplyDAO() {
        super(ReplyDAO.class);
    }

    public Integer updateParent(ReplyDO replyDO) throws MyException {
        try {
            return sqlMapClientTemplate.update(namespace + ".updateParent", replyDO);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0005, e);
        }
    }
}
