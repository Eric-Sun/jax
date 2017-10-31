package com.j13.jax.event.req;

import com.j13.poppy.anno.Parameter;

public class EventCollectReq {
    @Parameter(desc="")
    private int eventId;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
