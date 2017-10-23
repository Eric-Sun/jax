package com.j13.jax.event.req;

import com.j13.poppy.anno.Parameter;

public class EventGetAlbumReq {
    @Parameter(desc="")
    private int albumId;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }
}
