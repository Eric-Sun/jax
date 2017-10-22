package com.j13.jax.message.req;

import com.j13.poppy.anno.Parameter;

public class MessageAddReq {
    @Parameter(desc="发送者uid")
    private int fromUserId;
    @Parameter(desc="接受者uid")
    private int toUserId;
    @Parameter(desc="内容")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }
}
