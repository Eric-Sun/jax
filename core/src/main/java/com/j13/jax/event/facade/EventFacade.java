package com.j13.jax.event.facade;

import com.alibaba.fastjson.JSON;
import com.j13.jax.core.Constants;
import com.j13.jax.core.PropertiesKey;
import com.j13.jax.event.dao.AlbumDAO;
import com.j13.jax.event.dao.EventDAO;
import com.j13.jax.event.dao.ImgDAO;
import com.j13.jax.event.dao.SystemFamilyCursorDAO;
import com.j13.jax.event.req.EventAddReq;
import com.j13.jax.event.req.EventGetReq;
import com.j13.jax.event.req.EventListReq;
import com.j13.jax.event.resp.EventGetResp;
import com.j13.jax.event.resp.EventListResp;
import com.j13.jax.event.resp.EventSimpleGetResp;
import com.j13.jax.event.vo.EventVO;
import com.j13.jax.event.vo.ImgVO;
import com.j13.jax.event.vo.TripleDetailContent;
import com.j13.jax.event.vo.TripleImgContentVO;
import com.j13.jax.fetcher.vo.AlbumInfo;
import com.j13.jax.user.dao.UserDAO;
import com.j13.jax.user.vo.UserVO;
import com.j13.poppy.anno.Action;
import com.j13.poppy.config.PropertiesConfiguration;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.core.CommonResultResp;
import com.j13.poppy.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventFacade {
    private static Logger LOG = LoggerFactory.getLogger(EventFacade.class);

    @Autowired
    SystemFamilyCursorDAO systemFamilyCursorDAO;
    @Autowired
    AlbumDAO albumDAO;
    @Autowired
    EventDAO eventDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    ImgDAO imgDAO;


    @Action(name = "event.list", desc = "event list in family.")
    public EventListResp list(CommandContext ctxt, EventListReq req) {
        EventListResp resp = new EventListResp();
        int userId = ctxt.getUid();

        // 如果是系统默认的家族，需要查询cursor
        int familyId = req.getFamilyId();
        if (familyId == Constants.SystemFamily.MEINV) {
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
                int albumId = new Integer(eventVO.getContent());
                EventSimpleGetResp getResp = new EventSimpleGetResp();

                String img1 = getImgUrl(albumId, 1);
                String img2 = getImgUrl(albumId, 2);
                String img3 = getImgUrl(albumId, 3);

                TripleImgContentVO vo = new TripleImgContentVO();
                vo.setImg1Url(img1);
                vo.setImg2Url(img2);
                vo.setImg3Url(img3);

                getResp.setContent(JSON.toJSONString(vo));
                getResp.setType(Constants.EventType.TRIPLE_IMG);
                getResp.setTitle("");
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
        if (req.getFamilyId() == Constants.SystemFamily.MEINV) {

            EventVO vo = eventDAO.get(req.getEventId());
            UserVO userVO = userDAO.getUserNameAndImg(vo.getUserId());

            vo.setUserName(userVO.getNickName());
            vo.setUserImgUrl(getUserImgUrl(userVO.getImg()));
            int albumId = new Integer(vo.getContent());
            List<ImgVO> imgList = imgDAO.list(albumId);
            String title = albumDAO.getAlbumTitle(albumId);
            TripleDetailContent c = new TripleDetailContent();
            for (ImgVO imgVO : imgList) {
                String url = getImgUrl(albumId, imgVO.getRemoteImgId());
                c.getImgList().add(url);
            }

            BeanUtils.copyProperties(resp, vo);
            resp.setContent(JSON.toJSONString(c));
            resp.setTitle(title);
        }

        return resp;
    }
}
