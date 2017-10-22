package com.j13.jax.family.req;

import com.j13.poppy.anno.Parameter;
import org.apache.commons.fileupload.FileItem;

public class FamilyUpdateReq {
    @Parameter(desc="家族id")
    private int familyId;
    @Parameter(desc="简介")
    private String brief;
    @Parameter(desc="头像图片")
    private FileItem headImg;
    @Parameter(desc="背景图片")
    private FileItem coverImg;
    @Parameter(desc="拥有者用户id")
    private int ownerUserId;

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public FileItem getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(FileItem coverImg) {
        this.coverImg = coverImg;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public FileItem getHeadImg() {
        return headImg;
    }

    public void setHeadImg(FileItem headImg) {
        this.headImg = headImg;
    }
}
