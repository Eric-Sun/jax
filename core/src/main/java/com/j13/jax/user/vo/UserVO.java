package com.j13.jax.user.vo;

public class UserVO {

    private int id;
    private String nickName;
    private String imgUrl;
    private int isMachine;

    public int getIsMachine() {
        return isMachine;
    }

    public void setIsMachine(int isMachine) {
        this.isMachine = isMachine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
