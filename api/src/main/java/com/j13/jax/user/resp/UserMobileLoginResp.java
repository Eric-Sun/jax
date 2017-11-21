package com.j13.jax.user.resp;

import com.j13.poppy.anno.Parameter;

public class UserMobileLoginResp {
    @Parameter(desc="")
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
