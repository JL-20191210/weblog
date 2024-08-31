package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuige.weblog.common.domain.dos.CategoryDO;
import com.shuige.weblog.common.domain.dos.TagDO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author felix
 * @date 2024/7/1 11:29
 */
public interface TagMapper extends BaseMapper<TagDO> {

    default Page<TagDO> selectPageList(long current, long size, String name, LocalDate startDate,LocalDate endTDate){
        //分页对象
        Page<TagDO> page = new Page<>(current, size);

        // 构造查询条件
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .like(Objects.nonNull(name),TagDO::getName,name)
                .ge(Objects.nonNull(startDate),TagDO::getCreateTime,startDate)
                .le(Objects.nonNull(endTDate),TagDO::getCreateTime,endTDate)
                .orderByDesc(TagDO::getCreateTime);
        return selectPage(page,wrapper);
    }

    /**
     * 根据标签关键字查询
     * @param key
     * @return
     */
    default List<TagDO> selectByKey(String key){
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();

        // 构造模糊查询条件
        wrapper.like(TagDO::getName,key).orderByDesc(TagDO::getCreateTime);
        return selectList(wrapper);
    }

    default List<TagDO> selectByIds(List<Long> tagIds){
        return selectList(Wrappers.<TagDO>lambdaQuery()
                .in(TagDO::getId,tagIds));
    }

    /**
     * 查询时指定数量
     * @param limit
     * @return
     */
    default List<TagDO> selectByLimit(Long limit) {
        return selectList(Wrappers.<TagDO>lambdaQuery()
                .orderByDesc(TagDO::getArticlesTotal) // 根据文章总数降序
                .last(String.format("LIMIT %d", limit))); // 查询指定数量
    }
}
