package com.shuige.weblog.web.convert;

import com.shuige.weblog.common.domain.dos.ArticleDO;
import com.shuige.weblog.web.model.vo.article.FindIndexArticlePageListRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author felix
 * @date 2024/7/17 22:24
 */
@Mapper
public interface ArticleConvert {

    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    /**
     * DO 转 VO
     *
     * @param articleDO
     * @return
     */
    @Mapping(target = "createDate",expression = "java(java.time.LocalDate.from(articleDO.getCreateTime()))")
    FindIndexArticlePageListRspVO convertDO2VO(ArticleDO articleDO);
}
