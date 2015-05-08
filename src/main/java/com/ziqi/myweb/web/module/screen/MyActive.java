package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ActiveDTO;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: MyActive
 * User: qige
 * Date: 15/5/8
 * Time: 15:54
 */
public class MyActive extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;

    private Logger logger = LoggerFactory.getLogger(MyActive.class);

    public void execute(Context context) {
        try {
            if(!checkLogin("/myActive.htm", context)) return;

            context.put("ownActives", activeBiz.listActiveAsOwner(getUserId(), context));
            context.put("beautyActives", activeBiz.listActiveAsBeauty(getUserId(), context));
            context.put("actorActives", activeBiz.listActiveAsActor(getUserId(), context));
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
