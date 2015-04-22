package com.ziqi.myweb.core.service;

import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.exception.MyException;
import com.ziqi.myweb.common.model.BaseDTO;
import com.ziqi.myweb.common.model.ReplyDTO;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.query.BaseQuery;
import com.ziqi.myweb.dal.model.BaseDO;
import com.ziqi.myweb.dal.model.ReplyDO;
import com.ziqi.myweb.dal.query.QueryMap;
import com.ziqi.myweb.dal.dao.BaseDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: BaseService
 * User: qige
 * Date: 15/4/11
 * Time: 01:28
 */
public abstract class BaseService<DTO extends BaseDTO, DO extends BaseDO> {

    protected BaseDAO<DO> baseDAO;

    private Logger logger = LoggerFactory.getLogger(BaseService.class);

    public void setBaseDAO(BaseDAO<DO> baseDAO) {
        this.baseDAO = baseDAO;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }

    public abstract DTO DOToDTO(DO baseDO);
    public abstract DO DTOToDO(DTO baseDO);

    public List<DO> DTOsToDOs(List<DTO> baseDTOs) {
        List<DO> baseDOs = new ArrayList<DO>();
        for(DTO baseDTO : baseDTOs) {
            baseDOs.add(DTOToDO(baseDTO));
        }
        return baseDOs;
    }

    public List<DTO> DOsToDTOs(List<DO> baseDOs) {
        List<DTO> baseDTOs = new ArrayList<DTO>();
        for(DO baseDO : baseDOs) {
            baseDTOs.add(DOToDTO(baseDO));
        }
        return baseDTOs;
    }

    @SuppressWarnings("unchecked")
    public ResultDTO<Integer> afterTransaction(Object result) {
        if(result instanceof ResultDTO) {
            return (ResultDTO<Integer>) result;
        } else if(result instanceof MyException) {
            return new ResultDTO<Integer>()
                    .setErrorCode(((MyException) result).getErrorCode())
                    .setErrorMessage(((MyException) result).getErrorMessage());
        } else {
            return new ResultDTO<Integer>();
        }
    }

    public static <T extends BaseQuery> QueryMap QueryToMap(T query) throws Exception {
        if(query == null) {
            return null;
        }
        QueryMap queryMap = new QueryMap();
        queryMap.put(QueryMap.start, query.getStart());
        queryMap.put(QueryMap.limit, query.getLimit());

        Class queryType = query.getClass();
        for(Field field : queryType.getDeclaredFields()) {
            field.setAccessible(true);
            queryMap.put(field.getName(), field.get(query));
        }
        Class superType = queryType.getSuperclass();
        for(Field field : superType.getDeclaredFields()) {
            field.setAccessible(true);
            queryMap.put(field.getName(), field.get(query));
        }
        return queryMap;
    }

    public <E extends BaseQuery> ResultDTO<List<DTO>> query(E query) {
        ResultDTO<List<DTO>> resultDTO = new ResultDTO<List<DTO>>();
        try {
            if(query == null) {
                throw new MyException(ErrorCode.ERR_AC_0001, "query==null");
            }
            QueryMap queryMap = QueryToMap(query);
            resultDTO.setResult(DOsToDTOs(baseDAO.select(queryMap)));
            resultDTO.setStartLimitSize(query.getStart(), query.getLimit(), baseDAO.count(queryMap));
            resultDTO.setIsSuccess(true);
        } catch (MyException e) {
            onMyException(e, "query", resultDTO, query);
        } catch (Exception e) {
            onException(e, "query", resultDTO, query);
        }
        return resultDTO;
    }

    public ResultDTO<DTO> queryById(int id) {
        ResultDTO<DTO> resultDTO = new ResultDTO<DTO>();
        try {
            if(id <= 0) {
                throw new MyException(ErrorCode.ERR_AC_0002, "id <= 0");
            }
            resultDTO.setResult(DOToDTO(baseDAO.selectById(id)));
            resultDTO.setIsSuccess(true);
        } catch (MyException e) {
            onMyException(e, "queryById", resultDTO, id);
        } catch (Exception e) {
            onException(e, "queryById", resultDTO, id);
        }
        return resultDTO;
    }

    public ResultDTO<Integer> saveBasic(DTO t) {
        ResultDTO<Integer> resultDTO = new ResultDTO<Integer>();
        try {
            if(t == null) {
                throw new MyException(ErrorCode.ERR_AC_0001, "t==null");
            }
            resultDTO.setResult(baseDAO.insert(DTOToDO(t)));
            resultDTO.setIsSuccess(true);
        } catch (MyException e) {
            onMyException(e, "saveBasic", resultDTO, t);
        } catch (Exception e) {
            onException(e, "saveBasic", resultDTO, t);
        }
        return resultDTO;
    }

    public ResultDTO<Integer> delete(int id) {
        ResultDTO<Integer> resultDTO = new ResultDTO<Integer>();
        try {
            if(id <= 0) {
                throw new MyException(ErrorCode.ERR_AC_0002, "id <= 0");
            }
            resultDTO.setResult(baseDAO.delete(id));
            resultDTO.setIsSuccess(true);
        } catch (MyException e) {
            onMyException(e, "delete", resultDTO, id);
        } catch (Exception e) {
            onException(e, "delete", resultDTO, id);
        }
        return resultDTO;
    }

    public ResultDTO<Integer> update(DTO t) {
        ResultDTO<Integer> resultDTO = new ResultDTO<Integer>();
        try {
            if(t == null) {
                throw new MyException(ErrorCode.ERR_AC_0001, "t==null");
            }
            resultDTO.setResult(baseDAO.update(DTOToDO(t)));
            resultDTO.setIsSuccess(true);
        } catch (MyException e) {
            onMyException(e, "update", resultDTO, t);
        } catch (Exception e) {
            onException(e, "update", resultDTO, t);
        }
        return resultDTO;
    }

    protected boolean saveContent(String content, String savePath) throws Exception {
        File contentFile = new File(savePath);
        if(contentFile.exists() || contentFile.createNewFile()) {
            FileOutputStream outputStream = new FileOutputStream(contentFile);
            outputStream.write(content.getBytes("utf-8"));
            outputStream.close();
            return true;
        }
        return false;
    }

    protected void onMyException(MyException e, String name, ResultDTO resultDTO, Object... args) {
        logger.error("failed@{}, errorCode={}, errorMessage={}, args={}", name, e.getErrorCode(), e.getErrorMessage(), args, e);
        resultDTO.setErrorCode(e.getErrorCode());
        resultDTO.setErrorMessage(e.getErrorMessage());
    }

    protected void onException(Exception e, String name, ResultDTO resultDTO, Object... args) {
        logger.error("failed@{}, errorCode={}, args={}", name, ErrorCode.ERR_UNKNOWN, args, e);
        resultDTO.setErrorCode(ErrorCode.ERR_UNKNOWN);
        resultDTO.setErrorMessage("");
    }

}
