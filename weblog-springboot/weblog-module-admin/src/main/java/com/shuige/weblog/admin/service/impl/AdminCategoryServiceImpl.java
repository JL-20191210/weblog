package com.shuige.weblog.admin.service.impl;

import com.shuige.weblog.admin.model.vo.category.AddCategoryReqVo;
import com.shuige.weblog.admin.service.AdminCategoryService;
import com.shuige.weblog.common.domain.dos.CategoryDO;
import com.shuige.weblog.common.domain.mapper.CategoryMapper;
import com.shuige.weblog.common.enums.ResponseCodeEnum;
import com.shuige.weblog.common.exception.BizException;
import com.shuige.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author felix
 * @date 2024/7/1 12:39
 */
@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Response addCategory(AddCategoryReqVo addCategoryReqVo) {
        String categoryName = addCategoryReqVo.getName();

        CategoryDO categoryDO = categoryMapper.selectByName(categoryName);
        if(Objects.nonNull(categoryDO)){
            log.warn("分类名称：{},此分类已存在",categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);
        }

        CategoryDO insertCategoryDo = CategoryDO.builder().name(addCategoryReqVo.getName().trim()).build();

        categoryMapper.insert(insertCategoryDo);

        return Response.success();
    }
}
