package com.ziqi.myweb.core.service;

import com.ziqi.myweb.common.constants.ActiveConstants;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.exception.MyException;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.ActiveQuery;
import com.ziqi.myweb.common.query.ActiveRelationQuery;
import com.ziqi.myweb.dal.dao.ActiveDAO;
import com.ziqi.myweb.dal.dao.ActiveRelationDAO;
import com.ziqi.myweb.dal.model.ActiveDO;
import com.ziqi.myweb.common.model.ActiveDTO;
import com.ziqi.myweb.dal.model.ActiveRelationDO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Description: ActiveService
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class ActiveService extends BaseService<ActiveDTO, ActiveDO> {

    @Resource
    private ActiveRelationDAO activeRelationDAO;

    @Resource
    private UserService userService;

    public ActiveService() {
        setLogger(LoggerFactory.getLogger(ActiveService.class));
    }

    public ResultDTO<Boolean> join(int userId, int activeId) {
        ResultDTO<Boolean> resultDTO = new ResultDTO<Boolean>();
        try {
            ActiveRelationDO activeRelationDO = new ActiveRelationDO();
            activeRelationDO.setActiveId(activeId);
            activeRelationDO.setUserId(userId);
            activeRelationDAO.insert(activeRelationDO);

            ((ActiveDAO) baseDAO).updateCount(activeId);

            resultDTO.setIsSuccess(true);
            resultDTO.setResult(true);
        } catch (MyException e) {
            onMyException(e, "join", resultDTO, userId, activeId);
        } catch (Exception e) {
            onException(e, "join", resultDTO, userId, activeId);
        }
        return resultDTO;
    }

    public ResultDTO<Boolean> isJoined(int userId, int activeId) {

        ResultDTO<Boolean> resultDTO = new ResultDTO<Boolean>();
        try {
            ActiveRelationQuery query = new ActiveRelationQuery();
            query.setActiveId(activeId);
            query.setUserId(userId);
            List<ActiveRelationDO> relationDOs = activeRelationDAO.select(QueryToMap(query));
            if(CollectionUtils.isEmpty(relationDOs)) {
                resultDTO.setResult(false);
            } else {
                resultDTO.setResult(true);
            }
            resultDTO.setIsSuccess(true);
        } catch (MyException e) {
            onMyException(e, "isJoined", resultDTO, userId, activeId);
        } catch (Exception e) {
            onException(e, "isJoined", resultDTO, userId, activeId);
        }
        return resultDTO;
    }

    public ResultDTO<List<ActiveDTO>> listActive() {
        ResultDTO<List<ActiveDTO>> resultDTO = new ResultDTO<List<ActiveDTO>>();
        try {
            ActiveQuery query = new ActiveQuery();
            query.setStatus(ActiveConstants.Status.AGREED);
            query.addOrderField(TableConstants.Base.gmtCreate, true);
            resultDTO = query(query);
        } catch (Exception e) {
            onException(e, "listActive", resultDTO);
        }
        return resultDTO;
    }

    public ResultDTO<List<ActiveDTO>> listActiveAsOwner(int userId) {
        ResultDTO<List<ActiveDTO>> resultDTO = new ResultDTO<List<ActiveDTO>>();
        try {
            ActiveQuery query = new ActiveQuery();
            query.setOwnerId(userId);
            query.addOrderField(TableConstants.Base.gmtCreate, true);
            resultDTO = query(query);
        } catch (Exception e) {
            onException(e, "listActiveAsOwner", resultDTO);
        }
        return resultDTO;
    }

    public ResultDTO<List<ActiveDTO>> listActiveAsBeauty(int userId) {
        ResultDTO<List<ActiveDTO>> resultDTO = new ResultDTO<List<ActiveDTO>>();
        try {
            ActiveQuery query = new ActiveQuery();
            query.setTopBeautyId(userId);
            query.addOrderField(TableConstants.Base.gmtCreate, true);
            resultDTO = query(query);
        } catch (Exception e) {
            onException(e, "listActiveAsBeauty", resultDTO);
        }
        return resultDTO;
    }

    public ResultDTO<List<ActiveDTO>> listActiveAsActor(int userId) {
        ResultDTO<List<ActiveDTO>> resultDTO = new ResultDTO<List<ActiveDTO>>();
        try {
            ActiveRelationQuery query = new ActiveRelationQuery();
            query.setUserId(userId);
            List<ActiveRelationDO> relationDOs = activeRelationDAO.select(QueryToMap(query));
            List<ActiveDO> activeDOs = ((ActiveDAO) baseDAO).selectByRelations(relationDOs);
            resultDTO.setResult(DOsToDTOs(activeDOs));
            resultDTO.setIsSuccess(true);
        } catch (Exception e) {
            onException(e, "listActiveAsActor", resultDTO);
        }
        return resultDTO;
    }

    @Override
    public ActiveDTO DOToDTO(ActiveDO activeDO) {
        ActiveDTO activeDTO = new ActiveDTO();
        activeDTO.setId(activeDO.getId());
        activeDTO.setFeature(activeDO.getFeature());
        activeDTO.setOptions(activeDO.getOptions());
        activeDTO.setIsDeleted(activeDO.getIsDeleted());
        activeDTO.setGmtCreate(activeDO.getGmtCreate());
        activeDTO.setGmtModified(activeDO.getGmtModified());
        activeDTO.setVersion(activeDO.getVersion());

        ResultDTO<UserDTO> resultDTO = userService.queryById(activeDO.getTopBeautyId());
        if(resultDTO.getResult() != null) {
            activeDTO.setTopBeauty(resultDTO.getResult());
        }

        resultDTO = userService.queryById(activeDO.getOwnerId());
        if(resultDTO.getResult() != null) {
            activeDTO.setOwnerDTO(resultDTO.getResult());
        }

        activeDTO.setCount(activeDO.getCount());
        activeDTO.setStartTime(activeDO.getStartTime());
        activeDTO.setAddress(activeDO.getAddress());
        activeDTO.setStatus(activeDO.getStatus());
        return activeDTO;
    }
    @Override
    public ActiveDO DTOToDO(ActiveDTO activeDTO) {
        ActiveDO activeDO = new ActiveDO();
        activeDO.setId(activeDTO.getId());
        activeDO.setFeature(activeDTO.getFeature());
        activeDO.setOptions(activeDTO.getOptions());
        activeDO.setIsDeleted(activeDTO.getIsDeleted());
        activeDO.setGmtCreate(activeDTO.getGmtCreate());
        activeDO.setGmtModified(activeDTO.getGmtModified());
        activeDO.setVersion(activeDTO.getVersion());
        activeDO.setTopBeautyId(activeDTO.getTopBeautyId());
        activeDO.setOwnerId(activeDTO.getOwnerId());
        activeDO.setCount(activeDTO.getCount());
        activeDO.setStartTime(activeDTO.getStartTime());
        activeDO.setAddress(activeDTO.getAddress());
        activeDO.setStatus(activeDTO.getStatus());
        return activeDO;
    }
}
