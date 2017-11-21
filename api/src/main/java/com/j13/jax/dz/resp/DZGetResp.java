package com.j13.jax.dz.resp;

import com.google.common.collect.Lists;
import com.j13.poppy.anno.Parameter;

import java.util.List;

public class DZGetResp {
    private int type= -2;
    @Parameter(desc = "")
    private int dzId;
    @Parameter(desc = "")
    private String content;
    @Parameter(desc = "")
    private int userId;
    @Parameter(desc = "")
    private String userName;
    @Parameter(desc = "")
    private String userHeadUrl;
    @Parameter(desc = "")
    private int praise;
    @Parameter(desc = "")
    private int share;
    private List<CommentGetResp> topList = Lists.newLinkedList();
    private List<CommentGetResp> recentList = Lists.newLinkedList();

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<CommentGetResp> getRecentList() {
        return recentList;
    }

    public void setRecentList(List<CommentGetResp> recentList) {
        this.recentList = recentList;
    }

    public List<CommentGetResp> getTopList() {
        return topList;
    }

    public void setTopList(List<CommentGetResp> topList) {
        this.topList = topList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDzId() {
        return dzId;
    }

    public void setDzId(int dzId) {
        this.dzId = dzId;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
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
