package com.j13.jax.message.facade;

import com.alibaba.fastjson.JSON;
import com.j13.jax.message.dao.MessageDAO;
import com.j13.jax.message.req.MessageAddReq;
import com.j13.jax.message.req.MessageDeleteReq;
import com.j13.jax.message.req.MessageSearchReq;
import com.j13.jax.message.resp.MessageGetResp;
import com.j13.jax.message.resp.MessageSearchResp;
import com.j13.jax.message.vo.MessageVO;
import com.j13.jax.user.dao.UserDAO;
import com.j13.jax.user.vo.UserVO;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.core.CommonResultResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageFacade {
    private static Logger LOG = LoggerFactory.getLogger(MessageFacade.class);

    @Autowired
    MessageDAO messageDAO;
    @Autowired
    UserDAO userDAO;

    @Action(name = "message.add", desc = "发送消息")
    public CommonResultResp add(CommandContext ctxt, MessageAddReq req) {
        CommonResultResp resp = new CommonResultResp();
        MessageVO messageVO = new MessageVO();
        messageVO.setContent(req.getContent());
        messageVO.setFromUserId(req.getFromUserId());
        messageVO.setToUserId(req.getToUserId());

        // 检测接受者id是否存在


        int id = messageDAO.add(messageVO);
        LOG.info("message add successfully. id={}", id);
        return resp;
    }


    @Action(name = "message.delete", desc = "删除消息,如果返回-1则为删除失败")
    public CommonResultResp delete(CommandContext ctxt, MessageDeleteReq req) {
        CommonResultResp resp = new CommonResultResp();

        int size = messageDAO.delete(req.getToUserId(), req.getMessageId());
        if (size == 0) {
            resp.setResult(-1);
            LOG.info("delete failed. size={},data={}", size, JSON.toJSONString(req));
        } else {
            LOG.info("delete successfully. id={}", req.getMessageId());
        }
        return resp;
    }

    @Action(name = "message.read", desc = "读取消息,如果返回-1则为删除失败")
    public CommonResultResp read(CommandContext ctxt, MessageDeleteReq req) {
        CommonResultResp resp = new CommonResultResp();

        int size = messageDAO.read(req.getToUserId(), req.getMessageId());
        if (size == 0) {
            resp.setResult(-1);
            LOG.info("read failed. size={},data={}", size, JSON.toJSONString(req));
        } else {
            LOG.info("read successfully. id={}", req.getMessageId());
        }
        return resp;
    }


    @Action(name = "message.search", desc = "查询消息")
    public MessageSearchResp search(CommandContext ctxt, MessageSearchReq req) {
        MessageSearchResp resp = new MessageSearchResp();
        List<MessageVO> list = messageDAO.search(req.getToUserId(), req.getPageNum(), req.getSizePerPage());
        for (MessageVO vo : list) {
            MessageGetResp messageGetResp = new MessageGetResp();
            messageGetResp.setContent(vo.getContent());
            messageGetResp.setCreatetime(vo.getCreatetime().getTime());
            UserVO fromUserInfo = userDAO.getUserInfo(vo.getFromUserId());
            messageGetResp.setFromUserHeadImgUrl(fromUserInfo.getImgUrl());
            messageGetResp.setFromUserName(fromUserInfo.getNickName());
            messageGetResp.setFromUserId(vo.getFromUserId());
            messageGetResp.setToUserId(vo.getToUserId());
            UserVO toUserInfo = userDAO.getUserInfo(vo.getToUserId());
            messageGetResp.setToUserName(toUserInfo.getNickName());
            messageGetResp.setToUserHeadImgUrl(toUserInfo.getImgUrl());
            messageGetResp.setRead(vo.getRead());
            messageGetResp.setMessageId(vo.getId());
            resp.getList().add(messageGetResp);
        }
        return resp;
    }


}
