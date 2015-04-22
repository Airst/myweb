package com.ziqi.myweb.temp.script;

import com.ziqi.myweb.dal.model.*;

/**
 * Description: ClassCreator
 * User: qige
 * Date: 15/4/13
 * Time: 12:57
 */
public class ClassCreator {

    public static void main(String[] args) throws Exception {
        Class[] classes = {ReplyDO.class, ThreadDO.class, UserDO.class, ImageDO.class};
        CreatorUtils.buildQueryClass(classes);
//        CreatorUtils.buildServiceClass(classes);
//        CreatorUtils.buildDAOClass(classes);
        CreatorUtils.buildConstantsClass(new Class[] {BaseDO.class, ReplyDO.class, ThreadDO.class, UserDO.class, ImageDO.class});
//        CreatorUtils.buildBizClass(classes);

//        CreatorUtils.buildDTOClass(new Class[] { ReplyDO.class });
    }

}
