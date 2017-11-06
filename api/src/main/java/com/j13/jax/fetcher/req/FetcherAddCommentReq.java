package com.j13.jax.fetcher.req;

import com.j13.poppy.anno.Parameter;

public class FetcherAddCommentReq {
    @Parameter(desc = "")
    private int dzId;
    @Parameter(desc = "")
    private String content;
    @Parameter(desc = "")
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDzId() {
        return dzId;
    }

    public void setDzId(int dzId) {
        this.dzId = dzId;
    }
}
