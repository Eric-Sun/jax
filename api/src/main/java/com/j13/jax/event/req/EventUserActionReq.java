package com.j13.jax.event.req;

import com.j13.poppy.anno.Parameter;

public class EventUserActionReq {
    @Parameter(desc="用户操作，0位点赞，1为分享")
    private int userActionType;
    @Parameter(desc="")
    private int eventId;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserActionType() {
        return userActionType;
    }

    public void setUserActionType(int userActionType) {
        this.userActionType = userActionType;
    }
}
