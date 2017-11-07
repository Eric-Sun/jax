package com.j13.jax.vo;

import com.google.common.collect.Lists;
import com.j13.jax.dz.resp.CommentGetResp;
import com.j13.poppy.anno.Parameter;

import java.util.List;

public class DZVO {
    private int dzId;
    private String content;
    private int userId;
    private String userName;
    private String userHeadUrl;
    private int praise;
    private int share;
    private List<DZCommentVO> topList = Lists.newLinkedList();
    private List<DZCommentVO> recentList = Lists.newLinkedList();

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

    public List<DZCommentVO> getRecentList() {
        return recentList;
    }

    public void setRecentList(List<DZCommentVO> recentList) {
        this.recentList = recentList;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public List<DZCommentVO> getTopList() {
        return topList;
    }

    public void setTopList(List<DZCommentVO> topList) {
        this.topList = topList;
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
