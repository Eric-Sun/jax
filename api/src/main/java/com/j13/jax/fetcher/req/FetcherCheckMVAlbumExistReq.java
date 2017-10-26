package com.j13.jax.fetcher.req;

import com.j13.poppy.anno.Parameter;

public class FetcherCheckMVAlbumExistReq {

    @Parameter(desc="源中album的id")
    private int remoteAlbumId;

    public int getRemoteAlbumId() {
        return remoteAlbumId;
    }

    public void setRemoteAlbumId(int remoteAlbumId) {
        this.remoteAlbumId = remoteAlbumId;
    }
}
