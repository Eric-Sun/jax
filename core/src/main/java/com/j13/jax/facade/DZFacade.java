package com.j13.jax.facade;

import com.j13.jax.core.Constants;
import com.j13.jax.core.ErrorCode;
import com.j13.jax.core.PropertiesKey;
import com.j13.jax.dao.*;
import com.j13.jax.dz.req.DZAddCommentReq;
import com.j13.jax.dz.req.DZCollectReq;
import com.j13.jax.dz.req.DZGetReq;
import com.j13.jax.dz.req.DZListReq;
import com.j13.jax.dz.resp.*;
import com.j13.jax.mv.resp.MVSimpleGetResp;
import com.j13.jax.vo.*;
import com.j13.poppy.anno.Action;
import com.j13.poppy.config.PropertiesConfiguration;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.exceptions.CommonException;
import com.j13.poppy.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class DZFacade {
    private static Logger LOG = LoggerFactory.getLogger(DZFacade.class);

    @Autowired
    UserDAO userDAO;
    @Autowired
    DZDAO dzdao;
    @Autowired
    DZCommentDAO dzCommentDAO;
    @Autowired
    SystemFamilyCursorDAO systemFamilyCursorDAO;
    @Autowired
    CollectionDAO collectionDAO;

    @Action(name = "dz.list", desc = "")
    public DZListResp list(CommandContext ctxt, DZListReq req) {
        DZListResp listResp = new DZListResp();
        int userId = ctxt.getUid();

        int familyId = Constants.EventType.DZ;
        //美女
        boolean existed = systemFamilyCursorDAO.checkExisted(userId, familyId);
        int fromCursorId = 0;
        if (!existed) {
            fromCursorId = dzdao.getLeastId();
            systemFamilyCursorDAO.insert(userId, familyId, fromCursorId);
        } else {
            fromCursorId = systemFamilyCursorDAO.getCursorId(userId, familyId);
        }

        int toCursorId = 0;
        // 获取下N条
        List<DZVO> list = dzdao.list(fromCursorId, 2);
        for (DZVO dzvo : list) {
            UserVO userVO = userDAO.getUserInfo(dzvo.getUserId());
            String userHeadFileName = userVO.getImg();
            String userHeadUrl = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.USER_HEAD_PATH) + File.separator + userHeadFileName;
            DZGetResp getResp = new DZGetResp();

            BeanUtils.copyProperties(getResp, dzvo);
            getResp.setUserHeadUrl(userHeadUrl);
            getResp.setUserName(userVO.getNickName());
            listResp.getList().add(getResp);
            toCursorId = dzvo.getDzId();
        }

        // update new cursor
        systemFamilyCursorDAO.update(userId, familyId, toCursorId);
        return listResp;
    }


    @Action(name = "dz.get", desc = "")
    public DZGetResp get(CommandContext ctxt, DZGetReq req) {
        int userId = ctxt.getUid();

        DZVO dzvo = dzdao.get(req.getDzId());
        DZGetResp getResp = new DZGetResp();

        UserVO userVO = userDAO.getUserInfo(dzvo.getUserId());
        String userHeadFileName = userVO.getImg();
        String userHeadUrl = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.USER_HEAD_PATH) + File.separator + userHeadFileName;

        BeanUtils.copyProperties(getResp, dzvo);
        getResp.setUserHeadUrl(userHeadUrl);

        // load all comment
        // most at 5
        List<DZCommentVO> commentList = dzCommentDAO.list(req.getDzId(), 1, 5);
        for (DZCommentVO dzCommentVO : commentList) {
            CommentGetResp commentResp = new CommentGetResp();
            BeanUtils.copyProperties(commentResp, dzCommentVO);
            UserVO commentUserVO = userDAO.getUserNameAndImg(dzCommentVO.getUserId());
            commentResp.setUserName(commentUserVO.getNickName());
            String commentUserHeadUrl = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.USER_HEAD_PATH) + File.separator + userHeadFileName;
            commentResp.setUserHeadUrl(commentUserHeadUrl);
            getResp.getTopList().add(commentResp);
        }

        return getResp;
    }


    @Action(name = "dz.addComment", desc = "")
    public DZAddCommentResp addComment(CommandContext ctxt, DZAddCommentReq req) {
        int dzCommentId = dzCommentDAO.add(ctxt.getUid(), req.getDzId(), req.getContent(), 0);
        DZAddCommentResp resp = new DZAddCommentResp();
        resp.setDzCommentId(dzCommentId);
        return resp;
    }

    @Action(name = "dz.collect", desc = "")
    public DZCollectResp collect(CommandContext ctxt, DZCollectReq req) {
        int dzId = req.getDzId();
        int uid = ctxt.getUid();

        if (collectionDAO.checkExists(uid, dzId, Constants.CollectionType.DZ)) {
            throw new CommonException(ErrorCode.Collection.COLLECTED);
        }
        int collectionId = collectionDAO.add(uid, Constants.CollectionType.DZ, dzId);
        LOG.info("dz collect successfully. collectionId = {}", collectionId);
        DZCollectResp resp = new DZCollectResp();
        resp.setCollectionId(collectionId);
        return resp;
    }


}
