package com.shuige.weblog.admin.service;

import com.shuige.weblog.admin.model.vo.category.AddCategoryReqVo;
import com.shuige.weblog.common.utils.Response;

/**
 * @author felix
 * @date 2024/7/1 12:38
 */
public interface AdminCategoryService {

    /**
     * 添加分类
     * @param addCategoryReqVo
     * @return
     */
    Response addCategory(AddCategoryReqVo addCategoryReqVo);
}
