package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ActiveConstants;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.model.ActiveDTO;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.query.ActiveQuery;
import com.ziqi.myweb.common.query.ThreadQuery;
import com.ziqi.myweb.core.service.ActiveService;
import com.ziqi.myweb.dal.model.ActiveDO;

import java.util.Date;
import java.util.List;


/**
 * Description: ActiveBiz
 * User: qige
 * Date: 15/4/16
 * Time: 22:11
 */
public class ActiveBiz extends BaseBiz<ActiveDTO, ActiveDO> {

    public Integer startActive(int userId, int topBeautyId, Date date, String address, String description, Context context) {
        ActiveDTO activeDTO = new ActiveDTO();
        activeDTO.setCount(2);
        activeDTO.setOwnerId(userId);
        activeDTO.setStartTime(new Date());
        activeDTO.setTopBeautyId(topBeautyId);
        activeDTO.setAddress(address);
        activeDTO.setStartTime(date);
        activeDTO.setStatus(ActiveConstants.Status.NEW);
        activeDTO.setDescription(description);
        return result(baseService.saveBasic(activeDTO), context);
    }

    public boolean join(int userId, int activeId, Context context) {
        return result(((ActiveService) baseService).join(userId, activeId), context);
    }

    public boolean isJoined(int userId, int activeId, Context context) {
        return result(((ActiveService) baseService).isJoined(userId, activeId), context);
    }

    public List<ActiveDTO> listActive(Context context) {
        return result(((ActiveService) baseService).listActive(), context);
    }
    
    public List<Integer> listUserIds(int activeId, Context context) {
    	return result(((ActiveService) baseService).listUserIds(activeId), context);
    }
    
    private boolean isNeedUpdate(Date lastModified, Context context) {
    	ActiveQuery query = new ActiveQuery();
        query.setStatus(ActiveConstants.Status.AGREED);
        query.setFromModified(lastModified);
    	Date date = new Date();
    	query.setToModified(date);
    	query.addOrderField(TableConstants.Base.gmtCreate, true);
        if(queryOne(query, context) != null) {
        	return true;
        }
        else {
        	return false;
        }
    }
    
    public List<ActiveDTO> listUpdateActive(Date lastModified, Context context) {
    	if(isNeedUpdate(lastModified,context)) {
	    	return listActive(context);
    	}
    	return null;
    }

    public List<ActiveDTO> listActiveAsOwner(int userId, Context context) {
        return result(((ActiveService) baseService).listActiveAsOwner(userId), context);
    }
    
    public List<ActiveDTO> listUpdateActiveAsOwner(Date lastModified, int userId, Context context) {
        return result(((ActiveService) baseService).listUpdateActiveAsOwner(lastModified, userId), context);
    }

    public List<ActiveDTO> listActiveAsBeauty(int userId, Context context) {
        return result(((ActiveService) baseService).listActiveAsBeauty(userId), context);
    }
    
    public List<ActiveDTO> listUpdateActiveAsBeauty(Date lastModified, int userId, Context context) {
        return result(((ActiveService) baseService).listUpdateActiveAsBeauty(lastModified, userId), context);
    }

    public List<ActiveDTO> listActiveAsActor(int userId, Context context) {
        return result(((ActiveService) baseService).listActiveAsActor(userId), context);
    }
    
    public List<ActiveDTO> listUpdateActiveAsActor(Date lastModified, int userId, Context context) {
        return result(((ActiveService) baseService).listUpdateActiveAsActor(lastModified, userId), context);
    }

}
