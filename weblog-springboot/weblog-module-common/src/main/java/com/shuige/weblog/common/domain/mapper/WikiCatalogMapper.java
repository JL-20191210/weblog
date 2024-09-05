package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shuige.weblog.common.config.InsertBatchMapper;
import com.shuige.weblog.common.domain.dos.WikiCatalogDO;
import com.shuige.weblog.common.enums.WikiCatalogLevelEnum;

import java.util.List;

/**
 * @author felix
 * @date 2024/9/2 09:50
 */
public interface WikiCatalogMapper extends InsertBatchMapper<WikiCatalogDO> {
    /**
     * 根据某个知识库下所有目录
     * @param wikiId
     * @return
     */
    default List<WikiCatalogDO> selectByWikiId(Long wikiId){
        return selectList(Wrappers.<WikiCatalogDO>lambdaQuery()
                .eq(WikiCatalogDO::getWikiId, wikiId));
    }

    /**
     * 删除知识库
     * @param wikiId
     * @return
     */
    default int deleteByWikiId(Long wikiId){
        return delete(Wrappers.<WikiCatalogDO>lambdaQuery()
                .eq(WikiCatalogDO::getWikiId, wikiId));
    }

    default WikiCatalogDO selectFirstArticleId(Long wikiId){
        return selectOne(Wrappers.<WikiCatalogDO>lambdaQuery()
                .eq(WikiCatalogDO::getWikiId,wikiId)
                .eq(WikiCatalogDO::getLevel, WikiCatalogLevelEnum.TWO.getValue())
                .isNotNull(WikiCatalogDO::getArticleId)
                .orderByAsc(WikiCatalogDO::getId)
                .last("limit 1"));
    }

    /**
     * 根据知识库 ID 和文章 ID 查询对应的目录
     * @param wikiId
     * @param articleId
     * @return
     */
    default WikiCatalogDO selectByWikiIdAndArticleId(Long wikiId, Long articleId) {
        return selectOne(Wrappers.<WikiCatalogDO>lambdaQuery()
                .eq(WikiCatalogDO::getWikiId, wikiId)
                .eq(WikiCatalogDO::getArticleId, articleId)
        );
    }

    /**
     * 查询下一篇文章
     * @param wikiId
     * @param catalogId
     * @return
     */
    default WikiCatalogDO selectNextArticle(Long wikiId, Long catalogId) {
        return selectOne(Wrappers.<WikiCatalogDO>lambdaQuery()
                .eq(WikiCatalogDO::getWikiId, wikiId)
                .isNotNull(WikiCatalogDO::getArticleId) // article_id 字段不能为空
                .orderByAsc(WikiCatalogDO::getId) // 按目录 ID 倒序排列
                .gt(WikiCatalogDO::getId, catalogId) // 查询比当前文章 ID 大的
                .last("limit 1") // 第一条记录即为下一篇文章
        );
    }

    /**
     * 查询上一篇文章
     * @param wikiId
     * @param catalogId
     * @return
     */
    default WikiCatalogDO selectPreArticle(Long wikiId, Long catalogId) {
        return selectOne(Wrappers.<WikiCatalogDO>lambdaQuery()
                .eq(WikiCatalogDO::getWikiId, wikiId)
                .isNotNull(WikiCatalogDO::getArticleId) // // article_id 字段不能为空
                .orderByDesc(WikiCatalogDO::getId) // 按文章 ID 倒序排列
                .lt(WikiCatalogDO::getId, catalogId) // 查询比当前文章 ID 小的
                .last("limit 1") // 第一条记录即为上一篇文章
        );
    }
}
