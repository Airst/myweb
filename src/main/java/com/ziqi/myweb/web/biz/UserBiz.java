package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.UserQuery;
import com.ziqi.myweb.core.service.ThreadService;
import com.ziqi.myweb.dal.model.UserDO;
import com.ziqi.myweb.web.model.RegisterVO;
import org.apache.commons.fileupload.FileItem;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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

    public String uploadImage(FileItem fileItem, String account, String parentPath) throws Exception {
        if (fileItem != null) {
            String filename = fileItem.getName();
            String extension = filename.substring(filename.lastIndexOf(".")); //".jpg"
            String accountPath = "/images/" + account;  //"/images/ziqi.gzq"
            String contentPath = accountPath + "/" + UUID.randomUUID() + extension; //"/images/ziqi.gzq/1111.jpg"
            String savePath = parentPath + contentPath; //"/xxx/images/ziqi.gzq/1111.jpg"
            File accountDir = new File(parentPath + accountPath);
            if(accountDir.exists() || accountDir.mkdirs()) {
                File file = new File(savePath);
                if(!file.createNewFile()) {
                    return "error";
                }
                fileItem.write(file);
                return contentPath;
            } else {
                return "error";
            }
        }
        return "error";
    }
    
    public String uploadAppImage(FileItem fileItem, String account, String parentPath) throws Exception {
        return uploadImage(fileItem, account, parentPath);
    }

    public List<UserDTO> listTopBeauty(int pageIndex, Context context) {
        UserQuery query = new UserQuery();
        query.setLevel(0);
        query.setPageIndex(pageIndex);
        return query(query, context);
    }

    public List<UserDTO> listTopUser(Context context) {
        List<UserDTO> userDTOs = new ArrayList<UserDTO>();
        ResultDTO<List<Integer>> resultDTO = threadService.listTopUser();
        List<Integer> result = result(resultDTO, context);
        for(Integer id : result) {
            userDTOs.add(queryById(id, context));
        }
        return userDTOs;
    }

}
