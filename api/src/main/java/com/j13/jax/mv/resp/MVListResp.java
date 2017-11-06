package com.j13.jax.mv.resp;

import com.google.common.collect.Lists;
import com.j13.poppy.anno.Parameter;

import java.util.List;

public class MVListResp {

    @Parameter(desc="")
    private List<MVSimpleGetResp> list = Lists.newLinkedList();

    public List<MVSimpleGetResp> getList() {
        return list;
    }

    public void setList(List<MVSimpleGetResp> list) {
        this.list = list;
    }
}
