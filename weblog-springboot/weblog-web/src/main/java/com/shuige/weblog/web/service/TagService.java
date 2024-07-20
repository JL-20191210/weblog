package com.shuige.weblog.web.service;

import com.shuige.weblog.common.utils.Response;

/**
 * @author felix
 * @date 2024/7/18 21:45
 */
public interface TagService {
    /**
     * 查询标签列表
     * @return
     */
    Response findTagList();
}
