package com.j13.jax.fetcher.resp;

import com.j13.poppy.anno.Parameter;

public class FetcherDZAddResp {
    @Parameter(desc="")
    private int dzId;

    public int getDzId() {
        return dzId;
    }

    public void setDzId(int dzId) {
        this.dzId = dzId;
    }
}
