package com.j13.jax.dz.req;

import com.j13.poppy.anno.Parameter;

public class DZCollectReq {
    @Parameter(desc = "")
    private int dzId;

    public int getDzId() {
        return dzId;
    }

    public void setDzId(int dzId) {
        this.dzId = dzId;
    }
}
