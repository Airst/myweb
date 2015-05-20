package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.constants.ContextConstants;
import com.ziqi.myweb.web.module.BaseModule;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: UploadAction
 * User: qige
 * Date: 15/4/19
 * Time: 12:15
 */
public class AppPublishAction extends BaseModule {

    @Resource
    private ParserRequestContext parse;

    @Resource
    private UserBiz userBiz;
    
    @Resource
    private ThreadBiz threadBiz;

    private static Logger logger = LoggerFactory.getLogger(AppPublishAction.class);

    public void execute(Context context) {
        try {
        	request.setCharacterEncoding("utf-8");
        	String userId = request.getParameter("userId");
        	String account = request.getParameter("account");
        	String title = request.getParameter("title");
        	String data = request.getParameter("content");
        	int filesize = Integer.parseInt(request.getParameter("filesize"));
            String content = "<!DOCTYPE html><html><head>" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/></head>" +
                    "<style> img { max-width: 730px; } </style>" +
                    "<body><p>";
            for(int i=1; i<=filesize; ++i) {
            	FileItem fileItem = parse.getParameters().getFileItem("userfile" + i);
            	String result = userBiz.uploadAppImage(fileItem, account, getFilesRoot());
            	if(result.equals("error"))
            		continue;
            	content += "<img src=\"" + result + "\" />";
            }
            content += data + "</p></body></html>";
            content = content.replace("<img", "\n<img");
            threadBiz.publishThread(title, content, getFilesRoot(), Integer.parseInt(userId), context);
            response.getWriter().write("upload ok");
        } catch (Exception e) {
            onException(context, logger, e);
        }

    }

}
