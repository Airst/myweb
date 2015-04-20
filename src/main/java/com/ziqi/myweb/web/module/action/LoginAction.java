package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.UserQuery;
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
public class LoginAction extends BaseModule {

    @Resource
    private UserBiz userBiz;

    private static Logger logger = LoggerFactory.getLogger(LoginAction.class);

    public void execute(Context context) {
        try {
            String account = request.getParameter("account");
            String password = request.getParameter("password");

            Integer tryUserId = checkLogin(account, password, context);
            if(tryUserId != null) {
                session.setAttribute(ContextConstants.USER_ID, tryUserId);
                session.setAttribute(ContextConstants.ACCOUNT, account);

                String url;
                if(StringUtils.isNotBlank(request.getParameter("url"))) {
                    url = request.getParameter("url");
                } else {
                    url = getHostUrl() + "/index.htm";
                }
                response.sendRedirect(url);
            }

        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

    private Integer checkLogin(String account, String password, Context context) {
        UserQuery query = new UserQuery();
        query.setAccount(account);
        query.setPassword(password);
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
