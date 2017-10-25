package com.j13.jax.facade;

import com.j13.jax.dao.UserDAO;
import com.j13.jax.user.req.UserRandomUserReq;
import com.j13.jax.user.resp.UserRandomUserResp;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
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


}
