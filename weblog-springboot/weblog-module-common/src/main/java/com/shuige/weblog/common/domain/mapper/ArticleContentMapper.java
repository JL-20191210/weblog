package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shuige.weblog.common.domain.dos.ArticleContentDO;

/**
 * @author felix
 * @date 2024/7/9 10:53
 */
public interface ArticleContentMapper extends BaseMapper<ArticleContentDO> {

    /**
     * 根据文章id删除
     * @param articleId
     * @return
     */
    default int deleteByArticleIdInt(Long articleId){
        return delete(Wrappers.<ArticleContentDO>lambdaQuery().eq(ArticleContentDO::getArticleId,articleId));
    }

}