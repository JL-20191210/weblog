package com.shuige.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.shuige.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.shuige.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.shuige.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.shuige.weblog.admin.model.vo.category.FindCategoryPageListRspVO;
import com.shuige.weblog.admin.service.AdminCategoryService;
import com.shuige.weblog.common.domain.dos.CategoryDO;
import com.shuige.weblog.common.domain.mapper.CategoryMapper;
import com.shuige.weblog.common.enums.ResponseCodeEnum;
import com.shuige.weblog.common.exception.BizException;
import com.shuige.weblog.common.model.vo.SelectRspVO;
import com.shuige.weblog.common.utils.PageResponse;
import com.shuige.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加分类
     *
     * @param addCategoryReqVO
     * @return
     */
    @Override
    public Response addCategory(AddCategoryReqVO addCategoryReqVO) {
        String categoryName = addCategoryReqVO.getName();

        // 先判断该分类是否已经存在
        CategoryDO categoryDO = categoryMapper.selectByName(categoryName);

        if (Objects.nonNull(categoryDO)) {
            log.warn("分类名称： {}, 此分类已存在", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);
        }

        // 构建 DO 类
        CategoryDO insertCategoryDO = CategoryDO.builder()
                .name(addCategoryReqVO.getName().trim())
                .build();

        // 执行 insert
        categoryMapper.insert(insertCategoryDO);

        return Response.success();
    }

    @Override
    public PageResponse findCategoryPageList(FindCategoryPageListReqVO findCategoryPageListReqVO) {
        Long current = findCategoryPageListReqVO.getCurrent();
        Long size = findCategoryPageListReqVO.getSize();

        Page<CategoryDO> page = new Page<>(current, size);

        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();

        String name = findCategoryPageListReqVO.getName();
        LocalDate startDate = findCategoryPageListReqVO.getStartDate();
        LocalDate endDate = findCategoryPageListReqVO.getEndDate();

        wrapper
                .like(StringUtils.isNotBlank(name),CategoryDO::getName,name.trim())
                .ge(Objects.nonNull(startDate),CategoryDO::getCreateTime,startDate)
                .le(Objects.nonNull(endDate),CategoryDO::getCreateTime,endDate)
                .orderByDesc(CategoryDO::getCreateTime);

        Page<CategoryDO> categoryDOPage = categoryMapper.selectPage(page, wrapper);

        List<CategoryDO> categoryDOS = categoryDOPage.getRecords();

        List<FindCategoryPageListRspVO> vos = null;
        if(!CollectionUtils.isEmpty(categoryDOS)){
            vos = categoryDOS.stream()
                    .map(categoryDO -> FindCategoryPageListRspVO.builder()
                            .id(categoryDO.getId())
                            .name(categoryDO.getName())
                            .createTime(categoryDO.getCreateTime())
                            .build())
                    .collect(Collectors.toList());
        }

        return PageResponse.success(categoryDOPage,vos);
    }

    @Override
    public Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO) {
        // 执行删除
        Long id = deleteCategoryReqVO.getId();
        categoryMapper.deleteById(id);

        return Response.success();
    }

    @Override
    public Response findCategorySelectList() {
        //查询所有分类
        List<CategoryDO> categoryDOS = categoryMapper.selectList(null);

        List<SelectRspVO> selectRspVOS = null;

        if(!CollectionUtils.isEmpty(categoryDOS)){
            selectRspVOS = categoryDOS.stream()
                    .map(categoryDO -> SelectRspVO.builder()
                            .label(categoryDO.getName())
                            .value(categoryDO.getId())
                            .build())
                    .collect(Collectors.toList());
        }

        return Response.success(selectRspVOS);
    }
}
