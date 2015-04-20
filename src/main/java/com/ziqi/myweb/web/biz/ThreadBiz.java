package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.query.ThreadQuery;
import com.ziqi.myweb.dal.model.ThreadDO;

import java.util.List;

/**
 * Description: ThreadBiz
 * User: qige
 * Date: 15/4/19
 * Time: 16:33
 */
public class ThreadBiz extends BaseBiz<ThreadDTO, ThreadDO> {

    public List<ThreadDTO> listThread(int pageIndex, int pageSize, Context context) {
        ThreadQuery threadQuery = new ThreadQuery();
        threadQuery.setPageIndex(pageIndex);
        threadQuery.setPageSize(pageSize);
        threadQuery.setOrderField(TableConstants.Base.gmtCreate);
        return query(threadQuery, context);
    }

}
