package com.j13.jax.comment.req;

import com.j13.poppy.anno.Parameter;

public class CommentDeleteReq {
    @Parameter(desc="")
    private int cid;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
