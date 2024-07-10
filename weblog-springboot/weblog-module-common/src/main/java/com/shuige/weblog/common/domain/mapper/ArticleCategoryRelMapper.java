package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shuige.weblog.common.domain.dos.ArticleCategoryRelDO;

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
}