package com.shuige.weblog.web.convert;

import com.shuige.weblog.common.domain.dos.ArticleDO;
import com.shuige.weblog.web.model.vo.archive.FindArchiveArticleRspVO;
import com.shuige.weblog.web.model.vo.article.FindIndexArticlePageListRspVO;
import com.shuige.weblog.web.model.vo.category.FindCategoryArticlePageListRspVO;
import com.shuige.weblog.web.model.vo.tag.FindTagArticlePageListRspVO;
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
     * @param bean
     * @return
     */
    @Mapping(target = "createDate",expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
    FindIndexArticlePageListRspVO convertDO2VO(ArticleDO bean);

    /**
     * 将 DO 转化为归档文章 VO
     * @param bean
     * @return
     */
    @Mapping(target = "createDate",expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
    @Mapping(target = "createMonth",expression = "java(java.time.YearMonth.from(bean.getCreateTime()))")
    FindArchiveArticleRspVO convertDO2ArchiveArticleVO(ArticleDO bean);

    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
    FindCategoryArticlePageListRspVO convertDO2CategoryArticleVO(ArticleDO bean);

    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
    FindTagArticlePageListRspVO convertDO2TagArticleVO(ArticleDO bean);
}
