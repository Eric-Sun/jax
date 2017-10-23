package com.j13.jax.event.req;

import com.j13.poppy.anno.Parameter;

public class EventAddReq {
    @Parameter(desc="")
    private int familyId;
    @Parameter(desc="")
    private int userId;
    @Parameter(desc="")
    private int type;
    @Parameter(desc="")
    private String title;
    @Parameter(desc="")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
