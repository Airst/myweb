package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.UserQuery;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: faceToFace
 * User: qige
 * Date: 15/5/8
 * Time: 13:39
 */
public class FaceToFace extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;

    @Resource
    private UserBiz userBiz;

    private Logger logger = LoggerFactory.getLogger(FaceToFace.class);

    public void execute(Context context) {
        try {

            String key = request.getParameter("key");
            if(StringUtils.isNotBlank(key)) {
                String type = request.getParameter("option");
                UserQuery query = new UserQuery();
                query.setAccount(key);
                UserDTO userDTO = userBiz.queryOne(query, context);
                if(userDTO != null) {
                    if (type.equals("0")) {
                        context.put("activeDTOs", activeBiz.listActiveAsBeauty(userDTO.getId(), context));
                    } else {
                        context.put("activeDTOs", activeBiz.listActiveAsOwner(userDTO.getId(), context));
                    }

                }
                context.put("key", key);
            } else {
                context.put("activeDTOs", activeBiz.listActive(context));
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
