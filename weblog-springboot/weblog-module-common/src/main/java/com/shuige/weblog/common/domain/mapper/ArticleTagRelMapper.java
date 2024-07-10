package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shuige.weblog.common.config.InsertBatchMapper;
import com.shuige.weblog.common.domain.dos.ArticleTagRelDO;

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
}