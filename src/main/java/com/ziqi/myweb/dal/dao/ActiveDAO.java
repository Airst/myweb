package com.ziqi.myweb.dal.dao;

import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.exception.MyException;
import com.ziqi.myweb.dal.model.ActiveDO;
import com.ziqi.myweb.dal.model.ActiveRelationDO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: ActiveDAO
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class ActiveDAO extends BaseDAO<ActiveDO> {
    public ActiveDAO() {
        super(ActiveDAO.class);
    }

    public Integer updateCount(int activeId) throws MyException {
        try {
            return sqlMapClientTemplate.update(namespace + ".updateCount", activeId);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0070, e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<ActiveDO> selectByRelations(List<ActiveRelationDO> relationDOs) throws MyException {
        try {
            if(CollectionUtils.isEmpty(relationDOs)) {
                return new ArrayList<ActiveDO>();
            }
            List<Integer> ids = new ArrayList<Integer>();
            for(ActiveRelationDO relationDO : relationDOs) {
                ids.add(relationDO.getActiveId());
            }
            return sqlMapClientTemplate.queryForList(namespace + ".selectByIds", ids);
        } catch (DataAccessException e) {
            throw new MyException(ErrorCode.ERR_DAE_0071, e);
        }
    }
}
