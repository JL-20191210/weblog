package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuige.weblog.common.domain.dos.CategoryDO;

/**
 * @author felix
 * @date 2024/7/1 11:29
 */
public interface CategoryMapper extends BaseMapper<CategoryDO> {

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
}
