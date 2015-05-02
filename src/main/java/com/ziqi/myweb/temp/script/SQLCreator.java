package com.ziqi.myweb.temp.script;

import com.ziqi.myweb.dal.model.*;

/**
 * Description: SQLCreator
 * User: qige
 * Date: 15/4/13
 * Time: 12:56
 */
public class SQLCreator {


    public static void main(String[] args) throws Exception {
        Class[] classes = {MessageDO.class};
        CreatorUtils.buildSQL(classes);
//        CreatorUtils.buildInitSQL(classes);
    }

}
