package com.shuige.weblog.admin.service;

import com.shuige.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.shuige.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.shuige.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.shuige.weblog.common.utils.PageResponse;
import com.shuige.weblog.common.utils.Response;

public interface AdminCategoryService {
    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    Response addCategory(AddCategoryReqVO addCategoryReqVO);

    /**
     * 分页查询分类列表
     * @param findCategoryPageListReqVO
     * @return
     */
    PageResponse findCategoryPageList(FindCategoryPageListReqVO findCategoryPageListReqVO);

    /**
     * 删除分类
     * @param deleteCategoryReqVO
     * @return
     */
    Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO);

    /**
     * 获取文章分类的 Select 列表数据
     * @return
     */
    Response findCategorySelectList();
}
