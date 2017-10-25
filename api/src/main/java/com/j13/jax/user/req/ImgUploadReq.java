package com.j13.jax.user.req;

import com.j13.poppy.anno.Parameter;
import org.apache.commons.fileupload.FileItem;

public class ImgUploadReq {
    @Parameter(desc="图片内容")
    private FileItem file;
    @Parameter(desc="图片的类型，0为event发布，1为family发布时候的headImg，2为family发布的时候的coverImg，3为用户的头像")
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public FileItem getFile() {
        return file;
    }

    public void setFile(FileItem file) {
        this.file = file;
    }
}
