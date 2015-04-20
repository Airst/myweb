package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.model.BaseDTO;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.query.BaseQuery;
import com.ziqi.myweb.core.service.BaseService;
import com.ziqi.myweb.dal.model.BaseDO;
import com.ziqi.myweb.web.constants.ContextConstants;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: IBaseBiz
 * User: qige
 * Date: 15/4/16
 * Time: 22:12
 */
public class BaseBiz<DTO extends BaseDTO, DO extends BaseDO> {

    protected BaseService<DTO, DO> baseService;

    public void setBaseService(BaseService<DTO, DO> baseService) {
        this.baseService = baseService;
    }

    public <T> boolean resultBoolean(ResultDTO<T> resultDTO, Context context) {
        if (!resultDTO.isSuccess()) {
            context.put(ContextConstants.ERROR_MSG, ErrorCode.ERR_WEB_0001);
            return false;
        }
        return true;
    }

    public boolean save(DTO t, Context context) {
        return resultBoolean(baseService.saveBasic(t), context);
    }

    public boolean update(DTO t, Context context) {
        return resultBoolean(baseService.update(t), context);
    }

    public boolean delete(int id, Context context) {
        return resultBoolean(baseService.delete(id), context);
    }

    public DTO queryById(int id, Context context) {
        ResultDTO<DTO> resultDTO = baseService.queryById(id);
        if (!resultDTO.isSuccess()) {
            context.put(ContextConstants.ERROR_MSG, ErrorCode.ERR_WEB_0001);
            return null;
        }
        return resultDTO.getResult();
    }

    public <E extends BaseQuery> DTO queryOne(E query, Context context) {
        ResultDTO<List<DTO>> resultDTO = baseService.query(query);
        if (!resultDTO.isSuccess()) {
            context.put(ContextConstants.ERROR_MSG, ErrorCode.ERR_WEB_0001);
            return null;
        }
        if(CollectionUtils.isNotEmpty(resultDTO.getResult()))
            return resultDTO.getResult().get(0);
        return null;
    }

    public <E extends BaseQuery> List<DTO> query(E query, Context context) {
        ResultDTO<List<DTO>> resultDTO = baseService.query(query);
        if (!resultDTO.isSuccess()) {
            context.put(ContextConstants.ERROR_MSG, ErrorCode.ERR_WEB_0001);
            return new ArrayList<DTO>();
        }

        context.put("pageIndex", resultDTO.getPageIndex());
        context.put("totalPage", resultDTO.getTotalPage());
        context.put("pageSize", resultDTO.getPageSize());
        return resultDTO.getResult();
    }

}
