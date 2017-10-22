package com.j13.jax.fetcher.req;

import com.j13.poppy.anno.Parameter;

public class FetcherUpdateLastIndexReq {
    @Parameter(desc="")
    private int sourceId;
    @Parameter(desc="上次成功的最后一个位置")
    private int lastIndex;

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
