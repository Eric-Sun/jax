package com.j13.jax.facade;

import com.alibaba.fastjson.JSON;
import com.j13.jax.core.Constants;
import com.j13.jax.core.PropertiesKey;
import com.j13.jax.dao.MVAlbumDAO;
import com.j13.jax.dao.EventDAO;
import com.j13.jax.dao.MVImgDAO;
import com.j13.jax.dao.SystemFamilyCursorDAO;
import com.j13.jax.event.req.EventAddReq;
import com.j13.jax.event.req.EventGetReq;
import com.j13.jax.event.req.EventListReq;
import com.j13.jax.event.resp.EventGetResp;
import com.j13.jax.event.resp.EventListResp;
import com.j13.jax.event.resp.EventSimpleGetResp;
import com.j13.jax.vo.EventVO;
import com.j13.jax.vo.MVImgVO;
import com.j13.jax.vo.TripleDetailContent;
import com.j13.jax.vo.TripleImgContentVO;
import com.j13.jax.vo.MVAlbumVO;
import com.j13.jax.dao.UserDAO;
import com.j13.jax.vo.UserVO;
import com.j13.poppy.anno.Action;
import com.j13.poppy.config.PropertiesConfiguration;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.core.CommonResultResp;
import com.j13.poppy.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Properties;

@Component
public class EventFacade {
    private static Logger LOG = LoggerFactory.getLogger(EventFacade.class);

    @Autowired
    SystemFamilyCursorDAO systemFamilyCursorDAO;
    @Autowired
    MVAlbumDAO MVAlbumDAO;
    @Autowired
    EventDAO eventDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    MVImgDAO MVImgDAO;


    @Action(name = "event.list", desc = "event list in family.")
    public EventListResp list(CommandContext ctxt, EventListReq req) {
        EventListResp resp = new EventListResp();
        int userId = ctxt.getUid();

        // 如果是系统默认的家族，需要查询cursor
        int familyId = req.getFamilyId();
        if (familyId == Constants.EventType.MEINV) {
            //美女
            boolean existed = systemFamilyCursorDAO.checkExisted(userId, familyId);
            int fromCursorId = 0;
            if (!existed) {
                fromCursorId = eventDAO.getLeastMEINVId();
//                fromCursorId = PropertiesConfiguration.getInstance().getIntValue(PropertiesKey.IMG_FIRST_ID);
                systemFamilyCursorDAO.insert(userId, familyId, fromCursorId);
            } else {
                fromCursorId = systemFamilyCursorDAO.getCursorId(userId, familyId);
            }

            int toCursorId = 0;
            // 获取下N条
            List<EventVO> list = eventDAO.MEINVIdList(fromCursorId, 2);
            for (EventVO eventVO : list) {
                int eventUserId = eventVO.getUserId();

                UserVO userVO = userDAO.getUserInfo(eventUserId);
                String userHeadFileName = userVO.getImg();
                String userHeadUrl = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.THUMB_SERVER) + File.separator + userHeadFileName;
                int mvAlbumId = new Integer(eventVO.getContent());
                MVAlbumVO ai = MVAlbumDAO.getMVAlbum(mvAlbumId);
                int remoteAlbumId = ai.getRemoteAlbumId();
                EventSimpleGetResp getResp = new EventSimpleGetResp();

                String img1 = getImgUrl(remoteAlbumId, 1);
                String img2 = getImgUrl(remoteAlbumId, 2);
                String img3 = getImgUrl(remoteAlbumId, 3);

                TripleImgContentVO vo = new TripleImgContentVO();
                vo.setImg1Url(img1);
                vo.setImg2Url(img2);
                vo.setImg3Url(img3);

                getResp.setContent(vo);
                getResp.setType(Constants.EventType.MEINV);
                getResp.setUserHeadUrl(userHeadUrl);
                getResp.setTitle(ai.getTitle());
                getResp.setId(eventVO.getId());
                resp.getList().add(getResp);
                toCursorId = eventVO.getId();
            }

            // update new cursor
            systemFamilyCursorDAO.update(userId, familyId, toCursorId);
        }
        return resp;
    }

    private String getImgUrl(int albumId, int imgId) {
        String server = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.IMG_SERVER);
        return new StringBuilder().append(server).append("/").append(albumId).append("/").append(imgId).append(".jpg").toString();
    }


    @Action(name = "event.add", desc = "添加event")
    public CommonResultResp add(CommandContext ctxt, EventAddReq req) {

        EventVO eventVO = new EventVO();
        eventVO.setContent(req.getContent());
        eventVO.setFamilyId(req.getFamilyId());
        eventVO.setTitle(req.getTitle());
        eventVO.setType(req.getType());
        eventVO.setUserId(req.getUserId());

        int eventId = eventDAO.add(eventVO);
        LOG.info("event add successfully. id={}", eventId);
        return CommonResultResp.success();
    }

    private String getUserImgUrl(String imgUrl) {
        return PropertiesConfiguration.getInstance().getStringValue("img.server") + "/" + imgUrl;
    }

    @Action(name = "event.get", desc = "")
    public EventGetResp get(CommandContext ctxt, EventGetReq req) {
        EventGetResp resp = new EventGetResp();
        if (req.getFamilyId() == Constants.EventType.MEINV) {

            EventVO vo = eventDAO.get(req.getEventId());
            UserVO userVO = userDAO.getUserNameAndImg(vo.getUserId());

            vo.setUserName(userVO.getNickName());
            vo.setUserImgUrl(getUserImgUrl(userVO.getImg()));
            int mvAlbumId = new Integer(vo.getContent());
            List<MVImgVO> imgList = MVImgDAO.list(mvAlbumId);
            MVAlbumVO mvAlbumVO = MVAlbumDAO.getMVAlbum(mvAlbumId);
            TripleDetailContent c = new TripleDetailContent();
            for (MVImgVO MVImgVO : imgList) {
                String url = getImgUrl(mvAlbumVO.getRemoteAlbumId(), MVImgVO.getRemoteImgId());
                c.getImgList().add(url);
            }

            BeanUtils.copyProperties(resp, vo);
            resp.setContent(c.getImgList());
            resp.setTitle(mvAlbumVO.getTitle());
        }

        return resp;
    }
}
