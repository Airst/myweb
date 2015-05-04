package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.constants.ContextConstants;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: UserImageAction
 * User: qige
 * Date: 15/5/4
 * Time: 12:07
 */
public class UserImageAction extends BaseModule {

    @Resource
    private ParserRequestContext parse;

    @Resource
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(UserImageAction.class);

    public void execute(Context context) {
        try {
            FileItem fileItem = parse.getParameters().getFileItem("userfile");
            if (fileItem != null) {
                String account = (String) session.getAttribute(ContextConstants.ACCOUNT);
                String result = userBiz.uploadImage(fileItem, account, getFilesRoot());
                if(!result.equals("error")) {
                    UserDTO userDTO = userBiz.queryById(getUserId(), context);
                    userDTO.setImagePath(result);
                    userBiz.update(userDTO, context);
                }
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
