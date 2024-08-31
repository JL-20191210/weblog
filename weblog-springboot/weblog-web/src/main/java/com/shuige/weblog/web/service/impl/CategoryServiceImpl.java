package com.shuige.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.shuige.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.shuige.weblog.common.domain.dos.ArticleDO;
import com.shuige.weblog.common.domain.dos.CategoryDO;
import com.shuige.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.shuige.weblog.common.domain.mapper.ArticleMapper;
import com.shuige.weblog.common.domain.mapper.CategoryMapper;
import com.shuige.weblog.common.enums.ResponseCodeEnum;
import com.shuige.weblog.common.exception.BizException;
import com.shuige.weblog.common.utils.PageResponse;
import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.convert.ArticleConvert;
import com.shuige.weblog.web.model.vo.category.FindCategoryArticlePageListReqVO;
import com.shuige.weblog.web.model.vo.category.FindCategoryArticlePageListRspVO;
import com.shuige.weblog.web.model.vo.category.FindCategoryListReqVO;
import com.shuige.weblog.web.model.vo.category.FindCategoryListRspVO;
import com.shuige.weblog.web.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author felix
 * @date 2024/7/18 21:36
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Response findCategoryList(FindCategoryListReqVO findCategoryListReqVO) {
        Long size = findCategoryListReqVO.getSize();

        // 查询所有分类
        List<CategoryDO> categoryDOS = null;

        // 如果接口入参中未指定 size
        if (Objects.isNull(size) || size == 0) {
            // 查询所有分类
            categoryDOS = categoryMapper.selectList(Wrappers.emptyWrapper());
        } else {
            // 否则查询指定的数量
            categoryDOS = categoryMapper.selectByLimit(size);
        }
        // DO转VO
        List<FindCategoryListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(categoryDOS)) {
            vos = categoryDOS.stream()
                    .map(categoryDO -> FindCategoryListRspVO.builder()
                            .id(categoryDO.getId())
                            .name(categoryDO.getName())
                            .articlesTotal(categoryDO.getArticlesTotal())
                            .build())
                    .collect(Collectors.toList());
        }

        return Response.success(vos);
    }

    @Override
    public Response findCategoryArticlePageList(FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO) {
        Long current = findCategoryArticlePageListReqVO.getCurrent();
        Long size = findCategoryArticlePageListReqVO.getSize();
        Long categoryId = findCategoryArticlePageListReqVO.getId();

        CategoryDO categoryDO = categoryMapper.selectById(categoryId);

        if (Objects.isNull(categoryDO)) {
            log.warn("==>该分类不存在，categoryIDL{}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }

        List<ArticleCategoryRelDO> articleCategoryRelDOS = articleCategoryRelMapper.selectListByCategoryId(categoryId);

        if (CollectionUtils.isEmpty(articleCategoryRelDOS)) {
            log.info("==> 该分类下还未发布任何文章, categoryId: {}", categoryId);
            return PageResponse.success(null, "该分类下还未发布任何文章");
        }

        List<Long> articleIds = articleCategoryRelDOS.stream().map(ArticleCategoryRelDO::getArticleId).collect(Collectors.toList());

        Page<ArticleDO> page = articleMapper.selectPageListByArticleIds(current, size, articleIds);
        List<ArticleDO> articleDOS = page.getRecords();

        List<FindCategoryArticlePageListRspVO> vos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(articleDOS)) {
            vos = articleDOS.stream()
                    .map(ArticleConvert.INSTANCE::convertDO2CategoryArticleVO)
                    .collect(Collectors.toList());
        }
        return PageResponse.success(page, vos);
    }
}
