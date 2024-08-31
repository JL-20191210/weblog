package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuige.weblog.common.domain.dos.CategoryDO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author felix
 * @date 2024/7/1 11:29
 */
public interface CategoryMapper extends BaseMapper<CategoryDO> {

    default Page<CategoryDO> selectPageList(long current, long size, String name, LocalDate startDate, LocalDate endDate){
        //分页对象
        Page<CategoryDO> page = new Page<>(current, size);

        // 构造查询条件
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();

        wrapper
                .like(StringUtils.isNotBlank(name),CategoryDO::getName,name.trim())
                .ge(Objects.nonNull(startDate),CategoryDO::getCreateTime,startDate)
                .le(Objects.nonNull(endDate),CategoryDO::getCreateTime,endDate)
                .orderByDesc(CategoryDO::getCreateTime);
        return selectPage(page,wrapper);
    }

    /*
     * @description: 根据分类名称查询分类
     * @param categoryName
     * @return: com.shuige.weblog.common.domain.dos.CategoryDO
     */
    default CategoryDO selectByName(String categoryName){

        /*
         * @description: 根据分类名称查询分类
         * @param categoryName
         * @return: com.shuige.weblog.common.domain.dos.CategoryDO
         */
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryDO::getName, categoryName);

        return selectOne(wrapper);
    }

    /**
     * 查询时指定数量
     * @param limit
     * @return
     */
    default List<CategoryDO> selectByLimit(Long limit) {
        return selectList(Wrappers.<CategoryDO>lambdaQuery()
                .orderByDesc(CategoryDO::getArticlesTotal) // 根据文章总数降序
                .last(String.format("LIMIT %d", limit))); // 查询指定数量
    }
}
