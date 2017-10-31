package com.j13.jax.family.resp;

import com.j13.poppy.anno.Parameter;

public class FamilyGetResp {
    @Parameter(desc="家族id")
    private int familyId;
    @Parameter(desc="家族名称")
    private String name;
    @Parameter(desc="家族头像地址")
    private String headImgUrl;
    @Parameter(desc="家族背景图地址")
    private String coverImgUrl;
    @Parameter(desc="家族简介")
    private String brief;
    @Parameter(desc="")
    private int memberCount;
    @Parameter(desc="")
    private int topicCount;

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }

    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
