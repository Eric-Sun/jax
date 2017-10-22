package com.j13.jax.fetcher.req;

import com.j13.poppy.anno.Parameter;

public class FetcherAlbumAddReq {
    @Parameter(desc="源id")
    private int sourceId;
    @Parameter(desc="源中album的id")
    private int remoteAlbumId;
    @Parameter(desc="tagid")
    private int tagId;
    @Parameter(desc="album的标题")
    private String title;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRemoteAlbumId() {
        return remoteAlbumId;
    }

    public void setRemoteAlbumId(int remoteAlbumId) {
        this.remoteAlbumId = remoteAlbumId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
