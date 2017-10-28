package com.j13.jax.comment.req;

import com.j13.poppy.anno.Parameter;

public class CommentUserActionReq {
    @Parameter(desc="用户操作，0位点赞，1为分享")
    private int userActionType;
    @Parameter(desc="")
    private int cid;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUserActionType() {
        return userActionType;
    }

    public void setUserActionType(int userActionType) {
        this.userActionType = userActionType;
    }
}
