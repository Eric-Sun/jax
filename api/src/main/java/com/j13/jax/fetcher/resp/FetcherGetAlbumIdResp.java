package com.j13.jax.fetcher.resp;

import com.j13.poppy.anno.Parameter;

public class FetcherGetAlbumIdResp {
    @Parameter(desc="存储在v3中的albumId")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
