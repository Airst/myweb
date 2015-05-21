package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.service.form.CustomErrors;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.FormField;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.model.RegisterVO;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: RegisterAction
 * User: qige
 * Date: 15/4/15
 * Time: 16:55
 */
public class AppRegisterAction extends BaseModule {

    @Resource
    private UserBiz userBiz;

    private static Logger logger = LoggerFactory.getLogger(AppRegisterAction.class);

    public void execute(Context context) {
        try {
        	String account = request.getParameter("account");
        	String password = request.getParameter("password");
        	String rePassword = request.getParameter("rePassword");
        	String name = request.getParameter("name");
        	String phoneNum = request.getParameter("phoneNum");
        	String email = request.getParameter("email");
        	RegisterVO registerVO = new RegisterVO();
        	registerVO.setAccount(account);
        	registerVO.setPassword(password);
        	registerVO.setRePassword(rePassword);
        	registerVO.setName(name);
        	registerVO.setPhone(phoneNum);
        	registerVO.setEmail(email);
            boolean duplicated = userBiz.isAccountDuplicated(registerVO, context);
            if(hasError(context)) {
                return;
            }
            //账号重复
            if(duplicated) {
            	response.getWriter().write("duplicated");
                return;
            }

            UserDTO userDTO = toUserDTO(registerVO);
            //插入数据，成功后跳转到登陆
            if (userBiz.save(userDTO, context)) {
                response.getWriter().write("ok");
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

    public UserDTO toUserDTO(RegisterVO registerVO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setAccount(registerVO.getAccount());
        userDTO.setEmail(registerVO.getEmail());
        userDTO.setName(registerVO.getName());
        userDTO.setPassword(registerVO.getPassword());
        userDTO.setPhone(registerVO.getPhone());
        userDTO.setLevel(0);
        return userDTO;
    }

}
