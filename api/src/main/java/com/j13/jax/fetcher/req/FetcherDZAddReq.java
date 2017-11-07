package com.j13.jax.fetcher.req;

import com.j13.poppy.anno.Parameter;

public class FetcherDZAddReq {
    @Parameter(desc = "")
    private int userId;
    @Parameter(desc = "")
    private String content;
    @Parameter(desc = "")
    private int sourceId;
    @Parameter(desc = "")
    private String sourceDZId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSourceDZId() {
        return sourceDZId;
    }

    public void setSourceDZId(String sourceDZId) {
        this.sourceDZId = sourceDZId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
