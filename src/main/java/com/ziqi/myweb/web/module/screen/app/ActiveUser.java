package com.ziqi.myweb.web.module.screen.app;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ActiveConstants;
import com.ziqi.myweb.common.model.ActiveDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: ActiveDetail
 * User: qige
 * Date: 15/5/8
 * Time: 10:10
 */
public class ActiveUser extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;
    
    @Resource
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(ActiveUser.class);

    public void execute(Context context) {
        try {
            String activeId = request.getParameter("activeId");
            List<Integer> userIds = activeBiz.listUserIds(Integer.parseInt(activeId), context);
            List<UserDTO> userDTOs = new ArrayList<UserDTO>();
            ActiveDTO activeDTO = activeBiz.queryById(Integer.parseInt(activeId), context);
            userDTOs.add(activeDTO.getOwnerDTO());
            userDTOs.add(activeDTO.getTopBeauty());
            for(Integer userId : userIds) {
            	userDTOs.add(userBiz.queryById(userId, context));
            }
            context.put("userDTOs", userDTOs);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
