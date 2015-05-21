package com.ziqi.myweb.web.biz;

import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.UserConstants;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.UserQuery;
import com.ziqi.myweb.core.service.ThreadService;
import com.ziqi.myweb.dal.model.UserDO;
import com.ziqi.myweb.web.model.RegisterVO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

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
        query.setLevel(30);
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

    /**
     * 发表文章，level增加，加成点数减少
     */
    public void levelForPublish(int userId, Context context) {
        //用户升级
        UserDTO userDTO = queryById(userId, context);
        String point = userDTO.getFeature(UserConstants.feature.POINT);
        if(StringUtils.isBlank(point)) {
            userDTO.addFeature(UserConstants.feature.POINT, "4");
            point = "5";
        } else {
            if(Integer.parseInt(point) > 0) {
                userDTO.addFeature(UserConstants.feature.POINT, Integer.parseInt(point) - 1 + "");
            } else if(Integer.parseInt(point) < 0) {
                userDTO.addFeature(UserConstants.feature.POINT, "0");
                point = "0";
            }
        }
        userDTO.setLevel(userDTO.getLevel() + Integer.parseInt(point));//每发表一篇文章加分
        update(userDTO, context);
    }

    /**
     * 其他用户评论后，作者回复加成点数
     */
    public void pointForReply(int userId, int authorId, Context context) {
        if(userId == authorId) {
            return;
        }

        //用户升级
        UserDTO userDTO = queryById(authorId, context);
        String point = userDTO.getFeature("point");
        if(StringUtils.isBlank(point)) {
            userDTO.addFeature(UserConstants.feature.POINT, "5");
        } else {
            if(Integer.parseInt(point) < 5) {
                userDTO.addFeature(UserConstants.feature.POINT, Integer.parseInt(point) + 1 + "");
            } else if(Integer.parseInt(point) > 5) {
                userDTO.addFeature(UserConstants.feature.POINT, "5");
            }
        }
        update(userDTO, context);
    }

    /**
     * 文章作者回复他人评论，level增加，加成增加、
     */
    public void levelForReply(int threadAuthor, int replyAuthor, int userId, Context context) {
        if(threadAuthor != userId || replyAuthor == userId ) {
            return;
        }

        //用户升级
        UserDTO userDTO = queryById(userId, context);
        String point = userDTO.getFeature("point");
        if(StringUtils.isBlank(point)) {
            userDTO.addFeature(UserConstants.feature.POINT, "5");
            point = "5";
        } else {
            if(Integer.parseInt(point) < 5) {
                userDTO.addFeature(UserConstants.feature.POINT, Integer.parseInt(point) + 1 + "");
            } else if(Integer.parseInt(point) > 5) {
                userDTO.addFeature(UserConstants.feature.POINT, "5");
                point = "5";
            } else if(Integer.parseInt(point) < 0) {//防错步骤
                userDTO.addFeature(UserConstants.feature.POINT, "1");
                point = "1";
            }
        }
        userDTO.setLevel(userDTO.getLevel() + Integer.parseInt(point));//每发表一篇文章加分
        update(userDTO, context);
    }

}
