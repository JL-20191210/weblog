package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shuige.weblog.common.config.InsertBatchMapper;
import com.shuige.weblog.common.domain.dos.ArticleTagRelDO;

import java.util.List;

/**
 * @author felix
 * @date 2024/7/9 14:34
 */
public interface ArticleTagRelMapper extends InsertBatchMapper<ArticleTagRelDO> {

    /**
     * 根据文章 ID 删除
     * @param articleId
     * @return
     */
    default int deleteByArticleIdInt(Long articleId){
        return delete(Wrappers.<ArticleTagRelDO>lambdaQuery().eq(ArticleTagRelDO::getArticleId,articleId));
    }

    /**
     * 根据文章id查询
     * @param articleId
     * @return
     */
    default List<ArticleTagRelDO> selectByArticleId(Long articleId){
        return selectList(Wrappers.<ArticleTagRelDO>lambdaQuery().eq(ArticleTagRelDO::getArticleId,articleId));
    }

    /**
     * 根据标签id查询
     * @param tagId
     * @return
     */
    default ArticleTagRelDO selectOneByTagId(Long tagId){
        return  selectOne(Wrappers.<ArticleTagRelDO>lambdaQuery()
                .eq(ArticleTagRelDO::getTagId,tagId)
                .last("limit 1"));
    }

    default List<ArticleTagRelDO> selectByArticleIds(List<Long> articleIds){
        return selectList(Wrappers.<ArticleTagRelDO>lambdaQuery()
                .in(ArticleTagRelDO::getArticleId,articleIds));
    }
}