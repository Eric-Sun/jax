package com.j13.jax.facade;

import com.j13.jax.dao.ImgDAO;
import com.j13.jax.user.req.ImgUploadReq;
import com.j13.jax.user.resp.ImgUploadResp;
import com.j13.jax.service.ImgHelper;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImgFacade {
    private static Logger LOG = LoggerFactory.getLogger(ImgFacade.class);

    @Autowired
    ImgHelper imgHelper;

    @Autowired
    ImgDAO imgDAO;

    @Action(name = "img.upload", desc = "图片上传，并且回传图片的id")
    public ImgUploadResp upload(CommandContext ctxt, ImgUploadReq req) {
        int type = req.getType();
        String fileName = imgHelper.saveImg(req.getFile(), type);
        // 存储

        int imgId = imgDAO.add(type, fileName);
        LOG.info("img add. type={}, fileName={}", type, fileName);
        ImgUploadResp resp = new ImgUploadResp();
        resp.setImgId(imgId);
        return resp;
    }
}
