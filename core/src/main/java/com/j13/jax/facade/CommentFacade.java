package com.j13.jax.facade;

import com.j13.jax.comment.req.*;
import com.j13.jax.comment.resp.CommentAddResp;
import com.j13.jax.comment.resp.CommentGetResp;
import com.j13.jax.comment.resp.CommentListResp;
import com.j13.jax.comment.resp.CommentReplyResp;
import com.j13.jax.core.Constants;
import com.j13.jax.core.ErrorCode;
import com.j13.jax.dao.CollectionDAO;
import com.j13.jax.dao.CommentDAO;
import com.j13.jax.dao.UserDAO;
import com.j13.jax.service.ImgHelper;
import com.j13.jax.vo.CommentVO;
import com.j13.jax.vo.UserVO;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.core.CommonResultResp;
import com.j13.poppy.exceptions.CommonException;
import com.j13.poppy.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentFacade {
    private static Logger LOG = LoggerFactory.getLogger(CommentFacade.class);

    @Autowired
    CommentDAO commentDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    ImgHelper imgHelper;
    @Autowired
    CollectionDAO collectionDAO;

    @Action(name = "comment.add", desc = "")
    public CommentAddResp add(CommandContext ctxt, CommentAddReq req) {
        CommentAddResp resp = new CommentAddResp();
        int cid = commentDAO.add(req.getEventId(), ctxt.getUid(), req.getContent(), req.getReplyCId());
        resp.setCid(cid);
        return resp;
    }


    @Action(name = "comment.delete", desc = "")
    public CommonResultResp delete(CommandContext ctxt, CommentDeleteReq req) {
        commentDAO.delete(ctxt.getUid(), req.getCid());
        return CommonResultResp.success();
    }

    @Action(name = "comment.list", desc = "")
    public CommentListResp list(CommandContext ctxt, CommentListReq req) {
        CommentListResp listResp = new CommentListResp();
        List<CommentVO> list = commentDAO.list(req.getEventId());

        for (CommentVO commentVO : list) {
            CommentGetResp getResp = new CommentGetResp();
            BeanUtils.copyProperties(getResp, commentVO);
            UserVO userVO = userDAO.getUserNameAndImg(getResp.getUserId());
            getResp.setUserName(userVO.getNickName());
            getResp.setUserHeadUrl(imgHelper.getUserHeadUrl(userVO.getImg()));

            if (commentVO.getReplyCId() != 0) {
                // 有reply
                int replyCId = commentVO.getReplyCId();
                CommentVO replyCommentVO = commentDAO.get(replyCId);
                CommentReplyResp replyResp = new CommentReplyResp();
                replyResp.setContent(replyCommentVO.getContent());
                replyResp.setUserId(replyCommentVO.getUserId());
                UserVO replyUserVO = userDAO.getUserNameAndImg(replyResp.getUserId());
                replyResp.setUserName(replyUserVO.getNickName());
                replyResp.setUserHeadUrl(imgHelper.getUserHeadUrl(userVO.getImg()));
                getResp.setReplyComment(replyResp);
            }

            listResp.getList().add(getResp);
        }
        return listResp;
    }


    @Action(name = "comment.userAction", desc = "")
    public CommonResultResp userAciton(CommandContext ctxt, CommentUserActionReq req) {
        int userId = ctxt.getUid();
        int type = req.getUserActionType();
        int cid = req.getCid();

        if (type == Constants.UserAction.PRAISE) {
            commentDAO.praise(cid);
            LOG.info("praise comment. uid={},cid={}", userId, cid);
        } else {
            commentDAO.share(cid);
            LOG.info("share comment. uid={},cid={}", userId, cid);
        }
        return CommonResultResp.success();
    }

    @Action(name = "comment.collect", desc = "")
    public CommonResultResp collect(CommandContext ctxt, CommentCollectReq req) {
        int commentId = req.getCommentId();
        if (collectionDAO.checkExists(ctxt.getUid(), req.getCommentId(), Constants.CollectionType.COMMENT)) {
            //error
            throw new CommonException(ErrorCode.Comment.COMMENT_COLLECTION_EXIST);
        }
        collectionDAO.add(ctxt.getUid(), Constants.CollectionType.COMMENT, commentId);
        LOG.info("add collection . type=comment, eventId={},userId", req.getCommentId(), ctxt.getUid());
        return CommonResultResp.success();
    }

}
