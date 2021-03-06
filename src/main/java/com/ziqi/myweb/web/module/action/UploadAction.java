package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.constants.ContextConstants;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: UploadAction 用于编辑器上传图片
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
                if(result.equals("error")) {
                    result = "<script>parent.callback('failed', '" + ErrorCode.ERR_WEB_0001 + "');</script>";
                } else {
                    result = "<script>parent.callback('success', '" + result + "');</script>";
                }

                response.getOutputStream().println(result);
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }

    }

}
