package com.j13.jax.event.resp;

import com.google.common.collect.Lists;
import com.j13.poppy.anno.Parameter;

import java.util.List;

public class EventListResp {

    @Parameter(desc="事件列表，其实包含多种事件")
    private List<EventSimpleGetResp> list = Lists.newLinkedList();

    public List<EventSimpleGetResp> getList() {
        return list;
    }

    public void setList(List<EventSimpleGetResp> list) {
        this.list = list;
    }
}
