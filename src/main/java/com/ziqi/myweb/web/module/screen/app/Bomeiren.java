package com.ziqi.myweb.web.module.screen.app;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: TopBeauty
 * User: qige
 * Date: 15/5/2
 * Time: 16:11
 */
public class Bomeiren extends BaseModule {

    @Resource
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(Bomeiren.class);

    public void execute(Context context) {
        try {
            int pageIndex = 1;
            String page = request.getParameter("pageIndex");
            if(StringUtils.isNotBlank(page)) {
                pageIndex = Integer.parseInt(page);
            }
            List<UserDTO> userDTOs = userBiz.listTopBeauty(pageIndex, context);

            context.put("userDTOs", userDTOs);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
