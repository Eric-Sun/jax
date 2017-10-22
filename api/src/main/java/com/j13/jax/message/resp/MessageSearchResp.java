package com.j13.jax.message.resp;

import com.google.common.collect.Lists;
import com.j13.poppy.anno.Parameter;

import java.util.List;

public class MessageSearchResp {
    @Parameter(desc="消息列表")
    private List<MessageGetResp> list = Lists.newLinkedList();

    public List<MessageGetResp> getList() {
        return list;
    }

    public void setList(List<MessageGetResp> list) {
        this.list = list;
    }
}
