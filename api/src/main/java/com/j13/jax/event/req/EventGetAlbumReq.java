package com.j13.jax.event.req;

import com.j13.poppy.anno.Parameter;

public class EventGetAlbumReq {
    @Parameter(desc="")
    private int mvAlbumId;

    public int getMvAlbumId() {
        return mvAlbumId;
    }

    public void setMvAlbumId(int mvAlbumId) {
        this.mvAlbumId = mvAlbumId;
    }
}
