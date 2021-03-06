package com.ziqi.myweb.web.module.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.UserQuery;
import com.ziqi.myweb.common.utils.MD5Utils;
import com.ziqi.myweb.web.biz.MessageBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.constants.ContextConstants;
import com.ziqi.myweb.web.module.BaseModule;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: LoginAction
 * User: qige
 * Date: 15/4/15
 * Time: 15:26
 */
public class AppLoginAction extends BaseModule {

    @Resource
    private UserBiz userBiz;
    
    @Resource
    private MessageBiz messageBiz;
    
    public static Map<Integer, String> registrationMap = new HashMap<Integer, String>();

    private static Logger logger = LoggerFactory.getLogger(AppLoginAction.class);

    public void execute(Context context) {
        try {
        	String account = request.getParameter("account");
            String password = request.getParameter("password");
            String registrationId = request.getParameter("registrationId");

            Integer tryUserId = checkLogin(account, password, context);
            if(tryUserId != null) {
                session.setAttribute(ContextConstants.USER_ID, tryUserId);
                session.setAttribute(ContextConstants.ACCOUNT, account);

                registrationMap.put(tryUserId, registrationId);
                UserDTO userDTO = userBiz.queryById(tryUserId, context);
                Integer unreadSixin = messageBiz.unreadMessages(tryUserId, context);
                Integer unreadSysMsg = messageBiz.unreadNotifications(tryUserId, context);
                response.getWriter().write(userDTO.getId() + ":" + userDTO.getImagePath() + ":" + userDTO.getLevel() +
                		":" + userDTO.getName() + ":" + userDTO.getPhone() + ":" + userDTO.getEmail() + ":" +
                		unreadSixin + ":" +unreadSysMsg);
                 
                List<AppSendMessageAction.PushParam> pushParams = new ArrayList<AppSendMessageAction.PushParam>();
                for(AppSendMessageAction.PushParam pushParam : AppSendMessageAction.unreadPush) {
                	if(tryUserId.equals(Integer.parseInt(pushParam.toUserId))) {
                		AppSendMessageAction.sendMessage(pushParam);
                		pushParams.add(pushParam);
                	}
                }
                boolean isRemove = AppSendMessageAction.unreadPush.removeAll(pushParams);
        		logger.info("remove"+isRemove);
                
                logger.info("login ok");
                logger.info(registrationId);
            }
            else {
                response.getWriter().write("error");
            }

        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

    private Integer checkLogin(String account, String password, Context context) {
        UserQuery query = new UserQuery();
        query.setAccount(account);
        query.setPassword(MD5Utils.MD5(password));
        query.setPageSize(1);
        UserDTO userDTO = userBiz.queryOne(query, context);
        if(userDTO == null) {
            if(!hasError(context)) {
                context.put(ContextConstants.ERROR_MSG, ErrorCode.ERR_WEB_0002);
            }
            return null;
        }
        return userDTO.getId();
    }

}
