package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: Thread
 * User: qige
 * Date: 15/4/19
 * Time: 23:40
 */
public class Thread extends BaseModule {

    @Resource
    private ThreadBiz threadBiz;

    private Logger logger = LoggerFactory.getLogger(Thread.class);

    public void execute(Context context) {
        try {
            String id = request.getParameter("id");
            if(StringUtils.isBlank(id)) {
                response.sendRedirect(getHostUrl() + "/beautyStreet.htm");
            } else {
                ThreadDTO threadDTO = threadBiz.queryById(Integer.parseInt(id), context);
                context.put("threadDTO", threadDTO);
            }
            defaultExecute(context);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
