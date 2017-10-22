package com.j13.jax.fetcher.req;

import com.j13.poppy.anno.Parameter;

public class FetcherImgAddReq {
    @Parameter(desc="v3的albumId")
    private int albumId;
    @Parameter(desc="存储服务器相对路径，不包含source对应的路径")
    private String relationLocalPath;
    @Parameter(desc="源中对应的imgId")
    private int remoteImgId;
    @Parameter(desc="源中对应的url地址")
    private String remoteUrl;

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

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }
}
