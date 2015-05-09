package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ActiveDTO;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.module.BaseModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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
            List<List<ActiveDTO>> activesList = new ArrayList<List<ActiveDTO>>();
            activesList.add(activeBiz.listActiveAsOwner(getUserId(), context));
            activesList.add(activeBiz.listActiveAsBeauty(getUserId(), context));
            activesList.add(activeBiz.listActiveAsActor(getUserId(), context));

            context.put("activesList", activesList);
            context.put("activesTitle", Arrays.asList("我发起的", "作为播美人的", "我参加的"));

        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
