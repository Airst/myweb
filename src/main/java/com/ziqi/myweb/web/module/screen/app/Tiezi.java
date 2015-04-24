package com.ziqi.myweb.web.module.screen.app;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.BaseQuery;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: Index
 * User: qige
 * Date: 15/4/17
 * Time: 00:58
 */
public class Tiezi extends BaseModule {

	@Resource
	private ThreadBiz threadBiz;
	
	@Resource
	private UserBiz userBiz;

    public Logger logger = LoggerFactory.getLogger(Tiezi.class);

    public void execute(Context context) {
        try {
            defaultExecute(context);
            int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
			List<ThreadDTO> threadDTOs = threadBiz.listThread(pageIndex, 10, true, true, context);
			context.put("threadDTOs", threadDTOs);
			List<UserDTO> userDTOs = new ArrayList<UserDTO>();
			for(ThreadDTO threadDTO : threadDTOs) {
				userDTOs.add(userBiz.queryById(threadDTO.getAuthorId(), context));
			}
			context.put("userDTOs", userDTOs);
			String hostURL = "http://" + request.getServerName() + ":" + request.getServerPort();
			logger.info(hostURL);
			context.put("hostURL", hostURL);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
