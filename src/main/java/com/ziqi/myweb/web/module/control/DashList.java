package com.ziqi.myweb.web.module.control;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: DashList
 * User: qige
 * Date: 15/5/3
 * Time: 18:07
 */
public class DashList extends BaseModule {

    private Logger logger = LoggerFactory.getLogger(DashList.class);

    public void execute(Context context) {
        try {
            Integer userId = getUserId();
            String id = request.getParameter("userId");
            if(StringUtils.isNotBlank(id)) {
                userId = Integer.valueOf(id);
            }
            context.put("userId", userId);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
