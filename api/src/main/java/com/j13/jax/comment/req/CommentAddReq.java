package com.j13.jax.comment.req;

import com.j13.poppy.anno.Parameter;

public class CommentAddReq {

    @Parameter(desc = "")
    private int eventId;
    @Parameter(desc = "")
    private String content;
    @Parameter(desc = "")
    private int replyCId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getReplyCId() {
        return replyCId;
    }

    public void setReplyCId(int replyCId) {
        this.replyCId = replyCId;
    }
}
