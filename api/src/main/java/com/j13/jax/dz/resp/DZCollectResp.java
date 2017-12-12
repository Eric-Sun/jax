package com.j13.jax.dz.resp;

import com.j13.poppy.anno.Parameter;

public class DZCollectResp {
    @Parameter(desc = "")
    private int collectionId;

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }
}
