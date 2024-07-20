package com.shuige.weblog.web.service;

import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.model.vo.category.FindCategoryArticlePageListReqVO;

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

    Response findCategoryArticlePageList(FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO);
}
