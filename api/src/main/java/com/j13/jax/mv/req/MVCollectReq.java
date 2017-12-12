package com.j13.jax.mv.req;

import com.j13.poppy.anno.Parameter;

public class MVCollectReq {
    @Parameter(desc = "")
    private int mvId;

    public int getMvId() {
        return mvId;
    }

    public void setMvId(int mvId) {
        this.mvId = mvId;
    }
}
