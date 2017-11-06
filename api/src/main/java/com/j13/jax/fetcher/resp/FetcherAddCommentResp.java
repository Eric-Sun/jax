package com.j13.jax.fetcher.resp;

import com.j13.poppy.anno.Parameter;

public class FetcherAddCommentResp {
    @Parameter(desc = "")
    private int dzCommentId;

    public int getDzCommentId() {
        return dzCommentId;
    }

    public void setDzCommentId(int dzCommentId) {
        this.dzCommentId = dzCommentId;
    }
}
