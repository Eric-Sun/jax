package com.j13.jax.service;

import com.j13.jax.dao.MVAlbumDAO;
import com.j13.jax.event.resp.EventSimpleGetResp;
import com.j13.jax.vo.EventVO;
import com.j13.jax.vo.MVAlbumVO;
import com.j13.jax.vo.TripleImgContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用来处理公用的event相关的方法
 */
@Service
public class EventHelper {

    @Autowired
    MVAlbumDAO mvAlbumDAO;
    @Autowired
    ImgHelper imgHelper;


    public TripleImgContentVO getMVTriplerImgContent(EventVO eventVO) {
        int mvAlbumId = new Integer(eventVO.getContent());
        MVAlbumVO ai = mvAlbumDAO.getMVAlbum(mvAlbumId);
        int remoteAlbumId = ai.getRemoteAlbumId();

        String img1 = imgHelper.getEventImgUrl(remoteAlbumId, 1);
        String img2 = imgHelper.getEventImgUrl(remoteAlbumId, 2);
        String img3 = imgHelper.getEventImgUrl(remoteAlbumId, 3);

        TripleImgContentVO vo = new TripleImgContentVO();
        vo.setImg1Url(img1);
        vo.setImg2Url(img2);
        vo.setImg3Url(img3);
        vo.setTitle(ai.getTitle());
        return vo;
    }


}
