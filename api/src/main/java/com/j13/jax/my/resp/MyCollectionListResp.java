package com.j13.jax.my.resp;

import com.google.common.collect.Lists;
import com.j13.poppy.anno.Parameter;

import java.util.List;

public class MyCollectionListResp {
    @Parameter(desc = "")
    private List<CollectionGetResp> list = Lists.newLinkedList();

    public List<CollectionGetResp> getList() {
        return list;
    }

    public void setList(List<CollectionGetResp> list) {
        this.list = list;
    }
}
