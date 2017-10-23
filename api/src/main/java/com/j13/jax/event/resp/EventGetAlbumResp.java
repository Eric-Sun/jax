package com.j13.jax.event.resp;

import com.j13.poppy.anno.Parameter;

public class EventGetAlbumResp {
    @Parameter(desc = "")
    private String title;
    @Parameter(desc = "")
    private int sourceId;
    @Parameter(desc = "")
    private String sourceName;
    @Parameter(desc = "")
    private String tagId;
    @Parameter(desc = "")
    private String tagName;
    @Parameter(desc = "")
    private int remoteAlbumId;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
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

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
