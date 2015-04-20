package com.ziqi.myweb.dal.query;

import java.util.HashMap;

/**
 * Description: QueryMap
 * User: qige
 * Date: 15/4/12
 * Time: 18:09
 */
public class QueryMap extends HashMap<String, Object> {

    public static String start = "start";
    public static String limit = "limit";

    public QueryMap() {
        this.put(start, 1);
        this.put(limit, 20);
    }

}
