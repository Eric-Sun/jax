package com.j13.jax.facade;

import com.j13.jax.core.Constants;
import com.j13.jax.dao.CollectionDAO;
import com.j13.jax.dao.EventDAO;
import com.j13.jax.my.req.MyCollectionListReq;
import com.j13.jax.my.resp.CollectionGetResp;
import com.j13.jax.my.resp.MyCollectionListResp;
import com.j13.jax.service.MVHelper;
import com.j13.jax.vo.CollectionVO;
import com.j13.jax.vo.EventVO;
import com.j13.jax.vo.TripleImgContentVO;
import com.j13.poppy.anno.Action;
import com.j13.poppy.core.CommandContext;
import com.j13.poppy.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


    @Action(name = "my.collectionList", desc = "")
    public MyCollectionListResp collectionList(CommandContext ctxt, MyCollectionListReq req) {
        MyCollectionListResp listResp = new MyCollectionListResp();
//        int userId = ctxt.getUid();
//        List<CollectionVO> list = collectionDAO.list(userId, req.getPageNum(), 5);
//        for (CollectionVO vo : list) {
//            CollectionGetResp getResp = new CollectionGetResp();
//            BeanUtils.copyProperties(getResp, vo);
//
//            if (vo.getType() == Constants.CollectionType.COMMENT) {
//                // comment
//
//            } else {
//                // event
//                EventVO eventVO = eventDAO.get(vo.getResourceId());
//                TripleImgContentVO tripleImgContentVO = MVHelper.getMVTriplerImgContent(eventVO);
//                getResp.setResourceObj(tripleImgContentVO);
//            }
//            listResp.getList().add(getResp);
//        }

        return listResp;
    }



}
