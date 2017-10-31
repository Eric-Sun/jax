package com.j13.jax.family.req;

import com.j13.poppy.anno.Parameter;
import org.apache.commons.fileupload.FileItem;

public class FamilyAddReq {
    @Parameter(desc="家族名称")
    private String name;
    @Parameter(desc="头像")
    private String headImgId;
    @Parameter(desc="背景图片")
    private String coverImgId;
    @Parameter(desc="简介")
    private String brief;


    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCoverImgId() {
        return coverImgId;
    }

    public void setCoverImgId(String coverImgId) {
        this.coverImgId = coverImgId;
    }

    public String getHeadImgId() {
        return headImgId;
    }

    public void setHeadImgId(String headImgId) {
        this.headImgId = headImgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
