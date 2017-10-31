package com.j13.jax.service;

import com.j13.jax.core.PropertiesKey;
import com.j13.poppy.config.PropertiesConfiguration;
import com.j13.poppy.exceptions.ServerException;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

/**
 * 图片存储的app
 */
@Service
public class ImgHelper {

    private static Logger LOG = LoggerFactory.getLogger(ImgHelper.class);
    public static int EVENT_IMG = 0;
    public static int FAMILY_HEADIMG = 1;
    public static int FAMILY_COVERIMG = 2;
    public static int USER_HAED = 3;

    Random ran = new Random();


    private String getFilename() {
        int i = ran.nextInt(1000);
        String fileName = System.currentTimeMillis() + i + ".jpg";
        return fileName;
    }

    public String saveImg(FileItem item, int type) {
        String fileName = getFilename();

        String localFile = null;
        if (type == FAMILY_COVERIMG)
            localFile = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.FAMILY_COVERIMG_PATH) + File.separator + fileName;
        else if (type == FAMILY_HEADIMG)
            localFile = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.FAMILY_HEADIMG_PATH) + File.separator + fileName;
        else if (type == USER_HAED)
            localFile = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.USER_HEAD_PATH) + File.separator + fileName;
        else
            localFile = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.EVENT_IMG_PATH) + File.separator + fileName;


        try {
            InputStream in = item.getInputStream();
            FileOutputStream out = new FileOutputStream(localFile);
            byte buffer[] = new byte[1024];
            //判断输入流中的数据是否已经读完的标识
            int len = 0;
            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
            while ((len = in.read(buffer)) > 0) {
                //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                out.write(buffer, 0, len);
            }
            //关闭输入流
            in.close();
            //关闭输出流
            out.close();
            //删除处理文件上传时生成的临时文件
            item.delete();
        } catch (Exception e) {
            throw new ServerException("upload file .", e);
        }

        LOG.info("file saved. type={},path={}", type, localFile);

        return fileName;
    }


    public String getUserHeadUrl(String imgUrl) {
        return PropertiesConfiguration.getInstance().getStringValue("img.server") + "/" + imgUrl;
    }

    public String getFamilyHeadUrl(String imgUrl) {
        return PropertiesConfiguration.getInstance().getStringValue("family.headImg.path") + "/" + imgUrl;
    }

    public String getFamilyCoverUrl(String imgUrl) {
        return PropertiesConfiguration.getInstance().getStringValue("family.coverImg.path") + "/" + imgUrl;
    }



    public String getEventImgUrl(int albumId, int imgId) {
        String server = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.EVENT_IMG_PATH);
        return new StringBuilder().append(server).append("/").append(albumId).append("/").append(imgId).append(".jpg").toString();
    }

}
