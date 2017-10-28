package com.j13.jax.comment.req;

import com.j13.poppy.anno.Parameter;

public class CommentListReq {
    @Parameter(desc="")
    private int eventId;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
