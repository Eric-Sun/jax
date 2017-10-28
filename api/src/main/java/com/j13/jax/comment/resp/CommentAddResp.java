package com.j13.jax.comment.resp;

import com.j13.poppy.anno.Parameter;

public class CommentAddResp {
    @Parameter(desc="")
    private int cid;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
