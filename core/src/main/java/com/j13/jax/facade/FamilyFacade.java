package com.j13.jax.facade;

import com.j13.jax.dao.FamilyDAO;
import com.j13.jax.dao.FamilyMemberDAO;
import com.j13.jax.dao.ImgDAO;
import com.j13.jax.family.req.*;
import com.j13.jax.family.resp.FamilyAddResp;
import com.j13.jax.family.resp.FamilyCreatedListResp;
import com.j13.jax.family.resp.FamilyGetResp;
import com.j13.jax.family.resp.FamilyListResp;
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
    private static final int SIZE_PER_PAGE = 10;

    @Autowired
    FamilyDAO familyDAO;
    @Autowired
    ImgHelper imgHelper;
    @Autowired
    ImgDAO imgDAO;
    @Autowired
    FamilyMemberDAO familyMemberDAO;

    @Action(name = "family.add", desc = "创建一个family")
    public FamilyAddResp add(CommandContext ctxt, FamilyAddReq req) {
        FamilyAddResp resp = new FamilyAddResp();
        int familyId = familyDAO.add(req.getName(), req.getHeadImgId(),
                req.getCoverImgId(), req.getBrief(), ctxt.getUid(), ctxt.getUid()
        );
        familyMemberDAO.add(ctxt.getUid(), familyId);
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
        // add visit time
        familyMemberDAO.setVisitTime(uid, familyId);

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
        List<FamilyVO> list = familyDAO.createdList(ctxt.getUid(), req.getPageNum(), SIZE_PER_PAGE);
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

    @Action(name = "family.list", desc = "获取所有参与的和创建的family的列表")
    public FamilyListResp list(CommandContext ctxt, FamilyListReq req) {
        FamilyListResp resp = new FamilyListResp();
        int userId = ctxt.getUid();
        int pageNum = req.getPageNum();
        List<FamilyVO> createdList = familyDAO.createdList(ctxt.getUid(), req.getPageNum(), SIZE_PER_PAGE);
        List<FamilyVO> addedList = familyDAO.addedlist(userId, pageNum, SIZE_PER_PAGE);

        createdList.addAll(addedList);
        for (FamilyVO familyVO : createdList) {
            FamilyGetResp getResp = new FamilyGetResp();
            BeanUtils.copyProperties(getResp, familyVO);
            getResp.setFamilyId(familyVO.getId());
            ImgVO coverImg = imgDAO.get(familyVO.getCoverImgId());
            ImgVO headImg = imgDAO.get(familyVO.getHeadImgId());
//            getResp.setHeadImgUrl(imgHelper.getFamilyHeadUrl(headImg.getFileName()));
            getResp.setHeadImgUrl("http://123.56.86.200/1470584630718.jpg");
            getResp.setCoverImgUrl(imgHelper.getFamilyCoverUrl(coverImg.getFileName()));

            long visitTime = familyMemberDAO.getVisitTime(userId, familyVO.getId());
            int memberCount = familyMemberDAO.getMemberCount(familyVO.getId());
            getResp.setVisitTime(visitTime);
            getResp.setMemberCount(memberCount);
            resp.getList().add(getResp);
        }
        return resp;

    }


}
