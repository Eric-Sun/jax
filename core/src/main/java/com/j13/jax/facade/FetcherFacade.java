package com.j13.jax.facade;

import com.j13.jax.dao.MVAlbumDAO;
import com.j13.jax.dao.MVImgDAO;
import com.j13.jax.event.req.EventGetAlbumReq;
import com.j13.jax.event.resp.EventGetAlbumResp;
import com.j13.jax.service.FetcherSourceService;
import com.j13.jax.dao.FetchIndexDAO;
import com.j13.jax.fetcher.req.*;
import com.j13.jax.fetcher.resp.FetcherMVAlbumAddResp;
import com.j13.jax.fetcher.resp.FetcherGetMVAlbumIdResp;
import com.j13.jax.fetcher.resp.FetcherGetLastIndexResp;
import com.j13.jax.vo.MVAlbumVO;
import com.j13.jax.vo.MVImgVO;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.core.CommonResultResp;
import com.j13.poppy.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetcherFacade {

    private static Logger LOG = LoggerFactory.getLogger(FetcherFacade.class);

    @Autowired
    MVAlbumDAO MVAlbumDAO;
    @Autowired
    FetcherSourceService fetcherSourceService;
    @Autowired
    MVImgDAO MVImgDAO;
    @Autowired
    FetchIndexDAO fetchIndexDAO;

    @Action(name = "fetcher.mvAlbumAdd", desc = "创建一个mv_album")
    public FetcherMVAlbumAddResp mvAlbumAdd(CommandContext ctxt, FetcherMVAlbumAddReq req) {
        FetcherMVAlbumAddResp resp = new FetcherMVAlbumAddResp();
        int sourceId = req.getSourceId();
        int remoteAlbumId = req.getRemoteAlbumId();
        String title = req.getTitle();
        int tagId = req.getTagId();
        fetcherSourceService.check(sourceId);

        MVAlbumVO ai = new MVAlbumVO();
        ai.setRemoteAlbumId(remoteAlbumId);
        ai.setSourceId(sourceId);
        ai.setTitle(title);
        ai.setTagId(tagId);

        int id = MVAlbumDAO.addMVAlbum(ai);
        LOG.info("add mv_album success. id={}", id);
        resp.setMvAlbumId(id);
        return resp;
    }


    @Action(name = "fetcher.checkMVAlbumExist", desc = "检测这个mvalbum是否存在")
    public CommonResultResp checkMVAlbumExist(CommandContext ctxt, FetcherCheckMVAlbumExistReq req) {
        int remoteAlbumId = req.getRemoteAlbumId();
        if (MVAlbumDAO.checkMVAlbumExist(remoteAlbumId)) {
            return CommonResultResp.success();
        } else {
            return CommonResultResp.failure();
        }
    }

    @Action(name = "fetcher.getMVAlbumId", desc = "获取v3中的mvAlbumId")
    public FetcherGetMVAlbumIdResp getMVAlbumId(CommandContext ctxt, FetcherGetMVAlbumIdReq req) {
        FetcherGetMVAlbumIdResp r = new FetcherGetMVAlbumIdResp();
        int remoteAlbumId = req.getRemoteAlbumId();

        int mvAlbumId = MVAlbumDAO.getMVAlbumId(remoteAlbumId);
        r.setId(mvAlbumId);
        return r;
    }

    @Action(name = "fetcher.mvImgAdd", desc = "添加mv_img")
    public CommonResultResp mvImgAdd(CommandContext ctxt, FetcherMVImgAddReq req) {
        MVImgVO rii = new MVImgVO();
        rii.setMvAlbumId(req.getMvAlbumId());
        rii.setRelationLocalPath(req.getRelationLocalPath());
        rii.setRemoteImgId(req.getRemoteImgId());
        rii.setRemoteImgUrl(req.getRemoteUrl());
        int id = MVImgDAO.add(rii);
        LOG.info("mv_img add success. id={}", id);
        return CommonResultResp.success();
    }

    @Action(name = "fetcher.checkMVImgExist", desc = "查看mv_img是否存在")
    public CommonResultResp checkMVImgExist(CommandContext ctxt, FetcherCheckMVImgExistByRemoteUrlReq req) {
        String remoteImgUrl = req.getRemoteImgUrl();
        boolean exist = MVImgDAO.checkImgExist(remoteImgUrl);
        if (exist)
            return CommonResultResp.success();
        else
            return CommonResultResp.failure();
    }


    @Action(name = "fetcher.updateLastIndex", desc = "更新fetch源的最后一个位置")
    public CommonResultResp updateLastIndex(CommandContext ctxt, FetcherUpdateLastIndexReq req) {
        int sourceId = req.getSourceId();
        int lastIndex = req.getLastIndex();

        fetchIndexDAO.insert(sourceId, lastIndex);
        return CommonResultResp.success();
    }

    @Action(name = "fetcher.getLastIndex", desc = "获取上次的最后一次的索引位置")
    public FetcherGetLastIndexResp getLastIndex(CommandContext ctxt, FetcherGetLastIndexReq req) {
        int sourceId = req.getSourceId();
        FetcherGetLastIndexResp resp = new FetcherGetLastIndexResp();
        if (fetchIndexDAO.checkExist(sourceId)) {
            int index = fetchIndexDAO.getLastIndex(sourceId);
            resp.setIndex(index);
        } else {
            resp.setIndex(0);

        }
        return resp;
    }


    @Action(name = "fetcher.getMVAlbum", desc = "")
    public EventGetAlbumResp getMVAlbum(CommandContext ctxt, EventGetAlbumReq req) {
        int albumId = req.getMvAlbumId();
        EventGetAlbumResp resp = new EventGetAlbumResp();
        MVAlbumVO ai = MVAlbumDAO.getMVAlbum(albumId);

        BeanUtils.copyProperties(resp, ai);
        return resp;
    }
}
