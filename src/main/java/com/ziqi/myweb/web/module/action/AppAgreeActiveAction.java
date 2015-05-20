package com.ziqi.myweb.web.module.action;

import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ActiveConstants;
import com.ziqi.myweb.common.model.ActiveDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.web.biz.ActiveBiz;
import com.ziqi.myweb.web.biz.UserBiz;
import com.ziqi.myweb.web.constants.ContextConstants;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Description: AgreeActiveAction
 * User: qige
 * Date: 15/5/8
 * Time: 15:08
 */
public class AppAgreeActiveAction extends BaseModule {

    @Resource
    private ActiveBiz activeBiz;

    @Resource
    private UserBiz userBiz;

    @Resource
    private ParserRequestContext parse;

    private Logger logger = LoggerFactory.getLogger(AppAgreeActiveAction.class);

    public void execute(Context context) {
        try {
            String id = request.getParameter("id");
            String agree = request.getParameter("agree");
            String userId = request.getParameter("userId");
            String account = request.getParameter("account");
            if (StringUtils.isBlank(id)) {
                response.sendRedirect(getHostUrl() + "/faceToFace.htm");
                return;
            }

            ActiveDTO activeDTO = activeBiz.queryById(Integer.parseInt(id), context);
            if (agree.equals("1")) {
                if (activeDTO.getTopBeautyId().equals(Integer.parseInt(userId))) {
                    activeDTO.setStatus(ActiveConstants.Status.AGREED);
                    FileItem fileItem = parse.getParameters().getFileItem("userfile");
                    if (fileItem != null) {
                        String result = userBiz.uploadImage(fileItem, account, getFilesRoot());
                        activeDTO.setImagePath(result);
                    }
                    activeBiz.update(activeDTO, context);
                    response.getWriter().write("agreed");
                }
            } else {
                if (activeDTO.getTopBeautyId().equals(Integer.parseInt(userId))) {
                    activeDTO.setStatus(ActiveConstants.Status.REJECT);
                    activeBiz.update(activeDTO, context);
                    response.getWriter().write("rejected");
                }
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }
    }
}
