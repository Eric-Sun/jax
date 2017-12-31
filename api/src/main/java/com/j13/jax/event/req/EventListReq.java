package com.j13.jax.event.req;

import com.j13.poppy.anno.Parameter;

public class EventListReq {

    @Parameter(desc=" family id")
    private int familyId;
    @Parameter(desc="页数")
    private int pageNum;
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }



    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }
}
