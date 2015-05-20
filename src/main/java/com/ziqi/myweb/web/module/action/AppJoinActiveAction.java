package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: JoinActiveAction
 * User: qige
 * Date: 15/5/8
 * Time: 13:59
 */
public class AppJoinActiveAction extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;

    private Logger logger = LoggerFactory.getLogger(AppJoinActiveAction.class);

    public void execute(Context context) {
        try {
            String id = request.getParameter("id");
            String userId = request.getParameter("userId");
            if(StringUtils.isBlank(id)) {
                response.sendRedirect(getHostUrl() + "/faceToFace.htm");
                return;
            }

            activeBiz.join(Integer.parseInt(userId), Integer.parseInt(id), context);
            response.getWriter().write("ok");
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
