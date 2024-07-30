package com.shuige.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.shuige.weblog.common.domain.dos.ArticleDO;
import com.shuige.weblog.common.domain.dos.ArticleTagRelDO;
import com.shuige.weblog.common.domain.dos.TagDO;
import com.shuige.weblog.common.domain.mapper.ArticleMapper;
import com.shuige.weblog.common.domain.mapper.ArticleTagRelMapper;
import com.shuige.weblog.common.domain.mapper.TagMapper;
import com.shuige.weblog.common.enums.ResponseCodeEnum;
import com.shuige.weblog.common.exception.BizException;
import com.shuige.weblog.common.utils.PageResponse;
import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.convert.ArticleConvert;
import com.shuige.weblog.web.model.vo.tag.FindTagArticlePageListReqVO;
import com.shuige.weblog.web.model.vo.tag.FindTagArticlePageListRspVO;
import com.shuige.weblog.web.model.vo.tag.FindTagListRspVO;
import com.shuige.weblog.web.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author felix
 * @date 2024/7/18 21:46
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Response findTagList() {
        List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());

        List<FindTagListRspVO> vos = null;
        if(!CollectionUtils.isEmpty(tagDOS)){
            vos = tagDOS.stream()
                    .map(tagDO->FindTagListRspVO.builder()
                            .id(tagDO.getId())
                            .name(tagDO.getName())
                            .build())
                    .collect(Collectors.toList());
        }
        return Response.success(vos);
    }

    @Override
    public Response findTagPageList(FindTagArticlePageListReqVO findTagArticlePageListReqVO) {
        Long current = findTagArticlePageListReqVO.getCurrent();
        Long size = findTagArticlePageListReqVO.getSize();
        Long tagId = findTagArticlePageListReqVO.getId();

        TagDO tagDO = tagMapper.selectById(tagId);
        if(Objects.isNull(tagDO)){
            log.warn("===>该标签不存在，tagId{}",tagId);
            throw new BizException(ResponseCodeEnum.TAG_NOT_EXIST);
        }

        List<ArticleTagRelDO> articleTagRelDO = articleTagRelMapper.selectByTagId(tagId);
        if(CollectionUtils.isEmpty(articleTagRelDO)){
            log.warn("===>该标签下还未发布任何文章，tagId{}",tagId);
            return Response.success(null,"该标签下还未发布任何文章");
        }

        // 提取所有文章 ID
        List<Long> articleIds = articleTagRelDO.stream().map(ArticleTagRelDO::getArticleId).collect(Collectors.toList());

        // 根据文章 ID 集合查询文章分页数据
        Page<ArticleDO> page = articleMapper.selectPageListByArticleIds(current, size, articleIds);
        List<ArticleDO> articleDOS = page.getRecords();

        // DO 转 VO
        List<FindTagArticlePageListRspVO> vos = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(articleDOS)){
            vos = articleDOS.stream().map(articleDO -> ArticleConvert.INSTANCE.convertDO2TagArticleVO(articleDO)).collect(Collectors.toList());
        }
        return PageResponse.success(page,vos);
    }
}
