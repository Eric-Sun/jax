package com.j13.jax.family.resp;

import com.google.common.collect.Lists;
import com.j13.poppy.anno.Parameter;

import java.util.List;

public class FamilySearchByOwnerResp {
    @Parameter(desc="家族列表")
    private List<FamilyGetResp> list = Lists.newLinkedList();

    public List<FamilyGetResp> getList() {
        return list;
    }

    public void setList(List<FamilyGetResp> list) {
        this.list = list;
    }
}
