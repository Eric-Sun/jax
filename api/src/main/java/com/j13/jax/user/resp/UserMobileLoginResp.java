package com.j13.jax.user.resp;

import com.j13.poppy.anno.Parameter;

public class UserMobileLoginResp {
    @Parameter(desc = "")
    private int userId;
    @Parameter(desc = "")
    private String userHeadUrl;
    @Parameter(desc = "")
    private String userName;

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
