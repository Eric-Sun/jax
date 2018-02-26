package com.j13.jax.user.resp;

import com.j13.poppy.anno.Parameter;

public class UserMobileRegisterResp {
    @Parameter(desc = "")
    private int userId;
    @Parameter(desc = "")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
