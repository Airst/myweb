package com.ziqi.myweb.web.module.screen.app;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ActiveDTO;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.biz.ThreadBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: Index
 * User: qige
 * Date: 15/4/17
 * Time: 00:58
 */
public class UserIndex extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;

    @Resource
    private ThreadBiz threadBiz;

    @Resource
    private UserBiz userBiz;

    public Logger logger = LoggerFactory.getLogger(UserIndex.class);

    public void execute(Context context) {
        try {
            List<UserDTO> userDTOs = userBiz.listTopUser(context);
            if(userDTOs != null) {
                int size = 4;
                if(userDTOs.size() < 4) size = userDTOs.size();
                context.put("userDTOs", userDTOs.subList(0, size));
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }

}
