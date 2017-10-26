package com.j13.jax.event.resp;

import com.j13.poppy.anno.Parameter;

public class EventGetResp {
    @Parameter(desc="")
    private int id;
    @Parameter(desc="")
    private int userId;
    @Parameter(desc="")
    private String userName;
    @Parameter(desc="")
    private String title;
    @Parameter(desc="")
    private Object content;
    @Parameter(desc="")
    private long createtime;
    @Parameter(desc="")
    private int type;
    @Parameter(desc="")
    private String userImgUrl;

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
