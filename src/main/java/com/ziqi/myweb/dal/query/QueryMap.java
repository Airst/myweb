package com.ziqi.myweb.dal.query;

import com.ziqi.myweb.common.constants.QueryConstants;

import java.util.HashMap;

/**
 * Description: QueryMap
 * User: qige
 * Date: 15/4/12
 * Time: 18:09
 */
public class QueryMap extends HashMap<String, Object> {

    public QueryMap() {
        this.put(QueryConstants.Base.start, 1);
        this.put(QueryConstants.Base.limit, 20);
    }

}
