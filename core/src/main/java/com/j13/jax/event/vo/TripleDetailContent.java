package com.j13.jax.event.vo;

import com.google.common.collect.Lists;

import java.util.List;

public class TripleDetailContent {
    private List<String> imgList = Lists.newLinkedList();

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
