package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: BeautyStreet
 * User: qige
 * Date: 15/4/19
 * Time: 00:12
 */
public class BeautyStreet extends BaseModule {

    @Resource
    private ThreadBiz threadBiz;

    private Logger logger = LoggerFactory.getLogger(BeautyStreet.class);

    public void execute(Context context) {
        try {
            String pageIndex = request.getParameter("pageIndex");
            if(StringUtils.isBlank(pageIndex)) {
                pageIndex = "1";
            }
            List<ThreadDTO> threadDTOs = threadBiz.listThread(Integer.parseInt(pageIndex), 20, true, false, context);
            context.put("threadDTOs", threadDTOs);

            List<ThreadDTO> topThreads = threadBiz.listTopThread(1, 5, context);
            context.put("topThreads", topThreads);
            defaultExecute(context);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
