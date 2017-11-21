package com.j13.jax.dz.req;

import com.j13.poppy.anno.Parameter;

public class DZAddCommentReq {
    @Parameter(desc = "")
    private int dzId;
    @Parameter(desc = "")
    private String content;

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

