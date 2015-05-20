package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ActiveDTO;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: ViewBeauty
 * User: qige
 * Date: 15/5/4
 * Time: 14:01
 */
public class ViewBeauty extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;

    @Resource
    private ThreadBiz threadBiz;

    @Resource
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(Profile.class);

    public void execute(Context context) {
        try {
            String userId = request.getParameter("userId");
            if(StringUtils.isBlank(userId)) {
                response.sendRedirect(getHostUrl() + "/topBeauty.htm");
                return;
            }

            if(getUserId().equals(Integer.valueOf(userId))) {
                response.sendRedirect(getHostUrl() + "/profile.htm");
                return;
            }

            UserDTO userDTO = userBiz.queryById(Integer.parseInt(userId), context);
            context.put("userDTO", userDTO);

            List<ActiveDTO> activeDTOs = new ArrayList<ActiveDTO>();
            activeDTOs.addAll(activeBiz.listActiveAsOwner(getUserId(), context));
            activeDTOs.addAll(activeBiz.listActiveAsBeauty(getUserId(), context));
            activeDTOs.addAll(activeBiz.listActiveAsActor(getUserId(), context));
            context.put("activeDTOs", activeDTOs);

            List<ThreadDTO> threadDTOs = threadBiz.listUserThread(getUserId(), context);
            context.put("threadDTOs", threadDTOs);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
