package com.ziqi.myweb.web.module.screen;

import com.alibaba.citrus.service.requestcontext.parser.ParserRequestContext;
import com.alibaba.citrus.turbine.Context;
import com.ziqi.myweb.common.constants.ErrorCode;
import com.ziqi.myweb.web.constants.ContextConstants;
import com.ziqi.myweb.web.module.BaseModule;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;
import java.util.UUID;

/**
 * Description: Upload
 * User: qige
 * Date: 15/4/18
 * Time: 22:41
 */
public class Upload extends BaseModule {

    @Resource
    private ParserRequestContext parse;

    private static Logger logger = LoggerFactory.getLogger(Upload.class);

    public void execute(Context context) {
        try {
            FileItem fileItem = parse.getParameters().getFileItem("userfile");
            if (fileItem != null) {
                String account = (String) session.getAttribute(ContextConstants.ACCOUNT);
                File parent = new File(request.getServletContext().getRealPath("/") + "/images/" + account);
                if(parent.exists() || parent.mkdirs()) {
                    String filename = fileItem.getName();
                    String ext = filename.substring(filename.lastIndexOf("."));
                    File file = new File(parent.getAbsolutePath() + "/" + UUID.randomUUID() + ext);
                    if(!file.createNewFile()) {
                        response.getOutputStream().println("<script>parent.callback('failed', '" + ErrorCode.ERR_WEB_0001 + "');</script>");
                        response.getOutputStream().close();
                        return;
                    }
                    fileItem.write(file);
                    String fileUrl = request.getContextPath() + "/images/" + account + "/" + file.getName();
                    response.getOutputStream().println("<script>parent.callback('success', '" + fileUrl + "');</script>");
                    response.getOutputStream().close();
                } else {
                    response.getOutputStream().println("<script>parent.callback('failed', '" + ErrorCode.ERR_WEB_0001 + "');</script>");
                    response.getOutputStream().close();
                }
            }
        } catch (Exception e) {
            onException(context, logger, e);
        }

    }

}
