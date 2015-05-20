package com.ziqi.myweb.web.module.action;

import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.biz.MessageBiz;
import com.ziqi.myweb.web.module.BaseModule;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: ConfirmActiveAction
 * User: qige
 * Date: 15/5/8
 * Time: 11:55
 */
public class AppStartActiveAction extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;

    @Resource
    private MessageBiz messageBiz;

    private Logger logger = LoggerFactory.getLogger(CreateActiveAction.class);

    public void execute(Context context) {
        try {
        	String userId = request.getParameter("userId");
        	String account = request.getParameter("account");
            String topBeautyId = request.getParameter("topBeauty");
            if(StringUtils.isBlank(topBeautyId)) {
                response.sendRedirect(getHostUrl() + "/topBeauty.htm");
                return;
            }

            String startDate = request.getParameter("startDate");
            if(StringUtils.isBlank(startDate)) {
                return;
            }
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);

            String address = request.getParameter("address");
            String description = request.getParameter("description");
            final Integer activeId = activeBiz.startActive(Integer.parseInt(userId), Integer.parseInt(topBeautyId), date, address, description, context);

            String content = "用户" + account + "向您发出了面对面活动邀请! " + "点击查看详情";
            String url = "/activeDetail.htm?id=" + activeId;
            messageBiz.sendNotification(Integer.parseInt(topBeautyId), content, url, context);
            Map<String, String> extras = new HashMap<String, String>(){{
            	put("activeId", activeId.toString());
            }};
            AppSendMessageAction.sendMessage(content, "系统通知", extras, topBeautyId);
            response.getWriter().write("ok");
            //response.sendRedirect(getHostUrl() + "/activeDetail.htm?id=" + activeId);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
