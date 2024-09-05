package com.shuige.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.shuige.weblog.admin.event.ReadArticleEvent;
import com.shuige.weblog.common.domain.dos.*;
import com.shuige.weblog.common.domain.mapper.*;
import com.shuige.weblog.common.enums.ResponseCodeEnum;
import com.shuige.weblog.common.exception.BizException;
import com.shuige.weblog.common.utils.PageResponse;
import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.convert.ArticleConvert;
import com.shuige.weblog.web.markdown.MarkdownHelper;
import com.shuige.weblog.web.model.vo.article.*;
import com.shuige.weblog.web.model.vo.category.FindCategoryListRspVO;
import com.shuige.weblog.web.model.vo.tag.FindTagListRspVO;
import com.shuige.weblog.web.service.ArticleService;
import com.shuige.weblog.web.utils.MarkdownStatsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author felix
 * @date 2024/7/17 22:15
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Override
    public Response findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO) {
        Long current = findIndexArticlePageListReqVO.getCurrent();
        Long size = findIndexArticlePageListReqVO.getSize();

        // 第一步：分页查询文章主体记录
        Page<ArticleDO> articleDOPage = articleMapper.selectPageList(current, size, null, null, null,null);

        List<ArticleDO> articleDOS = articleDOPage.getRecords();

        List<FindIndexArticlePageListRspVO> vos = null;
        if(!CollectionUtils.isEmpty(articleDOS)){
            //文章DO转VO
            vos = articleDOS.stream()
                    .map(articleDO ->{
                                FindIndexArticlePageListRspVO vo = ArticleConvert.INSTANCE.convertDO2VO(articleDO);
                                vo.setIsTop(articleDO.getWeight()>0);
                                return vo;
                            }
                            )
                    .collect(Collectors.toList());

            //拿到所有文章的ID集合
            List<Long> articleIds = articleDOS.stream().map(ArticleDO::getId).collect(Collectors.toList());

            //第二步 设置文章所属分类
            //查询所有分类
            List<CategoryDO> categoryDOS = categoryMapper.selectList(Wrappers.emptyWrapper());
            //转map,方便后续根据分类ID拿到对应的分类名称
            Map<Long, String> categoryIdNameMap = categoryDOS.stream().collect(Collectors.toMap(CategoryDO::getId, CategoryDO::getName));
            // 根据文章ID批量查询所有关联记录
            List<ArticleCategoryRelDO> articleCategoryRelDOS = articleCategoryRelMapper.selectByArticleIds(articleIds);
            vos.forEach(vo->{
                Long currentArticleId = vo.getId();

                //过滤出当前文章对应的关联数据
                Optional<ArticleCategoryRelDO> optional = articleCategoryRelDOS.stream().filter(rel -> Objects.equals(rel.getArticleId(), currentArticleId)).findAny();

                if(optional.isPresent()){
                    ArticleCategoryRelDO articleCategoryRelDO = optional.get();
                    Long categoryId = articleCategoryRelDO.getCategoryId();
                    String categoryName = categoryIdNameMap.get(categoryId);

                    FindCategoryListRspVO findCategoryListRspVO = FindCategoryListRspVO.builder()
                            .id(categoryId)
                            .name(categoryName)
                            .build();
                    //设置到当前vo类中
                    vo.setCategory(findCategoryListRspVO);
                }
            });

            //第三步：设置文章标签
            List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());
            Map<Long, String> tagIdNameMap = tagDOS.stream().collect(Collectors.toMap(TagDO::getId, TagDO::getName));

            //拿到所有文章的标签关联记录
            List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleIds(articleIds);
            vos.forEach(vo->{
                Long currArticleId = vo.getId();
                //过滤出当前文章的标签关联记录
                List<ArticleTagRelDO> articleTagRelDOList = articleTagRelDOS.stream().filter(rel -> Objects.equals(rel.getArticleId(), currArticleId)).collect(Collectors.toList());

                List<FindTagListRspVO> findTagListRspVOS = Lists.newArrayList();
                //将关联记录DO转VO，并设置对应的标签名称
                articleTagRelDOList.forEach(articleTagRelDO -> {
                    Long tagId = articleTagRelDO.getTagId();

                    String tagName = tagIdNameMap.get(tagId);

                    FindTagListRspVO findTagListRspVO = FindTagListRspVO.builder()
                            .id(tagId)
                            .name(tagName)
                            .build();
                    findTagListRspVOS.add(findTagListRspVO);
                });
                vo.setTags(findTagListRspVOS);
            });

        }
        return PageResponse.success(articleDOPage,vos);
    }

    @Override
    public Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO) {
        Long articleId = findArticleDetailReqVO.getArticleId();
        ArticleDO articleDO = articleMapper.selectById(articleId);

        if(Objects.isNull(articleDO)){
            log.warn("==> 该文章不存在, articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }

        ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);
        ArticleCategoryRelDO articleCategoryRelDOS = articleCategoryRelMapper.selectByArticleId(articleId);
        CategoryDO categoryDO = categoryMapper.selectById(articleCategoryRelDOS.getCategoryId());

        // 查询正文
        String content = articleContentDO.getContent();

        // 计算 md 正文字数
        Integer totalWords = MarkdownStatsUtil.calculateWordCount(content);

        //DO转VO
        FindArticleDetailRspVO findArticleDetailRspVO = FindArticleDetailRspVO.builder()
                .title(articleDO.getTitle())
                .createTime(articleDO.getCreateTime())
                .content(MarkdownHelper.convertMarkdown2Html(articleContentDO.getContent()))
                .readNum(articleDO.getReadNum())
                .categoryId(categoryDO.getId())
                .categoryName(categoryDO.getName())
                .totalWords(totalWords)
                .readTime(MarkdownStatsUtil.calculateReadingTime(totalWords))
                .updateTime(articleDO.getUpdateTime())
                .build();

        List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleId(articleId);
        List<Long> tagIds = articleTagRelDOS.stream().map(ArticleTagRelDO::getTagId).collect(Collectors.toList());
        List<TagDO> tagDOS = tagMapper.selectByIds(tagIds);

        List<FindTagListRspVO> tagVOS = tagDOS.stream()
                .map(tagDO -> FindTagListRspVO.builder().id(tagDO.getId()).name(tagDO.getName()).build())
                .collect(Collectors.toList());

        findArticleDetailRspVO.setTags(tagVOS);

        ArticleDO preArticleDO = articleMapper.selectPreArticle(articleId);
        if(Objects.nonNull(preArticleDO)){
            FindPreNextArticleRspVO findPreNextArticleRspVO = FindPreNextArticleRspVO.builder()
                    .articleId(preArticleDO.getId())
                    .articleTitle(preArticleDO.getTitle())
                    .build();
            findArticleDetailRspVO.setPreArticle(findPreNextArticleRspVO);
        }

        ArticleDO nextArticleDO = articleMapper.selectNextArticle(articleId);
        if(Objects.nonNull(nextArticleDO)){
            FindPreNextArticleRspVO nextArticleRspVO = FindPreNextArticleRspVO.builder()
                    .articleId(nextArticleDO.getId())
                    .articleTitle(nextArticleDO.getTitle())
                    .build();
            findArticleDetailRspVO.setNextArticle(nextArticleRspVO);
        }

        eventPublisher.publishEvent(new ReadArticleEvent(this,articleId));
        return Response.success(findArticleDetailRspVO);
    }
}
