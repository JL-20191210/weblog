package com.shuige.weblog.admin.convert;

import com.shuige.weblog.admin.model.vo.article.FindArticleDetailRspVO;
import com.shuige.weblog.common.domain.dos.ArticleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author felix
 * @date 2024/7/10 17:00
 */
@Mapper
public interface ArticleDetailConvert {
    /**
     * 初始化 convert 实例
     */
    ArticleDetailConvert INSTANCE = Mappers.getMapper(ArticleDetailConvert.class);

    /**
     * ArticleDO 转 FindArticleDetailRspVO
     * @param bean
     * @return
     */
    FindArticleDetailRspVO convertDO2VO(ArticleDO bean);
}
