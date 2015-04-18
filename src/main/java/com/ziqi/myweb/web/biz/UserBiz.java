package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.UserQuery;
import com.ziqi.myweb.dal.model.UserDO;
import com.ziqi.myweb.web.model.RegisterVO;


/**
 * Description: UserBiz
 * User: qige
 * Date: 15/4/16
 * Time: 22:11
 */
public class UserBiz extends BaseBiz<UserDTO, UserDO> {

    public boolean isAccountDuplicated(RegisterVO registerVO, Context context) {
        UserQuery query = new UserQuery();
        query.setAccount(registerVO.getAccount());
        UserDTO userDTO = this.queryOne(query, context);
        return userDTO != null;
    }

}
