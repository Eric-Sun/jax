package com.j13.jax.facade;

import com.j13.jax.dao.FamilyDAO;
import com.j13.jax.family.req.FamilyAddReq;
import com.j13.jax.family.req.FamilyDeleteReq;
import com.j13.jax.family.req.FamilyGetReq;
import com.j13.jax.family.resp.FamilyAddResp;
import com.j13.jax.family.resp.FamilyGetResp;
import com.j13.jax.service.ImgService;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.core.CommonResultResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FamilyFacade {
    private static Logger LOG = LoggerFactory.getLogger(FamilyFacade.class);

    @Autowired
    FamilyDAO familyDAO;
    @Autowired
    ImgService imgService;


    @Action(name = "family.add", desc = "")
    public FamilyAddResp add(CommandContext ctxt, FamilyAddReq req) {
        FamilyAddResp resp = new FamilyAddResp();
        String headFilename = imgService.saveImg(req.getHeadImg(), ImgService.FAMILY_HEADIMG);
        String coverFilename = imgService.saveImg(req.getCoverImg(), ImgService.FAMILY_COVERIMG);
        int familyId = familyDAO.add(req.getName(), headFilename,
                coverFilename, req.getBrief(), req.getCreatorUserId(), req.getCreatorUserId()
        );
        LOG.info("family add success. id={}", familyId);
        resp.setFamilyId(familyId);
        return resp;
    }


    @Action(name = "family.delete", desc = "")
    public CommonResultResp delete(CommandContext ctxt, FamilyDeleteReq req) {

        int uid = ctxt.getUid();
        int familyId = req.getFamilyId();

        int count = familyDAO.delete(uid, familyId);
        LOG.info("family deleted. id={}", familyId);
        return CommonResultResp.success();
    }


    public FamilyGetResp get(CommandContext ctxt, FamilyGetReq req) {
        int uid = ctxt.getUid();
        int familyId = req.getFamilyId();

        return null;

    }

}
