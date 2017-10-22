package com.j13.jax.fetcher.facade;

import com.j13.jax.event.dao.AlbumDAO;
import com.j13.jax.event.dao.ImgDAO;
import com.j13.jax.fetcher.FetcherSourceService;
import com.j13.jax.fetcher.dao.FetchIndexDAO;
import com.j13.jax.fetcher.req.*;
import com.j13.jax.fetcher.resp.FetcherGetAlbumIdResp;
import com.j13.jax.fetcher.resp.FetcherGetLastIndexResp;
import com.j13.jax.fetcher.vo.AlbumInfo;
import com.j13.jax.fetcher.vo.RemoteImgInfo;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.core.CommonResultResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetcherFacade {

    private static Logger LOG = LoggerFactory.getLogger(FetcherFacade.class);

    @Autowired
    AlbumDAO albumDAO;
    @Autowired
    FetcherSourceService fetcherSourceService;
    @Autowired
    ImgDAO imgDAO;
    @Autowired
    FetchIndexDAO fetchIndexDAO;

    @Action(name = "fetcher.albumAdd", desc = "创建一个album")
    public CommonResultResp albumAdd(CommandContext ctxt, FetcherAlbumAddReq req) {
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

        int id = albumDAO.addAlbum(ai);
        LOG.info("add album success. id={}", id);
        return CommonResultResp.success();
    }


    @Action(name = "fetcher.checkAlbumExist", desc = "检测这个album是否存在")
    public CommonResultResp checkAlbumExist(CommandContext ctxt, FetcherCheckAlbumExistReq req) {
        int remoteAlbumId = req.getRemoteAlbumId();
        if (albumDAO.checkAlbumExist(remoteAlbumId)) {
            return CommonResultResp.success();
        } else {
            return CommonResultResp.failure();
        }
    }

    @Action(name = "fetcher.getAlbumId", desc = "获取v3中的albumId")
    public FetcherGetAlbumIdResp getAlbumId(CommandContext ctxt, FetcherGetAlbumIdReq req) {
        FetcherGetAlbumIdResp r = new FetcherGetAlbumIdResp();
        int remoteAlbumId = req.getRemoteAlbumId();

        int albumId = albumDAO.getAlbumId(remoteAlbumId);
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
        int id = imgDAO.add(rii);
        LOG.info("img add success. id={}", id);
        return CommonResultResp.success();
    }

    @Action(name = "fetcher.checkImgExist", desc = "查看img是否存在")
    public CommonResultResp checkImgExist(CommandContext ctxt, FetcherCheckImgExistByRemoteUrlReq req) {
        String remoteImgUrl = req.getRemoteImgUrl();
        boolean exist = imgDAO.checkImgExist(remoteImgUrl);
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
}
