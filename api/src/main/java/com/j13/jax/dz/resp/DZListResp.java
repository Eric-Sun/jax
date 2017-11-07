package com.j13.jax.dz.resp;

import com.google.common.collect.Lists;
import com.j13.poppy.anno.Parameter;

import java.util.List;

public class DZListResp {
    @Parameter(desc = "")
    private List<DZGetResp> list = Lists.newLinkedList();

    public List<DZGetResp> getList() {
        return list;
    }

    public void setList(List<DZGetResp> list) {
        this.list = list;
    }
}
