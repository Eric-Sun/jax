package com.j13.jax.fetcher.req;

import com.j13.poppy.anno.Parameter;

public class FetcherCheckImgExistByRemoteUrlReq {
    @Parameter(desc="")
    private String remoteImgUrl;

    public String getRemoteImgUrl() {
        return remoteImgUrl;
    }

    public void setRemoteImgUrl(String remoteImgUrl) {
        this.remoteImgUrl = remoteImgUrl;
    }
}
