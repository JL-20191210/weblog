package com.shuige.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.shuige.weblog.admin.convert.WikiConvert;
import com.shuige.weblog.admin.model.vo.wiki.*;
import com.shuige.weblog.admin.service.AdminWikiService;
import com.shuige.weblog.common.domain.dos.ArticleDO;
import com.shuige.weblog.common.domain.dos.WikiCatalogDO;
import com.shuige.weblog.common.domain.dos.WikiDO;
import com.shuige.weblog.common.domain.mapper.ArticleMapper;
import com.shuige.weblog.common.domain.mapper.CategoryMapper;
import com.shuige.weblog.common.domain.mapper.WikiCatalogMapper;
import com.shuige.weblog.common.domain.mapper.WikiMapper;
import com.shuige.weblog.common.enums.ArticleTypeEnum;
import com.shuige.weblog.common.enums.ResponseCodeEnum;
import com.shuige.weblog.common.enums.WikiCatalogLevelEnum;
import com.shuige.weblog.common.exception.BizException;
import com.shuige.weblog.common.utils.PageResponse;
import com.shuige.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.CollateExpression;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForJapaneseDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author felix
 * @date 2024/9/2 09:53
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class AdminWikiServiceImpl implements AdminWikiService {

    @Autowired
    private WikiMapper wikiMapper;

    @Autowired
    private WikiCatalogMapper wikiCatalogMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增知识库
     *
     * @param addWikiReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response addWiki(AddWikiReqVO addWikiReqVO) {
        // VO 转 DO
        WikiDO wikiDO = WikiDO.builder()
                .cover(addWikiReqVO.getCover())
                .title(addWikiReqVO.getTitle())
                .summary(addWikiReqVO.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        // 新增知识库
        wikiMapper.insert(wikiDO);
        // 获取新增记录的主键 ID
        Long wikiId = wikiDO.getId();

        // 初始化默认目录
        // > 概述
        // > 基础
        wikiCatalogMapper.insert(WikiCatalogDO.builder().wikiId(wikiId).title("概述").sort(1).build());
        wikiCatalogMapper.insert(WikiCatalogDO.builder().wikiId(wikiId).title("基础").sort(2).build());
        return Response.success();
    }

    /**
     * 删除知识库
     *
     * @param deleteWikiReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteWiki(DeleteWikiReqVO deleteWikiReqVO) {
        Long wikiId = deleteWikiReqVO.getId();

        // 删除知识库
        int count = wikiMapper.deleteById(wikiId);

        // 若知识库不存在
        if (count == 0) {
            log.warn("该知识库不存在, wikiId: {}", wikiId);
            throw new BizException(ResponseCodeEnum.WIKI_NOT_FOUND);
        }

        // 查询此知识库下所有目录
        List<WikiCatalogDO> wikiCatalogDOS = wikiCatalogMapper.selectByWikiId(wikiId);
        // 过滤目录中所有文章的 ID
        List<Long> articleIds = wikiCatalogDOS.stream()
                .filter(wikiCatalogDO -> Objects.nonNull(wikiCatalogDO.getArticleId())  // 文章 ID 不为空
                        && Objects.equals(wikiCatalogDO.getLevel(), WikiCatalogLevelEnum.TWO.getValue())) // 二级目录
                .map(WikiCatalogDO::getArticleId) // 提取文章 ID
                .collect(Collectors.toList());

        // 更新文章类型 type 为普通
        if (!CollectionUtils.isEmpty(articleIds)) {
            articleMapper.updateByIds(ArticleDO.builder()
                    .type(ArticleTypeEnum.NORMAL.getValue())
                    .build(), articleIds);
        }

        // 删除知识库目录
        wikiCatalogMapper.deleteByWikiId(wikiId);
        return Response.success();
    }

    /**
     * 知识库分页查询
     *
     * @param findWikiPageListReqVO
     * @return
     */
    @Override
    public Response findWikiPageList(FindWikiPageListReqVO findWikiPageListReqVO) {
        Long current = findWikiPageListReqVO.getCurrent();
        Long size = findWikiPageListReqVO.getSize();

        LocalDate startDate = findWikiPageListReqVO.getStartDate();
        LocalDate endDate = findWikiPageListReqVO.getEndDate();
        String title = findWikiPageListReqVO.getTitle();
        Page<WikiDO> page = wikiMapper.selectPageList(current, size, title, startDate, endDate, null);

        List<WikiDO> wikiDOS = page.getRecords();
        List<FindWikiPageListRspVO> vos = null;
        if(CollectionUtils.isNotEmpty(wikiDOS)){
            vos = wikiDOS.stream().map(articleDO-> WikiConvert.INSTANCE.convertDOTOVO(articleDO))
                    .collect(Collectors.toList());
        }

        return PageResponse.success(page,vos);
    }

    /**
     * 更新知识库置顶状态
     *
     * @param updateWikiIsTopReqVO
     * @return
     */
    @Override
    public Response updateWikiIsTop(UpdateWikiIsTopReqVO updateWikiIsTopReqVO) {
        Long wikiId = updateWikiIsTopReqVO.getId();
        Boolean isTop = updateWikiIsTopReqVO.getIsTop();

        // 默认权重值为 0 ，即不参与置顶
        Integer weight = 0;
        // 若设置为置顶
        if (isTop) {
            // 查询最大权重值
            WikiDO wikiDO = wikiMapper.selectMaxWeight();
            Integer maxWeight = wikiDO.getWeight();
            // 最大权重值加一
            weight = maxWeight + 1;
        }

        // 更新该知识库的权重值
        wikiMapper.updateById(WikiDO.builder().id(wikiId).weight(weight).build());
        return Response.success();
    }

    @Override
    public Response updateWikiIsPublish(UpdateWikiIsPublishReqVO updateWikiIsPublishReqVO) {
        Long wikiId = updateWikiIsPublishReqVO.getId();
        Boolean isPublish = updateWikiIsPublishReqVO.getIsPublish();
        //更新发布状态
        wikiMapper.updateById(WikiDO.builder().id(wikiId).isPublish(isPublish).build());
        return Response.success();
    }

    /**
     * 更新知识库
     *
     * @param updateWikiReqVO
     * @return
     */
    @Override
    public Response updateWiki(UpdateWikiReqVO updateWikiReqVO) {
        // VO 转 DO
        WikiDO wikiDO = WikiDO.builder()
                .id(updateWikiReqVO.getId())
                .title(updateWikiReqVO.getTitle())
                .cover(updateWikiReqVO.getCover())
                .summary(updateWikiReqVO.getSummary())
                .build();

        // 根据 ID 更新知识库
        wikiMapper.updateById(wikiDO);
        return Response.success();
    }

    @Override
    public Response findWikiCatalogList(FindWikiCatalogListReqVO findWikiCatalogListReqVO) {
        Long wikiID = findWikiCatalogListReqVO.getId();

        //查询此知识库下所有目录
        List<WikiCatalogDO> wikiCatalogDOS = wikiCatalogMapper.selectByWikiId(wikiID);

        List<FindWikiCatalogListRspVO> vos = null;
        if(CollectionUtils.isNotEmpty(wikiCatalogDOS)){
            vos = Lists.newArrayList();

            //提取一级目录
            List<WikiCatalogDO> level1Catalogs = wikiCatalogDOS.stream()
                    .filter(wikiCatalogDO -> Objects.equals(wikiCatalogDO.getLevel(), WikiCatalogLevelEnum.ONE.getValue()))
                    .sorted(Comparator.comparing(WikiCatalogDO::getSort))
                    .collect(Collectors.toList());

            //循环一级目录，DO转VO
            for(WikiCatalogDO wikiCatalogDO : level1Catalogs){
                vos.add(FindWikiCatalogListRspVO.builder()
                                .id(wikiCatalogDO.getId())
                                .articleId(wikiCatalogDO.getArticleId())
                                .title(wikiCatalogDO.getTitle())
                                .level(wikiCatalogDO.getLevel())
                                .sort(wikiCatalogDO.getSort())
                                .editing(Boolean.FALSE)
                        .build());
            }

            vos.forEach(level1Catalog->{
                Long parentId = level1Catalog.getId();
                //提取二级目录
                List<WikiCatalogDO> level2CatalogsDOS = wikiCatalogDOS.stream()
                        .filter(wikiCatalog -> Objects.equals(wikiCatalog.getParentId(), parentId)
                        && Objects.equals(wikiCatalog.getLevel(),WikiCatalogLevelEnum.TWO.getValue()))
                        .sorted(Comparator.comparing(WikiCatalogDO::getSort))
                        .collect(Collectors.toList());

                List<FindWikiCatalogListRspVO> level2Catalogs = level2CatalogsDOS.stream()
                        .map(wikiCatalogDO -> FindWikiCatalogListRspVO.builder()
                                .id(wikiCatalogDO.getId())
                                .articleId(wikiCatalogDO.getArticleId())
                                .title(wikiCatalogDO.getTitle())
                                .level(wikiCatalogDO.getLevel())
                                .sort(wikiCatalogDO.getSort())
                                .editing(Boolean.FALSE)
                                .build())
                        .collect(Collectors.toList());

                level1Catalog.setChildren(level2Catalogs);
            });

        }
        return Response.success(vos);
    }

    @Override
    public Response updateWikiCatalogs(UpdateWikiCatalogReqVO updateWikiCatalogReqVO) {
        Long wikiId = updateWikiCatalogReqVO.getId();

        List<UpdateWikiCatalogItemReqVO> catalogs = updateWikiCatalogReqVO.getCatalogs();

        //1.先将此知识库中的所有文章类型更新为普通
        //查出此Wiki下所有的文章ID
        List<WikiCatalogDO> wikiCatalogDOS = wikiCatalogMapper.selectByWikiId(wikiId);
        List<Long> articleIds = wikiCatalogDOS.stream()
                .filter(wikiCatalogDO -> Objects.nonNull(wikiCatalogDO.getArticleId()))
                .map(WikiCatalogDO::getArticleId).collect(Collectors.toList());

        //更新为普通文章类型
        if(CollectionUtils.isNotEmpty(articleIds)){
            articleMapper.updateByIds(ArticleDO.builder().type(ArticleTypeEnum.NORMAL.getValue()).build(),articleIds);
        }

        //2.先删除所有知识库下的所有目录
        wikiCatalogMapper.deleteByWikiId(wikiId);

        //3.再重新插入新的目录数据
        //若传入的目录不为空
        if(CollectionUtils.isNotEmpty(catalogs)){
            //重新设置sort排序字段的值
            for (int i = 0; i < catalogs.size(); i++) {
                UpdateWikiCatalogItemReqVO vo = catalogs.get(i);
                List<UpdateWikiCatalogItemReqVO> children = vo.getChildren();
                vo.setSort(i+1);
                if(CollectionUtils.isNotEmpty(children)){
                    for (int j = 0; j < children.size(); j++) {
                        children.get(j).setSort(j+1);
                    }
                }
            }

            catalogs.forEach(catalog->{
                WikiCatalogDO wikiCatalogDO = WikiCatalogDO.builder()
                        .wikiId(wikiId)
                        .title(catalog.getTitle())
                        .level(WikiCatalogLevelEnum.ONE.getValue())
                        .sort(catalog.getSort())
                        .build();
                // 添加一级目录
                wikiCatalogMapper.insert(wikiCatalogDO);

                // 一级目录ID
                Long catalogId = wikiCatalogDO.getId();

                //获取下面的二级目录
                List<UpdateWikiCatalogItemReqVO> children = catalog.getChildren();
                //需要被更新Type字段的所有文章ID
                List<Long> updateArticleIds = Lists.newArrayList();
                if(CollectionUtils.isNotEmpty(children)){
                    List<WikiCatalogDO> level2Catalogs  = Lists.newArrayList();
                    children.forEach(child->{
                        level2Catalogs.add(WikiCatalogDO.builder()
                                .wikiId(wikiId)
                                .title(child.getTitle())
                                .level(WikiCatalogLevelEnum.TWO.getValue())
                                .sort(child.getSort())
                                .articleId(child.getArticleId())
                                .parentId(catalogId)
                                .createTime(LocalDateTime.now())
                                .updateTime(LocalDateTime.now())
                                .isDeleted(Boolean.FALSE)
                                .build());

                        updateArticleIds.add(child.getArticleId());
                    });

                    // 批量插入二级目录数据
                    wikiCatalogMapper.insertBatchSomeColumn(level2Catalogs);
                    // 更新相关文章的 type 字段，知识库类型
                    articleMapper.updateByIds(ArticleDO.builder()
                            .type(ArticleTypeEnum.WIKI.getValue()).build(), updateArticleIds);
                }
            });
        }

        return Response.success();
    }

}
