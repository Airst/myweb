package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.service.form.CustomErrors;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.FormField;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.dal.model.UserDO;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.model.RegisterVO;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: RegisterAction
 * User: qige
 * Date: 15/4/15
 * Time: 16:55
 */
public class RegisterAction extends BaseModule {

    @Resource
    private UserBiz userBiz;

    private static Logger logger = LoggerFactory.getLogger(RegisterAction.class);

    public void execute(@FormGroup("register")RegisterVO registerVO, Context context,
            @FormField(name = "account", group = "register") CustomErrors err) {
        try {
            boolean duplicated = userBiz.isAccountDuplicated(registerVO, context);
            if(hasError(context)) {
                return;
            }
            //账号重复
            if(duplicated) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("account", registerVO.getAccount());
                err.setMessage("duplicatedAccount", params);
                return;
            }

            UserDTO userDTO = toUserDTO(registerVO);
            //插入数据，成功后跳转到登陆
            if (userBiz.save(userDTO, context)) {
                response.sendRedirect(request.getContextPath() + "/login.htm");
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

        return userDTO;
    }

}
