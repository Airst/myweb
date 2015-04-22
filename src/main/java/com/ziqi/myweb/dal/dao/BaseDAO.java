package com.ziqi.myweb.dal.dao;

import com.ziqi.myweb.common.exception.MyException;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.dal.query.QueryMap;
import com.ziqi.myweb.dal.model.BaseDO;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.util.List;

/**
 * Description: IbatisBaseDAO
 * User: qige
 * Date: 15/4/11
 * Time: 17:53
 */
public class BaseDAO<T extends BaseDO> {

    protected SqlMapClientTemplate sqlMapClientTemplate;

    protected String namespace = "";

    public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
        this.sqlMapClientTemplate = sqlMapClientTemplate;
    }

    public BaseDAO(Class< ? extends BaseDAO> clazz) {
        namespace = clazz.getSimpleName();
    }

    @SuppressWarnings("unchecked")
    public List<T> select(QueryMap query) throws MyException {
        try {
            return (List<T>) sqlMapClientTemplate.queryForList(namespace + ".select", query);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0001, e);
        }
    }

    @SuppressWarnings("unchecked")
    public T selectById(int id) throws MyException {
        try {
            return (T) sqlMapClientTemplate.queryForObject(namespace + ".selectById", id);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0002, e);
        }
    }

    public Integer count(QueryMap query) throws MyException {
        try {
            return (Integer) sqlMapClientTemplate.queryForObject(namespace + ".selectCount", query);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0003, e);
        }
    }

    public Integer insert(T t) throws MyException {
        try {
            return (Integer) sqlMapClientTemplate.insert(namespace + ".insert", t);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0004, e);
        }
    }

    public Integer update(T t) throws MyException {
        try {
            return sqlMapClientTemplate.update(namespace + ".update", t);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0005, e);
        }
    }

    public Integer delete(int id) throws MyException {
        try {
            return sqlMapClientTemplate.delete(namespace + ".delete", id);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0006, e);
        }
    }

}
