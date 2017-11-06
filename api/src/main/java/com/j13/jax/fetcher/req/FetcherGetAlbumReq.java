package com.j13.jax.fetcher.req;

import com.j13.poppy.anno.Parameter;

public class FetcherGetAlbumReq {
    @Parameter(desc="")
    private int mvAlbumId;

    public int getMvAlbumId() {
        return mvAlbumId;
    }

    public void setMvAlbumId(int mvAlbumId) {
        this.mvAlbumId = mvAlbumId;
    }
}
