package com.shuige.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.shuige.weblog.admin.service.AdminStatisticsService;
import com.shuige.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.shuige.weblog.common.domain.dos.ArticleTagRelDO;
import com.shuige.weblog.common.domain.dos.CategoryDO;
import com.shuige.weblog.common.domain.dos.TagDO;
import com.shuige.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.shuige.weblog.common.domain.mapper.ArticleTagRelMapper;
import com.shuige.weblog.common.domain.mapper.CategoryMapper;
import com.shuige.weblog.common.domain.mapper.TagMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminStatisticsServiceImpl implements AdminStatisticsService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;

    @Override
    public void statisticsCategoryArticleTotal() {

        //查询所有分类
        List<CategoryDO> categoryDOS = categoryMapper.selectList(Wrappers.emptyWrapper());

        //查询所有文章-分类映射记录
        List<ArticleCategoryRelDO> articleCategoryRelDOS = articleCategoryRelMapper.selectList(Wrappers.emptyWrapper());

        Map<Long,List<ArticleCategoryRelDO>> categoryIdAndArticleCategoryRelDOMap = Maps.newHashMap();

        if(CollectionUtils.isNotEmpty(articleCategoryRelDOS)){
            categoryIdAndArticleCategoryRelDOMap = articleCategoryRelDOS
                    .stream()
                    .collect(Collectors.groupingBy(ArticleCategoryRelDO::getCategoryId));
        }

        if(CollectionUtils.isNotEmpty(categoryDOS)){
            for(CategoryDO categoryDO : categoryDOS){
                Long categoryId = categoryDO.getId();
                List<ArticleCategoryRelDO> articleCategoryRelDOList  = categoryIdAndArticleCategoryRelDOMap.get(categoryId);
                int articlesTotal = CollectionUtils.isEmpty(articleCategoryRelDOList) ? 0 : articleCategoryRelDOList.size();

                CategoryDO categoryDO1 = CategoryDO.builder()
                        .id(categoryId)
                        .articlesTotal(articlesTotal)
                        .build();

                categoryMapper.updateById(categoryDO1);
            }
        }
    }

    @Override
    public void statisticsTagArticleTotal() {
        // 查询所有标签
        List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());

        // 查询所有文章-标签映射记录
        List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectList(Wrappers.emptyWrapper());

        // 按所需标签ID进行分组
        Map<Long,List<ArticleTagRelDO>> tagIdAndArticleTagRelDOMap = Maps.newHashMap();

        if(CollectionUtils.isNotEmpty(articleTagRelDOS)){
            tagIdAndArticleTagRelDOMap = articleTagRelDOS.stream()
                    .collect(Collectors.groupingBy(ArticleTagRelDO::getTagId));
        }

        if(CollectionUtils.isNotEmpty(tagDOS)){
            for(TagDO tagDO : tagDOS){
                Long tagId = tagDO.getId();

                List<ArticleTagRelDO> articleTagRelDOSList = tagIdAndArticleTagRelDOMap.get(tagId);

                int articleTotal = CollectionUtils.isEmpty(articleTagRelDOSList) ? 0 : articleTagRelDOSList.size();

                TagDO tagDO1 = TagDO.builder()
                        .id(tagId)
                        .articlesTotal(articleTotal)
                        .build();
                tagMapper.updateById(tagDO1);
            }
        }

    }
}
