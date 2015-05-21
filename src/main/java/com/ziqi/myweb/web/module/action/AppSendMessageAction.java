package com.ziqi.myweb.web.module.action;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.web.biz.MessageBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: SendMessageAction
 * User: qige
 * Date: 15/4/30
 * Time: 12:03
 */
public class AppSendMessageAction extends BaseModule {

    @Resource
    private MessageBiz messageBiz;
    
    @Resource
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(AppSendMessageAction.class);

    public void execute(Context context) {
        try {
            String content = request.getParameter("content");
            String toUserId = request.getParameter("toUserId");
            String fromUserId = request.getParameter("fromUserId");
            
            UserDTO fromUserDTO = null;
            if(StringUtils.isBlank(toUserId)) {
                return;
            }
            fromUserDTO = userBiz.queryById(Integer.parseInt(fromUserId), context);

            messageBiz.sendMessage(Integer.parseInt(toUserId), Integer.parseInt(fromUserId), content, context);
            //response.sendRedirect(getHostUrl() + "/messageDetail.htm?toUserId=" + toUserId);
         // 这是在极光网站上申请的密钥
            String masterSecret = "0d176a98edc09fe6449830d1";
            // 应用的appKey,同样在网站上申请
            String appKey = "39492286f624d296c8edb8d1";
            // 建立JpushClient类，用来发送消息的对象
            JPushClient client = new JPushClient(masterSecret, appKey);
            logger.info(Integer.parseInt(toUserId) + "," + AppLoginAction.registrationMap.get(Integer.parseInt(toUserId)));
            try {
            	// client.sendNotificationAll("hello world");

            	// client.sendMessageAll("this is a message");
            	Map<String, String> extras = userDTOToMap(fromUserDTO);
            	PushPayload payload = PushPayload
            			.newBuilder()
            			.setPlatform(Platform.android())
            			.setNotification(Notification.android(content, fromUserDTO.getAccount(), extras))
            			.setAudience(Audience.registrationId(AppLoginAction.registrationMap.get(Integer.parseInt(toUserId))))
                       .build();
            			client.sendPush(payload);

            		} catch (APIConnectionException e) {
            			e.printStackTrace();
            		} catch (APIRequestException e) {
            			e.printStackTrace();
            		}

        } catch(Exception e) {
            onException(context, logger, e);
        }
    }
    
    private Map<String, String> userDTOToMap(UserDTO userDTO) {
    	Map<String, String> extras = new HashMap<String, String>();
    	extras.put("id", userDTO.getId()+"");
    	extras.put("userimgurl", userDTO.getImagePath());
    	extras.put("username", userDTO.getAccount());
    	extras.put("level", userDTO.getLevel()+"");
    	return extras;
    }
    
    public static void sendMessage(String content, String title, Map<String, String> extras, String toUserId) {
    	String masterSecret = "0d176a98edc09fe6449830d1";
        // 应用的appKey,同样在网站上申请
        String appKey = "39492286f624d296c8edb8d1";
        // 建立JpushClient类，用来发送消息的对象
        JPushClient client = new JPushClient(masterSecret, appKey);
        try {
        	// client.sendNotificationAll("hello world");

        	// client.sendMessageAll("this is a message");
        	if(AppLoginAction.registrationMap.containsKey(Integer.parseInt(toUserId))) {
	        	PushPayload payload = PushPayload
	        			.newBuilder()
	        			.setPlatform(Platform.android())
	        			.setNotification(Notification.android(content, title, extras))
	        			.setAudience(Audience.registrationId(AppLoginAction.registrationMap.get(Integer.parseInt(toUserId))))
	                   .build();
	        			client.sendPush(payload);
        	}

        		} catch (APIConnectionException e) {
        			e.printStackTrace();
        		} catch (APIRequestException e) {
        			e.printStackTrace();
        		}
    }
}
