package com.j13.jax.message.req;

import com.j13.poppy.anno.Parameter;

public class MessageSearchReq {
    @Parameter(desc="接受者uid")
    private int toUserId;
    @Parameter(desc="分页页码")
    private int pageNum;
    @Parameter(desc="每页的条数")
    private int sizePerPage;

    public int getSizePerPage() {
        return sizePerPage;
    }

    public void setSizePerPage(int sizePerPage) {
        this.sizePerPage = sizePerPage;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
