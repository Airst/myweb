package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.UserQuery;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.biz.UserBiz;
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

    @Resource
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(BeautyStreet.class);

    public void execute(Context context) {
        try {
            String pageIndex = request.getParameter("pageIndex");
            if(StringUtils.isBlank(pageIndex)) {
                pageIndex = "1";
            }
            String key = request.getParameter("key");
            if(StringUtils.isNotBlank(key)) {
                UserQuery query = new UserQuery();
                query.setAccount(key);
                UserDTO userDTO = userBiz.queryOne(query, context);
                if(userDTO == null) {//根据用户名搜索
                    List<ThreadDTO> threadDTOs = threadBiz.searchThreads(Integer.parseInt(pageIndex), 20, key, context);
                    context.put("threadDTOs", threadDTOs);
                } else {
                    List<ThreadDTO> threadDTOs = threadBiz.listUserThread(userDTO.getId(), context);
                    context.put("threadDTOs", threadDTOs);
                }
                context.put("key", key);
            } else {
                List<ThreadDTO> threadDTOs = threadBiz.listThread(Integer.parseInt(pageIndex), 20, true, false, context);
                context.put("threadDTOs", threadDTOs);

                List<ThreadDTO> topThreads = threadBiz.listTopThread(1, 5, context);
                context.put("topThreads", topThreads);
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
