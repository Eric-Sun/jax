package com.j13.jax.vo;

/**
 * 用于存储v3中的album
 */
public class MVAlbumVO {

    /**
     * 源的类型
     */
    private int sourceId;
    /**
     * 在源中对应的album的id
     */
    private int remoteAlbumId;
    private int tagId;
    private String sourceName;
    private String tagName;
    private String title;
    private int userId;
    private int id;
    private int praise;
    private int share;
    private String userName;
    private String userImgUrl;

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRemoteAlbumId() {
        return remoteAlbumId;
    }

    public void setRemoteAlbumId(int remoteAlbumId) {
        this.remoteAlbumId = remoteAlbumId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
