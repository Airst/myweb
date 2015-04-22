package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ReplyDTO;
import com.ziqi.myweb.core.service.ReplyService;
import com.ziqi.myweb.dal.model.ReplyDO;

import java.io.File;
import java.util.Map;
import java.util.UUID;


/**
 * Description: ReplyBiz
 * User: qige
 * Date: 15/4/16
 * Time: 22:11
 */
public class ReplyBiz extends BaseBiz<ReplyDTO, ReplyDO> {

    public boolean publishReply(ReplyDTO replyDTO, String savePath, Context context) {
        return resultBoolean(((ReplyService) baseService).publishReply(replyDTO, savePath), context);
    }

}
