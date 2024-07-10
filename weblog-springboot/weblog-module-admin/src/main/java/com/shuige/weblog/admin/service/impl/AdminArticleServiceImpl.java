package com.shuige.weblog.admin.service.impl;

import com.google.common.collect.Lists;
import com.shuige.weblog.admin.model.vo.article.PublishArticleReqVO;
import com.shuige.weblog.admin.service.AdminArticleService;
import com.shuige.weblog.common.domain.dos.*;
import com.shuige.weblog.common.domain.mapper.*;
import com.shuige.weblog.common.enums.ResponseCodeEnum;
import com.shuige.weblog.common.exception.BizException;
import com.shuige.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author felix
 * @date 2024/7/9 14:49
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class AdminArticleServiceImpl implements AdminArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleContentMapper articleContentMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;

    /**
     * 发布文章
     * @param publishArticleReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response publishArticle(PublishArticleReqVO publishArticleReqVO) {
        // VO转ArticleDO,并保存
        ArticleDO articleDO = ArticleDO.builder()
                .title(publishArticleReqVO.getTitle())
                .cover(publishArticleReqVO.getCover())
                .summary(publishArticleReqVO.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        articleMapper.insert(articleDO);
        // 拿到插入记录的主键ID
        Long articleId = articleDO.getId();

        // VO转ArticleContentDO，并保存
        ArticleContentDO articleContentDO = ArticleContentDO.builder()
                .articleId(articleId)
                .content(publishArticleReqVO.getContent())
                .build();
        articleContentMapper.insert(articleContentDO);

        Long categoryId = publishArticleReqVO.getCategoryId();
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if(Objects.isNull(categoryDO)){
            log.warn("==>分类不存在，categoryId{}",categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }

        ArticleCategoryRelDO articleCategoryRelDO = ArticleCategoryRelDO.builder()
                .articleId(articleId)
                .categoryId(categoryId)
                .build();

        articleCategoryRelMapper.insert(articleCategoryRelDO);

        // 保存文章关联的标签集合
        List<String> publishTags = publishArticleReqVO.getTags();
        insertTags(articleId,publishTags);

        return Response.success();
    }

    /**
     * 保存文章关联的标签集合
     * @param publishTags
     */
    private void insertTags(Long articleId,List<String> publishTags){
        // 筛选提交的标签(不存在的)
        List<String> notExistTags = null;
        // 筛选提交的标签(已存在的)
        List<String> existedTags = null;

        //查询出所有的标签
        List<TagDO> tagDOS = tagMapper.selectList(null);

        if(CollectionUtils.isEmpty(tagDOS)){
            notExistTags = publishTags;
        }else{
            List<String> tagIds = tagDOS.stream().map(tagDO -> String.valueOf(tagDO.getId())).collect(Collectors.toList());
            // 表中已添加相关标签，则需要筛选
            // 通过标签 ID 来筛选，包含对应 ID 则表示提交的标签是表中存在的
            existedTags = publishTags.stream().filter(publishTag->tagIds.contains(publishTag)).collect(Collectors.toList());
            // 否则则是不存在的
            notExistTags = publishTags.stream().filter(publishTag->!tagIds.contains(publishTag)).collect(Collectors.toList());

            // 补充逻辑
            // 还有一种逻辑：按字符串名称提交上来的标签，也有可能是表中已经存在的，比如表中已经有了Java标签，用户提交了个Java小写的标签，需要内部替换为Java标签
            Map<String,Long> tagNameIdMap = tagDOS.stream().collect(Collectors.toMap(tagDO -> tagDO.getName().toLowerCase(),TagDO::getId));

            // 使用迭代器进行安全的删除操作
            Iterator<String> iterator = notExistTags.iterator();
            while (iterator.hasNext()){
                String notExistTag = iterator.next();
                // 转小写，若map中存在相同的key，则表示该标签是重复标签
                if(tagNameIdMap.containsKey(notExistTag.toLowerCase())){
                    // 从不存在的标签集合中清除
                    iterator.remove();
                    // 并将对应的ID添加到已存在的标签集合中
                    existedTags.add(String.valueOf(tagNameIdMap.get(notExistTag.toLowerCase())));
                }
            }

            // 将提交上来的，已存在于表中的标签，文章-标签关联关系入库
            if(!CollectionUtils.isEmpty(existedTags)){
                List<ArticleTagRelDO>  articleTagRelDOS = Lists.newArrayList();
                existedTags.forEach(tagId->{
                    ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                            .articleId(articleId)
                            .tagId(Long.valueOf(tagId))
                            .build();
                    articleTagRelDOS.add(articleTagRelDO);
                });
                articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
            }

            // 将提交的上来的，不存在于表中的标签，入库保存
            if(!CollectionUtils.isEmpty(notExistTags)){
                List<ArticleTagRelDO> articleTagRelDOS = Lists.newArrayList();
                notExistTags.forEach(tagName->{
                    TagDO tagDO = TagDO.builder()
                            .name(tagName)
                            .createTime(LocalDateTime.now())
                            .updateTime(LocalDateTime.now())
                            .build();

                    tagMapper.insert(tagDO);

                    // 拿到保存的标签ID
                    Long tagId = tagDO.getId();

                    // 文章-标签关联关系
                    ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                            .articleId(articleId)
                            .tagId(tagId)
                            .build();

                    articleTagRelDOS.add(articleTagRelDO);
                    // 批量插入
                    articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
                });
            }
        }
    }
}
