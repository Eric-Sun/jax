package com.j13.jax.facade;

import com.j13.jax.core.Constants;
import com.j13.jax.core.PropertiesKey;
import com.j13.jax.dao.*;
import com.j13.jax.event.req.EventGetReq;
import com.j13.jax.event.req.EventListReq;
import com.j13.jax.event.resp.EventGetResp;
import com.j13.jax.event.resp.EventListResp;
import com.j13.jax.event.resp.EventSimpleGetResp;
import com.j13.jax.mv.req.MVGetReq;
import com.j13.jax.mv.req.MVListReq;
import com.j13.jax.mv.resp.MVGetResp;
import com.j13.jax.mv.resp.MVListResp;
import com.j13.jax.mv.resp.MVSimpleGetResp;
import com.j13.jax.service.ImgHelper;
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

@Component
public class MVFacade {

    @Autowired
    SystemFamilyCursorDAO systemFamilyCursorDAO;
    @Autowired
    com.j13.jax.dao.MVAlbumDAO MVAlbumDAO;
    @Autowired
    EventDAO eventDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    com.j13.jax.dao.MVImgDAO MVImgDAO;
    @Autowired
    ImgHelper imgHelper;
    @Autowired
    CollectionDAO collectionDAO;
    @Autowired
    MVHelper mvHelper;
    @Autowired
    FamilyDAO familyDAO;


    @Action(name = "mv.list", desc = "mv list")
    public MVListResp list(CommandContext ctxt, MVListReq req) {
        MVListResp resp = new MVListResp();
        int userId = ctxt.getUid();

        // 如果是系统默认的家族，需要查询cursor
        int familyId = Constants.EventType.MEINV;
        //美女
        boolean existed = systemFamilyCursorDAO.checkExisted(userId, familyId);
        int fromCursorId = 0;
        if (!existed) {
            fromCursorId = MVImgDAO.getLeastMEINVId();
//                fromCursorId = PropertiesConfiguration.getInstance().getIntValue(PropertiesKey.IMG_FIRST_ID);
            systemFamilyCursorDAO.insert(userId, familyId, fromCursorId);
        } else {
            fromCursorId = systemFamilyCursorDAO.getCursorId(userId, familyId);
        }

        int toCursorId = 0;
        // 获取下N条
        List<MVAlbumVO> list = MVAlbumDAO.MEINVIdList(fromCursorId, 2);
        for (MVAlbumVO mvAlbumVO : list) {
            UserVO userVO = userDAO.getUserInfo(mvAlbumVO.getUserId());
            String userHeadFileName = userVO.getImg();
            String userHeadUrl = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.USER_HEAD_PATH) + File.separator + userHeadFileName;
            MVSimpleGetResp getResp = new MVSimpleGetResp();

//                TripleImgContentVO vo = eventHelper.getMVTriplerImgContent(eventVO);
            TripleImgContentVO vo = mvHelper.getMVTriplerImgContent(mvAlbumVO);
            getResp.setContent(vo);
            getResp.setType(Constants.EventType.MEINV);
            getResp.setUserHeadUrl(userHeadUrl);
            getResp.setTitle(vo.getTitle());
            getResp.setId(mvAlbumVO.getId());
            resp.getList().add(getResp);
            toCursorId = mvAlbumVO.getId();
        }

        // update new cursor
        systemFamilyCursorDAO.update(userId, familyId, toCursorId);

        return resp;
    }


    @Action(name = "mv.get", desc = "")
    public MVGetResp get(CommandContext ctxt, MVGetReq req) {
        MVGetResp resp = new MVGetResp();

        MVAlbumVO mvAlbumVO = MVAlbumDAO.getMVAlbum(req.getMvAlbumId());
        UserVO userVO = userDAO.getUserNameAndImg(mvAlbumVO.getUserId());

        mvAlbumVO.setUserName(userVO.getNickName());
        mvAlbumVO.setUserImgUrl(imgHelper.getUserHeadUrl(userVO.getImg()));
        List<MVImgVO> imgList = MVImgDAO.list(req.getMvAlbumId());
        TripleDetailContent c = new TripleDetailContent();
        for (MVImgVO MVImgVO : imgList) {
            String url = imgHelper.getEventImgUrl(mvAlbumVO.getRemoteAlbumId(), MVImgVO.getRemoteImgId());
            c.getImgList().add(url);
        }
        BeanUtils.copyProperties(resp, mvAlbumVO);
        resp.setContent(c.getImgList());
        resp.setTitle(mvAlbumVO.getTitle());
        resp.setType(-1);
        return resp;
    }

}
