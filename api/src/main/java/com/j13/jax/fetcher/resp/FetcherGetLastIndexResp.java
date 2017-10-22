package com.j13.jax.fetcher.resp;

import com.j13.poppy.anno.Parameter;

public class FetcherGetLastIndexResp {
    @Parameter(desc="索引位置")
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
