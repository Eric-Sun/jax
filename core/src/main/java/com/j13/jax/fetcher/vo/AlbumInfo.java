package com.j13.jax.fetcher.vo;

/**
 * 用于存储v3中的album
 */
public class AlbumInfo {

    /**
     * 源的类型
     */
    private int sourceId;
    /**
     * 在源中对应的album的id
     */
    private int remoteAlbumId;
    private int tagId;
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
