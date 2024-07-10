package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuige.weblog.common.domain.dos.ArticleDO;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author felix
 * @date 2024/7/9 10:52
 */
public interface ArticleMapper extends BaseMapper<ArticleDO> {

    /**
     * 查询文章分页数据
     * @param current 当前页
     * @param size 每页多少数据
     * @param title 文章标题
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    default Page<ArticleDO> selectPageList(Long current, Long size, String title, LocalDate startDate,LocalDate endDate){
        // 分页对象(查询第几页、每页多少数据)
        Page<ArticleDO> page = new Page<>(current,size);

        // 构建查询条件
        LambdaQueryWrapper<ArticleDO> wrapper = Wrappers.<ArticleDO>lambdaQuery()
                .like(StringUtils.isNotBlank(title),ArticleDO::getTitle,title.trim())
                .ge(Objects.nonNull(startDate),ArticleDO::getCreateTime,startDate)
                .le(Objects.nonNull(endDate),ArticleDO::getCreateTime,endDate)
                .orderByDesc(ArticleDO::getCreateTime);

        return selectPage(page,wrapper);
    }
}
