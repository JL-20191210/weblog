package com.shuige.weblog.web.service;

import com.shuige.weblog.common.utils.Response;

/**
 * @author felix
 * @date 2024/7/18 21:36
 */
public interface CategoryService {

    /**
     * 查询分类列表
     * @return
     */
    Response findCategoryList();
}
