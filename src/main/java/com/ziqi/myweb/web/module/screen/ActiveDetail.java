package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ActiveConstants;
import com.ziqi.myweb.common.model.ActiveDTO;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: ActiveDetail
 * User: qige
 * Date: 15/5/8
 * Time: 10:10
 */
public class ActiveDetail extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;

    private Logger logger = LoggerFactory.getLogger(ActiveDetail.class);

    public void execute(Context context) {
        try {
            String id = request.getParameter("id");
            if(StringUtils.isBlank(id)) {
                response.sendRedirect(getHostUrl() + "/faceToFace.htm");
                return;
            }
            ActiveDTO activeDTO = activeBiz.queryById(Integer.parseInt(id), context);
            context.put("activeDTO", activeDTO);
            context.put("agreed", activeDTO.getStatus().equals(ActiveConstants.Status.AGREED));

            if(activeDTO.getTopBeautyId().equals(getUserId())) {
                context.put("isTopBeauty", true);
                context.put("needConfirm", activeDTO.getStatus().equals(ActiveConstants.Status.NEW));
            } else {
                context.put("isTopBeauty", false);
                if(activeDTO.getOwnerId().equals(getUserId())) {
                    context.put("joined", true);
                } else {
                    context.put("joined", activeBiz.isJoined(getUserId(), activeDTO.getId(), context));
                }
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
