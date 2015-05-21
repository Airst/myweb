package com.ziqi.myweb.web.module.screen.app;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.constants.ThreadConstants;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.BaseQuery;
import com.ziqi.myweb.common.query.ThreadQuery;
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
        	String userId = request.getParameter("userId");
        	String lastModified = request.getParameter("lastModified");
        	int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        	if(userId == null) {
	        	String hostURL = "http://" + request.getServerName() + ":" + request.getServerPort();
				context.put("hostURL", hostURL);
	        	if(lastModified != null) {
	        		Date date = new Date();    
	                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	                try {   
	                    date = sdf.parse(lastModified);  
	                } catch (Exception e) {   
	                    e.printStackTrace();   
	                }  
	        		List<ThreadDTO> threadDTOs = threadBiz.listUpdateThread(date, pageIndex, 10, context);
	        		if(threadDTOs != null) {
	        			context.put("threadDTOs", threadDTOs);
	        		}
	        		else {
	        			//do nothing
	        		}
	        	}
	        	else {
					List<ThreadDTO> threadDTOs = threadBiz.listThread(pageIndex, 10, true, true, context);
					context.put("threadDTOs", threadDTOs);
					logger.info(hostURL);
	        	}
        	}
        	else {
        		String hostURL = "http://" + request.getServerName() + ":" + request.getServerPort();
				context.put("hostURL", hostURL);
	        	if(lastModified != null) {
	        		Date date = new Date();    
	                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
	                try {   
	                    date = sdf.parse(lastModified);  
	                } catch (Exception e) {   
	                    e.printStackTrace();   
	                }  
	        		List<ThreadDTO> threadDTOs = threadBiz.listUpdateThreadByUserId(Integer.parseInt(userId), date, pageIndex, 10, context);
	        		if(threadDTOs != null) {
	        			context.put("threadDTOs", threadDTOs);
	        		}
	        		else {
	        			//do nothing
	        		}
	        	}
	        	else {
	        		List<ThreadDTO> threadDTOs = threadBiz.listThreadByUserId(Integer.parseInt(userId), pageIndex, 10, true, true, context);
					context.put("threadDTOs", threadDTOs);
	        	}
        	}
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
