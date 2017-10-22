package com.j13.jax.message.req;

import com.j13.poppy.anno.Parameter;

public class MessageReadReq {
    @Parameter(desc = "接受者id")
    private int toUserId;
    @Parameter(desc = "消息id")
    private int messageId;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }
}
