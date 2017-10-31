package com.j13.jax.facade;

import com.j13.jax.dao.FamilyDAO;
import com.j13.jax.dao.ImgDAO;
import com.j13.jax.family.req.FamilyAddReq;
import com.j13.jax.family.req.FamilyCreatedListReq;
import com.j13.jax.family.req.FamilyDeleteReq;
import com.j13.jax.family.req.FamilyGetReq;
import com.j13.jax.family.resp.FamilyAddResp;
import com.j13.jax.family.resp.FamilyCreatedListResp;
import com.j13.jax.family.resp.FamilyGetResp;
import com.j13.jax.service.ImgHelper;
import com.j13.jax.vo.FamilyVO;
import com.j13.jax.vo.ImgVO;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.core.CommonResultResp;
import com.j13.poppy.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class FamilyFacade {
    private static Logger LOG = LoggerFactory.getLogger(FamilyFacade.class);

    @Autowired
    FamilyDAO familyDAO;
    @Autowired
    ImgHelper imgHelper;
    @Autowired
    ImgDAO imgDAO;


    @Action(name = "family.add", desc = "")
    public FamilyAddResp add(CommandContext ctxt, FamilyAddReq req) {
        FamilyAddResp resp = new FamilyAddResp();
        int familyId = familyDAO.add(req.getName(), req.getHeadImgId(),
                req.getCoverImgId(), req.getBrief(), ctxt.getUid(), ctxt.getUid()
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


    @Action(name = "family.get", desc = "")
    public FamilyGetResp get(CommandContext ctxt, FamilyGetReq req) {
        FamilyGetResp getResp = new FamilyGetResp();
        int uid = ctxt.getUid();
        int familyId = req.getFamilyId();

        FamilyVO familyVO = familyDAO.get(familyId);

        BeanUtils.copyProperties(getResp, familyVO);
        ImgVO coverImg = imgDAO.get(familyVO.getCoverImgId());
        ImgVO headImg = imgDAO.get(familyVO.getHeadImgId());
        getResp.setHeadImgUrl(imgHelper.getFamilyHeadUrl(headImg.getFileName()));
        getResp.setCoverImgUrl(imgHelper.getFamilyCoverUrl(coverImg.getFileName()));
        getResp.setFamilyId(familyId);

        return getResp;
    }


    @Action(name = "family.createdList", desc = "查询用户创建的所有family的列表")
    public FamilyCreatedListResp createdList(CommandContext ctxt, FamilyCreatedListReq req) {
        FamilyCreatedListResp listResp = new FamilyCreatedListResp();
        List<FamilyVO> list = familyDAO.createdList(ctxt.getUid(), req.getPageNum(), 10);
        for (FamilyVO familyVO : list) {
            FamilyGetResp getResp = new FamilyGetResp();
            BeanUtils.copyProperties(getResp, familyVO);
            ImgVO coverImg = imgDAO.get(familyVO.getCoverImgId());
            ImgVO headImg = imgDAO.get(familyVO.getHeadImgId());
            getResp.setHeadImgUrl(imgHelper.getFamilyHeadUrl(headImg.getFileName()));
            getResp.setCoverImgUrl(imgHelper.getFamilyCoverUrl(coverImg.getFileName()));
            listResp.getList().add(getResp);
        }
        return listResp;
    }


}
