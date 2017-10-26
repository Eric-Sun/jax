package com.j13.jax.fetcher.req;

import com.j13.poppy.anno.NeedSet;
import com.j13.poppy.anno.Parameter;

public class FetcherGetMVAlbumIdReq {
    @Parameter(desc = "源中album的id")
    @NeedSet
    private int remoteAlbumId;

    public int getRemoteAlbumId() {
        return remoteAlbumId;
    }

    public void setRemoteAlbumId(int remoteAlbumId) {
        this.remoteAlbumId = remoteAlbumId;
    }
}
