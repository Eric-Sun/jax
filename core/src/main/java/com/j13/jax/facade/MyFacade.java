package com.j13.jax.facade;

import com.j13.jax.core.Constants;
import com.j13.jax.core.PropertiesKey;
import com.j13.jax.dao.*;
import com.j13.jax.dz.resp.DZGetResp;
import com.j13.jax.mv.resp.MVSimpleGetResp;
import com.j13.jax.my.req.MyCollectionListReq;
import com.j13.jax.my.resp.CollectionGetResp;
import com.j13.jax.my.resp.MyCollectionListResp;
import com.j13.jax.service.MVHelper;
import com.j13.jax.vo.*;
import com.j13.poppy.anno.Action;
import com.j13.poppy.config.PropertiesConfiguration;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;


/**
 * 我的页面中的服务
 */
@Component
public class MyFacade {

    @Autowired
    CollectionDAO collectionDAO;
    @Autowired
    EventDAO eventDAO;
    @Autowired
    MVHelper MVHelper;
    @Autowired
    DZDAO dzdao;
    @Autowired
    UserDAO userDAO;
    @Autowired
    MVAlbumDAO mvAlbumDAO;
    @Autowired
    MVHelper mvHelper;


    @Action(name = "my.collectionList", desc = "")
    public MyCollectionListResp collectionList(CommandContext ctxt, MyCollectionListReq req) {
        MyCollectionListResp listResp = new MyCollectionListResp();
        int userId = ctxt.getUid();
        List<CollectionVO> list = collectionDAO.list(userId, req.getPageNum(), 5);
        for (CollectionVO vo : list) {
            CollectionGetResp getResp = new CollectionGetResp();
            BeanUtils.copyProperties(getResp, vo);

            if (vo.getType() == Constants.CollectionType.DZ) {
                // comment
                DZGetResp dzResp = new DZGetResp();
                DZVO dzvo = dzdao.get(vo.getResourceId());
                BeanUtils.copyProperties(dzResp, dzvo);
                UserVO userVO = userDAO.getUserInfo(dzvo.getUserId());
                String userHeadFileName = userVO.getImg();
                String userHeadUrl = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.USER_HEAD_PATH) + File.separator + userHeadFileName;

                BeanUtils.copyProperties(getResp, dzvo);
                dzResp.setUserHeadUrl(userHeadUrl);
                dzResp.setUserName(userVO.getNickName());
                getResp.setResourceObj(dzResp);
                getResp.setType(Constants.EventType.DZ);
            }
            if (vo.getType() == Constants.CollectionType.MV) {
                MVAlbumVO mvAlbumVO = mvAlbumDAO.getMVAlbum(vo.getResourceId());
                UserVO userVO = userDAO.getUserInfo(mvAlbumVO.getUserId());
                String userHeadFileName = userVO.getImg();
                String userHeadUrl = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.USER_HEAD_PATH) + File.separator + userHeadFileName;
                MVSimpleGetResp mvResp = new MVSimpleGetResp();

                TripleImgContentVO ticVO = mvHelper.getMVTriplerImgContent(mvAlbumVO);
                mvResp.setContent(ticVO);
                mvResp.setType(Constants.EventType.MEINV);
                mvResp.setUserHeadUrl(userHeadUrl);
                mvResp.setTitle(ticVO.getTitle());
                getResp.setResourceObj(ticVO);
                getResp.setType(Constants.CollectionType.MV);
            } else {
//                // event
//                EventVO eventVO = eventDAO.get(vo.getResourceId());
//                TripleImgContentVO tripleImgContentVO = MVHelper.getMVTriplerImgContent(eventVO);
//                getResp.setResourceObj(tripleImgContentVO);
            }
            listResp.getList().add(getResp);
        }

        return listResp;
    }


}
