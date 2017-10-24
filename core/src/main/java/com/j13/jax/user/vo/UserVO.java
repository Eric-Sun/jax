package com.j13.jax.user.vo;

public class UserVO {

    private int id;
    private String nickName;
    private String img;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
