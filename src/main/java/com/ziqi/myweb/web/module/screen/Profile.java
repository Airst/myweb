package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: Profile
 * User: qige
 * Date: 15/4/30
 * Time: 18:01
 */
public class Profile extends BaseModule {

    @Resource
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(Profile.class);

    public void execute(Context context) {
        try {
            if(!checkLogin("/profile.htm", context)) return;

            UserDTO userDTO = userBiz.queryById(getUserId(), context);
            context.put("userDTO", userDTO);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
