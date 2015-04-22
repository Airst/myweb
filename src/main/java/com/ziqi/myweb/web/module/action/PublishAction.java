package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.FormField;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: PublishAction
 * User: qige
 * Date: 15/4/19
 * Time: 12:30
 */
public class PublishAction extends BaseModule {

    @Resource
    private ThreadBiz threadBiz;

    private Logger logger = LoggerFactory.getLogger(PublishAction.class);

    public void execute(@FormField(name = "title", group = "publish") String title,
                        @FormField(name = "content", group = "publish") String data, Context context) {
        try {
            request.setCharacterEncoding("utf-8");
            String content = "<!DOCTYPE html><html><head>" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head>" +
                    "<style> img { max-width: 730px; } </style>" +
                    "<body>" + data + "</body></html>";
            content = content.replace("<img", "\n<img");
            threadBiz.publishThread(title, content, getFilesRoot(), getUserId(), context);
            response.sendRedirect(request.getContextPath() + "beautyStreet.htm");
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
