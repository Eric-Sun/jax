package com.j13.jax.facade;

import com.j13.jax.core.ErrorCode;
import com.j13.jax.dao.UserDAO;
import com.j13.jax.user.req.UserMobileLoginReq;
import com.j13.jax.user.req.UserMobileRegisterReq;
import com.j13.jax.user.req.UserRandomUserReq;
import com.j13.jax.user.resp.UserMobileLoginResp;
import com.j13.jax.user.resp.UserMobileRegisterResp;
import com.j13.jax.user.resp.UserRandomUserResp;
import com.j13.jax.vo.UserVO;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.exceptions.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    @Autowired
    UserDAO userDAO;

    @Action(name = "user.randomUser", desc = "随机一个用户")
    public UserRandomUserResp randomUser(CommandContext ctxt, UserRandomUserReq req) {
        UserRandomUserResp resp = new UserRandomUserResp();
        int uid = userDAO.randomUser();
        resp.setUid(uid);
        return resp;
    }

    @Action(name = "user.mobileLogin", desc = "")
    public UserMobileLoginResp mobileLogin(CommandContext ctxt, UserMobileLoginReq req) {
        UserMobileLoginResp resp = new UserMobileLoginResp();
        String mobile = req.getMobile();
        String passwordAfterMD5 = req.getPasswordAfterMD5();
        UserVO userVO = null;
        try {
            userVO = userDAO.mobileLogin(mobile, passwordAfterMD5);
        } catch (EmptyResultDataAccessException e) {
            throw new CommonException(ErrorCode.User.USER_LOGIN_ERROR);
        }
        resp.setUserId(userVO.getId());
        return resp;
    }


    @Action(name = "user.mobileRegister", desc = "")
    public UserMobileRegisterResp mobileRegister(CommandContext ctxt, UserMobileRegisterReq req) {
        UserMobileRegisterResp resp = new UserMobileRegisterResp();
        String mobile = req.getMobile();
        String passwordAfterMD5 = req.getPasswordAfterMD5();

        if (userDAO.checkMobile(mobile)) {
            throw new CommonException(ErrorCode.User.USER_MOBILE_EXISTED);
        }
        int userId = userDAO.mobileRegister(mobile, passwordAfterMD5);
        resp.setUserId(userId);
        resp.setNickName(mobile);
        return resp;
    }


}
