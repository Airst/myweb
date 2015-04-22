package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.model.ReplyDTO;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.UserQuery;
import com.ziqi.myweb.core.service.ThreadService;
import com.ziqi.myweb.dal.model.UserDO;
import com.ziqi.myweb.web.model.RegisterVO;
import org.apache.commons.fileupload.FileItem;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.UUID;


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

    public String uploadImage(FileItem fileItem, String account, String parentPath) throws Exception{
        if (fileItem != null) {
            File accountDir = new File(parentPath + "/images/" + account);
            if(accountDir.exists() || accountDir.mkdirs()) {
                String filename = fileItem.getName();
                String imageExt = filename.substring(filename.lastIndexOf("."));
                String imageFile = UUID.randomUUID() + imageExt;
                File file = new File(accountDir.getAbsolutePath() + "/" + imageFile);
                if(!file.createNewFile()) {
                    return "<script>parent.callback('failed', '" + ErrorCode.ERR_WEB_0001 + "');</script>";
                }
                fileItem.write(file);
                String fileUrl = file.getAbsolutePath().replace(parentPath, "");
                return "<script>parent.callback('success', '" + fileUrl + "');</script>";
            } else {
                return "<script>parent.callback('failed', '" + ErrorCode.ERR_WEB_0001 + "');</script>";
            }
        }
        return "<script>parent.callback('failed', '" + ErrorCode.ERR_WEB_0001 + "');</script>";
    }

}
