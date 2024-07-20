package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shuige.weblog.common.domain.dos.ArticleCategoryRelDO;

import java.util.List;

/**
 * @author felix
 * @date 2024/7/9 10:54
 */
public interface ArticleCategoryRelMapper extends BaseMapper<ArticleCategoryRelDO> {

    /**
     * 根据文章 ID 删除
     * @param articleId
     * @return
     */
    default int deleteByArticleIdInt(Long articleId){
        return delete(Wrappers.<ArticleCategoryRelDO>lambdaQuery().eq(ArticleCategoryRelDO::getArticleId,articleId));
    }

    /**
     * 根据文章id查询
     * @param articleId
     * @return
     */
    default ArticleCategoryRelDO selectByArticleId(Long articleId){
        return selectOne(Wrappers.<ArticleCategoryRelDO>lambdaQuery().eq(ArticleCategoryRelDO::getArticleId,articleId));
    }

    default ArticleCategoryRelDO selectOneByCategoryId(Long categoryId){
        return  selectOne(Wrappers.<ArticleCategoryRelDO>lambdaQuery()
                .eq(ArticleCategoryRelDO::getCategoryId,categoryId)
                .last("limit 1"));
    }

    default List<ArticleCategoryRelDO> selectByArticleIds(List<Long> articleIds){
        return selectList(Wrappers.<ArticleCategoryRelDO>lambdaQuery()
                .in(ArticleCategoryRelDO::getArticleId,articleIds));
    };

    /**
     * 根据分类id查询所有关联记录
     * @param categoryId
     * @return
     */
    default List<ArticleCategoryRelDO> selectListByCategoryId(Long categoryId){
        return selectList(Wrappers.<ArticleCategoryRelDO>lambdaQuery()
                .eq(ArticleCategoryRelDO::getCategoryId,categoryId));
    }
}