package com.ziqi.myweb.temp.script;

import com.ziqi.myweb.dal.model.ContentDO;
import com.ziqi.myweb.dal.model.ReplyDO;
import com.ziqi.myweb.dal.model.ThreadDO;
import com.ziqi.myweb.dal.model.UserDO;

/**
 * Description: ClassCreator
 * User: qige
 * Date: 15/4/13
 * Time: 12:57
 */
public class ClassCreator {

    public static void main(String[] args) throws Exception {
        Class[] classes = {ContentDO.class, ReplyDO.class, ThreadDO.class, UserDO.class};
        CreatorUtils.buildQueryClass(classes);
//        CreatorUtils.buildServiceClass(classes);
//        CreatorUtils.buildDAOClass(classes);
//        CreatorUtils.buildConstantsClass(classes);
//        CreatorUtils.buildBizClass(classes);
        CreatorUtils.buildDTOClass(classes);
    }

}
