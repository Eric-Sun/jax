package com.j13.jax.service;


import com.j13.poppy.SystemErrorCode;
import com.j13.poppy.exceptions.CommonException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 用于检测获取等对于抓取源的操作
 */
@Service
public class FetcherSourceService {

    private static HashMap<Integer, String> sourceMap = new HashMap<Integer, String>() {{
        put(1, "mm131");
    }};

    /**
     * 检测源是否存在，如果不存在抛出对应的异常
     * @param sourceId
     * @return
     */
    public void check(int sourceId) {
        if (sourceMap.keySet().contains(sourceId)) {
            return ;
        } else {
            throw new CommonException(SystemErrorCode.Common.INPUT_PARAMETER_ERROR, "source is error. sourceId=" + sourceId);

        }

    }


}
