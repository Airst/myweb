package com.ziqi.myweb.temp.script;

import com.ziqi.myweb.dal.model.ContentDO;
import com.ziqi.myweb.dal.model.ReplyDO;
import com.ziqi.myweb.dal.model.ThreadDO;
import com.ziqi.myweb.dal.model.UserDO;

/**
 * Description: SQLCreator
 * User: qige
 * Date: 15/4/13
 * Time: 12:56
 */
public class SQLCreator {


    public static void main(String[] args) throws Exception {
        Class[] classes = {ContentDO.class, ReplyDO.class, ThreadDO.class, UserDO.class};
        CreatorUtils.buildSQL(classes);
        CreatorUtils.buildInitSQL(classes);
    }

}
