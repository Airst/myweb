package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
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
public class UploadAction extends BaseModule {

    @Resource
    private ParserRequestContext parse;

    @Resource
    private UserBiz userBiz;

    private static Logger logger = LoggerFactory.getLogger(UploadAction.class);

    public void execute(Context context) {
        try {
            FileItem fileItem = parse.getParameters().getFileItem("userfile");
            if (fileItem != null) {
                String account = (String) session.getAttribute(ContextConstants.ACCOUNT);
                String result = userBiz.uploadImage(fileItem, account, getFilesRoot());

                response.getOutputStream().println(result);
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }

    }

}
