package com.shuige.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.shuige.weblog.admin.service.AdminStatisticsService;
import com.shuige.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.shuige.weblog.common.domain.dos.CategoryDO;
import com.shuige.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.shuige.weblog.common.domain.mapper.CategoryMapper;
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
                    .collect(Collectors.groupingBy(ArticleCategoryRelDO::getArticleId));
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
}
