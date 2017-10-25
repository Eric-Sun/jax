package com.j13.jax.user.resp;

import com.j13.poppy.anno.Parameter;

public class ImgUploadResp {
    @Parameter(desc="图片存储的id")
    private int imgId;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
