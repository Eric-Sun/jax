package com.j13.jax.fetcher.req;

import com.j13.poppy.anno.Parameter;

public class FetcherGetLastIndexReq {
    @Parameter(desc="")
    private int sourceId;

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
