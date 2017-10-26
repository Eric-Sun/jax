package com.j13.jax.fetcher.resp;

import com.j13.poppy.anno.Parameter;

public class FetcherMVAlbumAddResp {
    @Parameter(desc="")
    private int mvAlbumId;

    public int getMvAlbumId() {
        return mvAlbumId;
    }

    public void setMvAlbumId(int mvAlbumId) {
        this.mvAlbumId = mvAlbumId;
    }
}
