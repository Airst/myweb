package com.ziqi.myweb.web.module;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.constants.ContextConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Description: BaseAction
 * User: qige
 * Date: 15/4/16
 * Time: 23:01
 */
public class BaseModule {

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;

    @Resource
    protected HttpSession session;

    @Resource
    private UserBiz userBiz;

    public void onException(Context context, Logger logger, Exception e) {
        context.put(ContextConstants.ERROR_MSG, ErrorCode.ERR_WEB_0001);
        logger.error("failed@execute", e);
    }

    public boolean checkLogin(String url, Context context) throws Exception {
        Integer userId = getUserId();

        if(userId == null) {
            response.sendRedirect(getHostUrl() + "/login.htm?url=" + url);
            return false;
        }

        UserDTO userDTO = userBiz.queryById(userId, context);
        if(userDTO == null) {
            response.sendRedirect(getHostUrl() + "/login.htm?url=" + url);
            return false;
        }
        return true;
    }

    public String getHostUrl() {
        return request.getContextPath();
    }

    public Integer getUserId() {
        return (Integer) session.getAttribute(ContextConstants.USER_ID);
    }

    public String getAccount() {
        return (String) session.getAttribute(ContextConstants.ACCOUNT);
    }

    public void logout() {
        session.removeAttribute(ContextConstants.USER_ID);
        session.removeAttribute(ContextConstants.ACCOUNT);
    }

    public void flushUserId(Context context) {
        Integer userId = getUserId();
        String account = getAccount();

        if(userId != null) {
            context.put(ContextConstants.USER_ID, userId);
        }
        if(StringUtils.isNotBlank(account)) {
            context.put(ContextConstants.ACCOUNT, account);
        }
    }

    public boolean hasError(Context context) {
        return context.containsKey(ContextConstants.ERROR_MSG);
    }

    public String getServerRoot() {
        return request.getServletContext().getRealPath("/");
    }

    public String getFilesRoot() {
        File file = new File(request.getServletContext().getRealPath("/")).getParentFile();
        return file.getPath();
    }

    public String buildSavePath(String dir) {

        String parentPath = getFilesRoot();
        File dirFile = new File(parentPath + "/" + dir);
        if(!dirFile.exists() && !dirFile.mkdirs()) {
            return null;
        }
        String filename = UUID.randomUUID() + "_" + getUserId() + ".html";
        return parentPath + "/" + dir + "/" + filename;
    }

    public String buildContentPath(String savePath) {
        return savePath.replace(getFilesRoot(), "");
    }

}
