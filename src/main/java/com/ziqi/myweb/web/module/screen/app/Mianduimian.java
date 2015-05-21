package com.ziqi.myweb.web.module.screen.app;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ActiveDTO;
import com.ziqi.myweb.common.model.MessageDTO;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.module.BaseModule;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: faceToFace
 * User: qige
 * Date: 15/5/8
 * Time: 13:39
 */
public class Mianduimian extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;

    private Logger logger = LoggerFactory.getLogger(Mianduimian.class);

    public void execute(Context context) {
        try {
        	String userId = request.getParameter("userId");
        	String isMyActive = request.getParameter("isMyActive");
        	String lastModified = request.getParameter("lastModified");
        	List<ActiveDTO> activeDTOs = null;
        	if(isMyActive == null || !isMyActive.equals("1")) {
        		if(lastModified != null) {
	        		Date date = new Date();    
	                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	                try {   
	                    date = sdf.parse(lastModified);  
	                } catch (Exception e) {   
	                    e.printStackTrace();   
	                }  
	        		activeDTOs = activeBiz.listUpdateActive(date, context);
	        		/*if(activeDTOs != null) {
	        			context.put("threadDTOs", activeDTOs);
	        		}
	        		else {
	        			//do nothing
	        		}*/
	        	}
        		else {
        			activeDTOs = activeBiz.listActive(context);
        		}
        	}
        	else {
        		if(lastModified != null) {
	        		Date date = new Date();    
	                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	                try {   
	                    date = sdf.parse(lastModified);  
	                } catch (Exception e) {   
	                    e.printStackTrace();   
	                }  
	                activeDTOs = new ArrayList<ActiveDTO>();
	        		activeDTOs.addAll(activeBiz.listUpdateActiveAsOwner(date, Integer.parseInt(userId), context));
	        		activeDTOs.addAll(activeBiz.listUpdateActiveAsBeauty(date, Integer.parseInt(userId), context));
	        		activeDTOs.addAll(activeBiz.listUpdateActiveAsActor(date, Integer.parseInt(userId), context));
        		}
        		else {
	        		activeDTOs = new ArrayList<ActiveDTO>();
	        		activeDTOs.addAll(activeBiz.listActiveAsOwner(Integer.parseInt(userId), context));
	        		activeDTOs.addAll(activeBiz.listActiveAsBeauty(Integer.parseInt(userId), context));
	        		activeDTOs.addAll(activeBiz.listActiveAsActor(Integer.parseInt(userId), context));
        		}
        		//排序
        		if(activeDTOs != null) {
	                Collections.sort(activeDTOs, new Comparator<ActiveDTO>() {
	                    @Override
	                    public int compare(ActiveDTO o1, ActiveDTO o2) {
	                        return o2.getGmtCreate().compareTo(o1.getGmtCreate());
	                    }
	                });
        		}
        	}
        	List<Integer> isJoin = new ArrayList<Integer>();
        	for(ActiveDTO activeDTO : activeDTOs) {
        		if(StringUtils.isBlank(userId)) {
        			isJoin.add(0);
        			continue;
        		}
        		if(activeDTO.getOwnerId().equals(Integer.parseInt(userId)) || activeDTO.getTopBeauty().getId().equals(Integer.parseInt(userId))) {
                    isJoin.add(1);
                } else {
                    isJoin.add(activeBiz.isJoined(Integer.parseInt(userId), activeDTO.getId(), context) ? 1 : 0);
                }
        	}
            context.put("activeDTOs", activeDTOs);
            context.put("isJoin", isJoin);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
