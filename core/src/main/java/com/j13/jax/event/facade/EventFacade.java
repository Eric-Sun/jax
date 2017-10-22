package com.j13.jax.event.facade;

import com.alibaba.fastjson.JSON;
import com.j13.jax.core.Constants;
import com.j13.jax.core.PropertiesKey;
import com.j13.jax.event.dao.AlbumDAO;
import com.j13.jax.event.dao.SystemFamilyCursorDAO;
import com.j13.jax.event.req.EventListReq;
import com.j13.jax.event.resp.EventListResp;
import com.j13.jax.event.resp.EventSimpleGetResp;
import com.j13.jax.event.vo.TripleImgContentVO;
import com.j13.poppy.anno.Action;
import com.j13.poppy.config.PropertiesConfiguration;
import com.j13.poppy.core.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventFacade {

    @Autowired
    SystemFamilyCursorDAO systemFamilyCursorDAO;
    @Autowired
    AlbumDAO albumDAO;


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
                fromCursorId = PropertiesConfiguration.getInstance().getIntValue(PropertiesKey.IMG_FIRST_ID);
                systemFamilyCursorDAO.insert(userId, familyId, fromCursorId);
            } else {
                fromCursorId = systemFamilyCursorDAO.getCursorId(userId, familyId);
            }

            // 获取下5条
            List<Integer> list = albumDAO.idList(fromCursorId, 5);
            for (Integer albumId : list) {
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
            }
        }
        return resp;
    }

    private String getImgUrl(int albumId, int imgId) {
        String server = PropertiesConfiguration.getInstance().getStringValue(PropertiesKey.IMG_SERVER);
        return new StringBuilder().append(server).append("/").append(albumId).append("/").append(imgId).append(".jpg").toString();
    }

}
