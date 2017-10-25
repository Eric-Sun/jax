package com.j13.jax.vo;

import java.util.Date;

public class CommentVO {
    private int id;
    private int eventId;
    private int userId;
    private String content;
    private int isHot;
    private int isTop;
    private int priase;
    private int share;
    private int replyCId;
    private Date createtime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public int getPriase() {
        return priase;
    }

    public void setPriase(int priase) {
        this.priase = priase;
    }

    public int getReplyCId() {
        return replyCId;
    }

    public void setReplyCId(int replyCId) {
        this.replyCId = replyCId;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
