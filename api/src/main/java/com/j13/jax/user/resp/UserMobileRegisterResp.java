package com.j13.jax.user.resp;

import com.j13.poppy.anno.Parameter;

public class UserMobileRegisterResp {
    @Parameter(desc = "")
    private int userId;
    @Parameter(desc = "")
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
