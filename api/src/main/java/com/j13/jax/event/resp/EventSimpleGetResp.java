package com.j13.jax.event.resp;

import com.j13.poppy.anno.Parameter;

public class EventSimpleGetResp {
    @Parameter(desc="事件类型，0为多图")
    private int type;
    @Parameter(desc="标题")
    private String title;
    @Parameter(desc="内容为Object，客户端通过type进行解析")
    private Object content;
    @Parameter(desc="用户头像地址")
    private String userHeadUrl;
    @Parameter(desc="用户昵称")
    private String userName;
    @Parameter(desc="")
    private int id;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
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
}
