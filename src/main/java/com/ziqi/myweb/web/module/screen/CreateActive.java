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
 * Description: EditActive
 * User: qige
 * Date: 15/5/8
 * Time: 10:14
 */
public class CreateActive extends BaseModule {

    @Resource
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(CreateActive.class);

    public void execute(Context context) {
        try {
            String topBeautyId = request.getParameter("userId");
            if(StringUtils.isBlank(topBeautyId)) {
                response.sendRedirect(getHostUrl() + "/topBeauty.htm");
                return;
            }
            if(!checkLogin("/topBeauty.htm", context)) return;
            UserDTO topBeauty = userBiz.queryById(Integer.parseInt(topBeautyId), context);
            UserDTO userDTO = userBiz.queryById(getUserId(), context);

            context.put("topBeauty", topBeauty);
            context.put("userDTO", userDTO);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
