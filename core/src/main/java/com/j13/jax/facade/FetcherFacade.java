package com.j13.jax.facade;

import com.j13.jax.core.ErrorCode;
import com.j13.jax.dao.*;
import com.j13.jax.fetcher.req.FetcherGetAlbumReq;
import com.j13.jax.fetcher.resp.FetcherGetAlbumResp;
import com.j13.jax.fetcher.resp.*;
import com.j13.jax.service.FetcherSourceService;
import com.j13.jax.fetcher.req.*;
import com.j13.jax.utils.MD5Util;
import com.j13.jax.vo.MVAlbumVO;
import com.j13.jax.vo.MVImgVO;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.core.CommonResultResp;
import com.j13.poppy.exceptions.CommonException;
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
    @Autowired
    DZDAO dzdao;
    @Autowired
    DZCommentDAO dzCommentDAO;

    @Action(name = "fetcher.mvAlbumAdd", desc = "创建一个mv_album")
    public FetcherMVAlbumAddResp mvAlbumAdd(CommandContext ctxt, FetcherMVAlbumAddReq req) {
        FetcherMVAlbumAddResp resp = new FetcherMVAlbumAddResp();
        int sourceId = req.getSourceId();
        int remoteAlbumId = req.getRemoteAlbumId();
        String title = req.getTitle();
        int tagId = req.getTagId();
        int userId = req.getUserId();
        fetcherSourceService.check(sourceId);

        MVAlbumVO ai = new MVAlbumVO();
        ai.setRemoteAlbumId(remoteAlbumId);
        ai.setSourceId(sourceId);
        ai.setTitle(title);
        ai.setTagId(tagId);
        ai.setUserId(userId);

        int id = MVAlbumDAO.addMVAlbum(ai);
        LOG.info("mv_album added. id={}", id);
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
    public FetcherMVImgAddResp mvImgAdd(CommandContext ctxt, FetcherMVImgAddReq req) {
        MVImgVO rii = new MVImgVO();
        rii.setMvAlbumId(req.getMvAlbumId());
        rii.setRelationLocalPath(req.getRelationLocalPath());
        rii.setRemoteImgId(req.getRemoteImgId());
        rii.setRemoteImgUrl(req.getRemoteUrl());
        int id = MVImgDAO.add(rii);
        LOG.info("mvImg added. id={}", id);
        FetcherMVImgAddResp resp = new FetcherMVImgAddResp();
        resp.setMvImgId(id);
        return resp;
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


    @Action(name = "fetcher.updateLastIndex", desc = "更新fetch源的最后一个位置，已经在171103废弃")
    @Deprecated
    public CommonResultResp updateLastIndex(CommandContext ctxt, FetcherUpdateLastIndexReq req) {
        int sourceId = req.getSourceId();
        int lastIndex = req.getLastIndex();

        fetchIndexDAO.insert(sourceId, lastIndex);
        return CommonResultResp.success();
    }

    @Deprecated
    @Action(name = "fetcher.getLastIndex", desc = "获取上次的最后一次的索引位置，已经在171103废弃")
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
    public FetcherGetAlbumResp getMVAlbum(CommandContext ctxt, FetcherGetAlbumReq req) {
        int albumId = req.getMvAlbumId();
        FetcherGetAlbumResp resp = new FetcherGetAlbumResp();
        MVAlbumVO ai = MVAlbumDAO.getMVAlbum(albumId);
        BeanUtils.copyProperties(resp, ai);
        return resp;
    }


    @Action(name = "fetcher.dzAdd", desc = "")
    public FetcherDZAddResp dzAdd(CommandContext ctxt, FetcherDZAddReq req) {
        int userId = req.getUserId();
        int sourceId = req.getSourceId();
        String content = req.getContent();
        String sourceDZId = req.getSourceDZId();
        String md5 = MD5Util.getMD5String(req.getContent());
        if (dzdao.checkExisted(md5)) {
            LOG.info("dz exited. sourceId={}, sourceDZId={}", sourceId, sourceDZId);
            throw new CommonException(ErrorCode.Fetcher.DZ_EXISTED);
        }

        int dzId = dzdao.add(userId, sourceId, sourceDZId, content, md5);
        LOG.info("dz added. id={}", dzId);

        FetcherDZAddResp resp = new FetcherDZAddResp();
        resp.setDzId(dzId);
        return resp;
    }


    @Action(name = "fetcher.addComment", desc = "")
    public FetcherAddCommentResp addComment(CommandContext ctxt, FetcherAddCommentReq req) {
        FetcherAddCommentResp resp = new FetcherAddCommentResp();
        int userId = req.getUserId();
        int dzId = req.getDzId();
        String content = req.getContent();
        int isHot = req.getIsHot();

        int dzCommentId = dzCommentDAO.add(userId, dzId, content, isHot);
        LOG.info("comment added. id={}", dzCommentId);
        resp.setDzCommentId(dzCommentId);

        return resp;
    }


}
