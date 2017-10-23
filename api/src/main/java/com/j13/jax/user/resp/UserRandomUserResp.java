package com.j13.jax.user.resp;

import com.j13.poppy.anno.Parameter;

public class UserRandomUserResp {
    @Parameter(desc="随机的uid")
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
