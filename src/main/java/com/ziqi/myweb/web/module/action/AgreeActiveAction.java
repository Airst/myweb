package com.ziqi.myweb.web.module.action;

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
 * Description: AgreeActiveAction
 * User: qige
 * Date: 15/5/8
 * Time: 15:08
 */
public class AgreeActiveAction extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;

    private Logger logger = LoggerFactory.getLogger(AgreeActiveAction.class);

    public void execute(Context context) {
        try {
            String id = request.getParameter("id");
            if(StringUtils.isBlank(id)) {
                response.sendRedirect(getHostUrl() + "/faceToFace.htm");
                return;
            }

            if(!checkLogin("/activeDetail.htm?id=" + id, context)) return;
            ActiveDTO activeDTO = activeBiz.queryById(Integer.parseInt(id), context);
            if(activeDTO.getTopBeautyId().equals(getUserId())) {
                activeDTO.setStatus(ActiveConstants.Status.AGREED);
                activeBiz.update(activeDTO, context);
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
