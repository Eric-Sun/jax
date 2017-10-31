package com.j13.jax.my.req;

import com.j13.poppy.anno.Parameter;

public class MyCollectionListReq {
    @Parameter(desc="")
    private int pageNum;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
