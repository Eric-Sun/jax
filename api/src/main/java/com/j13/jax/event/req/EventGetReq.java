package com.j13.jax.event.req;

import com.j13.poppy.anno.Parameter;

public class EventGetReq {
    @Parameter(desc="")
    private int eventId;
    @Parameter(desc="")
    private int familyId;

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
