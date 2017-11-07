package com.j13.jax.dz.resp;

import com.j13.poppy.anno.Parameter;

public class CommentGetResp {
    @Parameter(desc = "")
    private int dzCommentId;
    @Parameter(desc = "")
    private String content;
    @Parameter(desc = "")
    private int userId;
    @Parameter(desc = "")
    private String userName;
    @Parameter(desc = "")
    private String userHeadUrl;
    @Parameter(desc = "")
    private int isHot;
    @Parameter(desc = "")
    private int praise;
    @Parameter(desc = "")
    private int share;

    public int getIsHot() {
        return isHot;
    }

    public void setIsHot(int isHot) {
        this.isHot = isHot;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDzCommentId() {
        return dzCommentId;
    }

    public void setDzCommentId(int dzCommentId) {
        this.dzCommentId = dzCommentId;
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
