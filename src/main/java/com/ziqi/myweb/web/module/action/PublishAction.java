package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.turbine.Context;
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
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(PublishAction.class);

    public void execute(Context context) {
        try {
            String content = "<!DOCTYPE html><html><head>" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head><body>" +
                    request.getParameter("content") + "</body></html>";
            String title = request.getParameter("title");
            userBiz.publishThread(title, content.replace(">", ">\n"), getFilesRoot(), getUserId(), context);
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
