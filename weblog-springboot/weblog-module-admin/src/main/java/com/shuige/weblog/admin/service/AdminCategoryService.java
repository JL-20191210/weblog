package com.shuige.weblog.admin.service;

import com.shuige.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.shuige.weblog.common.utils.Response;

public interface AdminCategoryService {
    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    Response addCategory(AddCategoryReqVO addCategoryReqVO);
}