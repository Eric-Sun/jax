package com.j13.jax.facade;

import com.j13.jax.core.Constants;
import com.j13.jax.core.ErrorCode;
import com.j13.jax.core.PropertiesKey;
import com.j13.jax.dao.*;
import com.j13.jax.event.req.*;
import com.j13.jax.event.resp.EventGetResp;
import com.j13.jax.event.resp.EventListResp;
import com.j13.jax.event.resp.EventSimpleGetResp;
import com.j13.jax.service.MVHelper;
import com.j13.jax.service.ImgHelper;
import com.j13.jax.vo.*;
import com.j13.poppy.anno.Action;
import com.j13.poppy.config.PropertiesConfiguration;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.core.CommonResultResp;
import com.j13.poppy.exceptions.CommonException;
import com.j13.poppy.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

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
    @Autowired
    ImgHelper imgHelper;
    @Autowired
    CollectionDAO collectionDAO;
    @Autowired
    MVHelper mvHelper;
    @Autowired
    FamilyDAO familyDAO;




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
        familyDAO.changeTopicCount(req.getFamilyId(), 1);

        return CommonResultResp.success();
    }




    @Action(name = "event.userAction", desc = "")
    public CommonResultResp userAction(CommandContext ctxt, EventUserActionReq req) {
        if (req.getUserActionType() == Constants.UserAction.PRAISE) {
            eventDAO.praise(req.getEventId());
            LOG.info("event praise. userId={},eventId={}", ctxt.getUid(), req.getEventId());
        } else {
            eventDAO.share(req.getEventId());
            LOG.info("event share. userId={},eventId={}", ctxt.getUid(), req.getEventId());
        }
        return CommonResultResp.success();
    }


    @Action(name = "event.collect", desc = "")
    public CommonResultResp collect(CommandContext ctxt, EventCollectReq req) {
        int eventId = req.getEventId();
        if (collectionDAO.checkExists(ctxt.getUid(), req.getEventId(), Constants.CollectionType.EVENT)) {
            //error
            throw new CommonException(ErrorCode.Event.EVENT_COLLECTION_EXIST);
        }

        collectionDAO.add(ctxt.getUid(), Constants.CollectionType.EVENT, eventId);
        LOG.info("add collection . type=event, eventId={},userId", eventId, ctxt.getUid());
        return CommonResultResp.success();
    }
}
