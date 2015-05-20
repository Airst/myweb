package com.ziqi.myweb.web.module.screen.app;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ActiveDTO;
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
        	List<ActiveDTO> activeDTOs = activeBiz.listActive(context);
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
            context.put("activeDTOs", activeBiz.listActive(context));
            context.put("isJoin", isJoin);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
