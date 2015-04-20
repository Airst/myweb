package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.UserQuery;
import com.ziqi.myweb.core.service.ThreadService;
import com.ziqi.myweb.dal.model.UserDO;
import com.ziqi.myweb.web.model.RegisterVO;
import org.apache.commons.fileupload.FileItem;

import javax.annotation.Resource;
import java.io.File;
import java.util.UUID;


/**
 * Description: UserBiz
 * User: qige
 * Date: 15/4/16
 * Time: 22:11
 */
public class UserBiz extends BaseBiz<UserDTO, UserDO> {

    @Resource
    private ThreadService threadService;

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

    public boolean publishThread(String title, String content, String parentPath, Integer userId, Context context) {

        File threadsDir = new File(parentPath + "/threads");
        if(!threadsDir.exists() && !threadsDir.mkdirs()) {
            return false;
        }
        String filename = UUID.randomUUID() + "_" + userId + ".html";
        String savePath = threadsDir.getAbsolutePath() + "/" + filename;
        String contentPath = savePath.replace(parentPath, "");

        ThreadDTO threadDTO = new ThreadDTO();
        threadDTO.setContent(content);
        threadDTO.setAuthorId(userId);
        threadDTO.setContentPath(contentPath);
        threadDTO.setHit(0);
        threadDTO.setLikeCount(0);
        threadDTO.setReplyCount(0);
        threadDTO.setTitle(title);
        return resultBoolean(threadService.publishThread(threadDTO, savePath), context);

    }

}
