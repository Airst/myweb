package com.ziqi.myweb.dal.dao;

import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.exception.MyException;
import com.ziqi.myweb.dal.model.ThreadDO;
import com.ziqi.myweb.dal.query.QueryMap;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Description: ThreadDAO
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class ThreadDAO extends BaseDAO<ThreadDO> {
    public ThreadDAO() {
        super(ThreadDAO.class);
    }

    public Integer updateHit(int threadId) throws MyException {
        try {
            return sqlMapClientTemplate.update(namespace + ".updateHit", threadId);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0030, e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<ThreadDO> search(QueryMap query) throws MyException {
        try {
            return sqlMapClientTemplate.queryForList(namespace + ".search", query);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0031, e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Integer> selectUser() throws MyException {
        try {
            return sqlMapClientTemplate.queryForList(namespace + ".selectUser");
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0032, e);
        }
    }

}
