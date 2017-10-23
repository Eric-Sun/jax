package com.j13.jax.fetcher.resp;

import com.j13.poppy.anno.Parameter;

public class FetcherAlbumAddResp {
    @Parameter(desc="")
    private int albumId;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
