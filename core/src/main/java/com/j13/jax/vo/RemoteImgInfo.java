package com.j13.jax.vo;

import java.lang.String;


/**
 * 对应v3中存储的img对象
 */
public class RemoteImgInfo {
    /**
     * 源中对应的url，用于重复抓取或者客户端直接使用
     */
    private String remoteImgUrl;
    /**
     * 对应v3中的albumid
     */
    private int albumId;
    /**
     * 源中对应的imgid，对于源来讲remoteImgId和remoteAlbumId对应了一张图片
     */
    private int remoteImgId;
    /**
     * 本地存储路径，相对路径，不包含source
     */
    private String relationLocalPath;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getRelationLocalPath() {
        return relationLocalPath;
    }

    public void setRelationLocalPath(String relationLocalPath) {
        this.relationLocalPath = relationLocalPath;
    }

    public int getRemoteImgId() {
        return remoteImgId;
    }

    public void setRemoteImgId(int remoteImgId) {
        this.remoteImgId = remoteImgId;
    }

    public String getRemoteImgUrl() {
        return remoteImgUrl;
    }

    public void setRemoteImgUrl(String remoteImgUrl) {
        this.remoteImgUrl = remoteImgUrl;
    }
}
