package com.j13.jax.fetcher.resp;

import com.j13.poppy.anno.Parameter;

public class FetcherMVImgAddResp {
    @Parameter(desc = "")
    private int mvImgId;

    public int getMvImgId() {
        return mvImgId;
    }

    public void setMvImgId(int mvImgId) {
        this.mvImgId = mvImgId;
    }
}
