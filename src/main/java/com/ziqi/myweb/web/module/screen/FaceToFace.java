package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.module.BaseModule;
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

    private Logger logger = LoggerFactory.getLogger(FaceToFace.class);

    public void execute(Context context) {
        try {
            context.put("activeDTOs", activeBiz.listActive(context));
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
