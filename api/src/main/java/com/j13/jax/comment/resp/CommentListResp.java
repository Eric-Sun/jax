package com.j13.jax.comment.resp;

import com.google.common.collect.Lists;
import com.j13.poppy.anno.Parameter;

import java.util.List;

public class CommentListResp {
    @Parameter(desc="")
    private List<CommentGetResp> list = Lists.newLinkedList();

    public List<CommentGetResp> getList() {
        return list;
    }

    public void setList(List<CommentGetResp> list) {
        this.list = list;
    }
}
