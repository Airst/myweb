package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.Date;

/**
 * Description: StartActiveAction
 * User: qige
 * Date: 15/5/7
 * Time: 14:52
 */
public class CreateActiveAction extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;

    private Logger logger = LoggerFactory.getLogger(CreateActiveAction.class);

    public void execute(Context context) {
        try {
            String topBeautyId = request.getParameter("userId");
            if(StringUtils.isBlank(topBeautyId)) {
                response.sendRedirect(getHostUrl() + "/topBeauty.htm");
                return;
            }
            if(!checkLogin("/topBeauty.htm", context)) return;

//            Integer activeId = activeBiz.startActive(getUserId(), Integer.parseInt(topBeautyId), context);
            response.sendRedirect(getHostUrl() + "/createActive.htm?userId=" + topBeautyId);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
