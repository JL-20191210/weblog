package com.shuige.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shuige.weblog.common.domain.dos.ArticleDO;
import com.shuige.weblog.common.domain.mapper.ArticleMapper;
import com.shuige.weblog.common.domain.mapper.CategoryMapper;
import com.shuige.weblog.common.domain.mapper.TagMapper;
import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.model.vo.statistics.FindStatisticsInfoRspVO;
import com.shuige.weblog.web.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

@Service
@Slf4j
@SuppressWarnings("all")
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;


    @Override
    public Response findInfo() {
        Long articleTotalCount  = articleMapper.selectCount(Wrappers.emptyWrapper());

        Long categoryTotalCount  = categoryMapper.selectCount(Wrappers.emptyWrapper());

        Long tagTotalCount  = tagMapper.selectCount(Wrappers.emptyWrapper());

        List<ArticleDO> articleDOS = articleMapper.selectAllReadNum();

        Long pvTotalCount = 0L;
        if(CollectionUtils.isNotEmpty(articleDOS)){
            // 所有 read_num 相加
            pvTotalCount = articleDOS.stream().mapToLong(ArticleDO::getReadNum).sum();
        }

        FindStatisticsInfoRspVO vo = FindStatisticsInfoRspVO.builder()
                .articleTotalCount(articleTotalCount)
                .categoryTotalCount(categoryTotalCount)
                .tagTotalCount(tagTotalCount)
                .pvTotalCount(pvTotalCount)
                .build();
        return Response.success(vo);
    }
}
