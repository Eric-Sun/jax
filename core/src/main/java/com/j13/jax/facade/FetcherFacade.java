package com.j13.jax.facade;

import com.j13.jax.dao.MVAlbumDAO;
import com.j13.jax.dao.MVImgDAO;
import com.j13.jax.event.req.EventGetAlbumReq;
import com.j13.jax.event.resp.EventGetAlbumResp;
import com.j13.jax.service.FetcherSourceService;
import com.j13.jax.dao.FetchIndexDAO;
import com.j13.jax.fetcher.req.*;
import com.j13.jax.fetcher.resp.FetcherAlbumAddResp;
import com.j13.jax.fetcher.resp.FetcherGetAlbumIdResp;
import com.j13.jax.fetcher.resp.FetcherGetLastIndexResp;
import com.j13.jax.vo.AlbumInfo;
import com.j13.jax.vo.RemoteImgInfo;
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

    @Action(name = "fetcher.albumAdd", desc = "创建一个album")
    public FetcherAlbumAddResp albumAdd(CommandContext ctxt, FetcherAlbumAddReq req) {
        FetcherAlbumAddResp resp = new FetcherAlbumAddResp();
        int sourceId = req.getSourceId();
        int remoteAlbumId = req.getRemoteAlbumId();
        String title = req.getTitle();
        int tagId = req.getTagId();
        fetcherSourceService.check(sourceId);

        AlbumInfo ai = new AlbumInfo();
        ai.setRemoteAlbumId(remoteAlbumId);
        ai.setSourceId(sourceId);
        ai.setTitle(title);
        ai.setTagId(tagId);

        int id = MVAlbumDAO.addAlbum(ai);
        LOG.info("add album success. id={}", id);
        resp.setAlbumId(id);
        return resp;
    }


    @Action(name = "fetcher.checkAlbumExist", desc = "检测这个album是否存在")
    public CommonResultResp checkAlbumExist(CommandContext ctxt, FetcherCheckAlbumExistReq req) {
        int remoteAlbumId = req.getRemoteAlbumId();
        if (MVAlbumDAO.checkAlbumExist(remoteAlbumId)) {
            return CommonResultResp.success();
        } else {
            return CommonResultResp.failure();
        }
    }

    @Action(name = "fetcher.getAlbumId", desc = "获取v3中的albumId")
    public FetcherGetAlbumIdResp getAlbumId(CommandContext ctxt, FetcherGetAlbumIdReq req) {
        FetcherGetAlbumIdResp r = new FetcherGetAlbumIdResp();
        int remoteAlbumId = req.getRemoteAlbumId();

        int albumId = MVAlbumDAO.getAlbumId(remoteAlbumId);
        r.setId(albumId);
        return r;
    }

    @Action(name = "fetcher.imgAdd", desc = "添加img")
    public CommonResultResp imgAdd(CommandContext ctxt, FetcherImgAddReq req) {
        RemoteImgInfo rii = new RemoteImgInfo();
        rii.setAlbumId(req.getAlbumId());
        rii.setRelationLocalPath(req.getRelationLocalPath());
        rii.setRemoteImgId(req.getRemoteImgId());
        rii.setRemoteImgUrl(req.getRemoteUrl());
        int id = MVImgDAO.add(rii);
        LOG.info("img add success. id={}", id);
        return CommonResultResp.success();
    }

    @Action(name = "fetcher.checkImgExist", desc = "查看img是否存在")
    public CommonResultResp checkImgExist(CommandContext ctxt, FetcherCheckImgExistByRemoteUrlReq req) {
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


    @Action(name = "fetcher.getAlbum", desc = "")
    public EventGetAlbumResp getAlbum(CommandContext ctxt, EventGetAlbumReq req) {
        int albumId = req.getAlbumId();
        EventGetAlbumResp resp = new EventGetAlbumResp();
        AlbumInfo ai = MVAlbumDAO.getAlbum(albumId);

        BeanUtils.copyProperties(resp, ai);
        return resp;
    }
}