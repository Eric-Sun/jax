package com.j13.jax.comment.req;

import com.j13.poppy.anno.Parameter;

public class CommentCollectReq {
    @Parameter(desc = "")
    private int commentId;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }
}
