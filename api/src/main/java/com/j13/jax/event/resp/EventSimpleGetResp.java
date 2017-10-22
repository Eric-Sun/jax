package com.j13.jax.event.resp;

import com.j13.poppy.anno.Parameter;

public class EventSimpleGetResp {
    @Parameter(desc="事件类型，0为多图")
    private int type;
    @Parameter(desc="标题")
    private String title;
    @Parameter(desc="内容，为json，字符串，客户端通过type进行相应的解析")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
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
