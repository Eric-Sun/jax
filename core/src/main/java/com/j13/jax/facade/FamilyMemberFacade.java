package com.j13.jax.facade;

import com.j13.jax.core.ErrorCode;
import com.j13.jax.dao.FamilyMemberDAO;
import com.j13.jax.familyMember.req.FamilyMemberAddReq;
import com.j13.jax.familyMember.req.FamilyMemberQuitReq;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.core.CommonResultResp;
import com.j13.poppy.exceptions.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FamilyMemberFacade {
    private static Logger LOG = LoggerFactory.getLogger(FamilyMemberFacade.class);

    @Autowired
    FamilyMemberDAO familyMemberDAO;


    @Action(name = "familyMember.add", desc = "")
    public CommonResultResp add(CommandContext ctxt, FamilyMemberAddReq req) {
        if (familyMemberDAO.checkExist(ctxt.getUid(), req.getFamilyId())) {
            throw new CommonException(ErrorCode.FamilyMember.EXISTED);

        } else {
            familyMemberDAO.add(ctxt.getUid(), req.getFamilyId());
            LOG.info("reAdd family member. uid={},familyId={}", ctxt.getUid(), req.getFamilyId());
        }
        return CommonResultResp.success();
    }

    @Action(name = "familyMember.quit", desc = "")
    public CommonResultResp quit(CommandContext ctxt, FamilyMemberQuitReq req) {
        familyMemberDAO.delete(ctxt.getUid(), req.getFamilyMemberId());
        LOG.info("delete family member. uid={},familyMemberId={}", ctxt.getUid(), req.getFamilyMemberId());
        return CommonResultResp.success();
    }

}
