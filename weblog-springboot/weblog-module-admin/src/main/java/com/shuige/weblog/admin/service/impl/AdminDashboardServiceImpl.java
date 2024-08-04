package com.shuige.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shuige.weblog.admin.model.vo.dashboard.FindDashboardPVStatisticsInfoRspVO;
import com.shuige.weblog.admin.model.vo.dashboard.FindDashboardStatisticsInfoRspVO;
import com.shuige.weblog.admin.service.AdminDashboardService;
import com.shuige.weblog.common.constant.Constants;
import com.shuige.weblog.common.domain.dos.ArticleDO;
import com.shuige.weblog.common.domain.dos.ArticlePublishCountDO;
import com.shuige.weblog.common.domain.dos.StatisticsArticlePVDO;
import com.shuige.weblog.common.domain.mapper.ArticleMapper;
import com.shuige.weblog.common.domain.mapper.CategoryMapper;
import com.shuige.weblog.common.domain.mapper.StatisticsArticlePVMapper;
import com.shuige.weblog.common.domain.mapper.TagMapper;
import com.shuige.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author felix
 * @date 2024/8/2 17:48
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private StatisticsArticlePVMapper articlePVMapper;

    /**
     * 获取仪表盘基础统计信息
     *
     * @return
     */
    @Override
    public Response findDashboardStatistics() {
        // 查询文章总数
        Long articleTotalCount = articleMapper.selectCount(Wrappers.emptyWrapper());

        // 查询分类总数
        Long categoryTotalCount = categoryMapper.selectCount(Wrappers.emptyWrapper());

        // 查询标签总数
        Long tagTotalCount = tagMapper.selectCount(Wrappers.emptyWrapper());

        // 总浏览量
        List<ArticleDO> articleDOS = articleMapper.selectAllReadNum();
        Long pvTotalCount = 0L;

        if (!CollectionUtils.isEmpty(articleDOS)) {
            // 所有 read_num 相加
            pvTotalCount = articleDOS.stream().mapToLong(ArticleDO::getReadNum).sum();
        }

        // 组装 VO 类
        FindDashboardStatisticsInfoRspVO vo = FindDashboardStatisticsInfoRspVO.builder()
                .articleTotalCount(articleTotalCount)
                .categoryTotalCount(categoryTotalCount)
                .tagTotalCount(tagTotalCount)
                .pvTotalCount(pvTotalCount)
                .build();

        return Response.success(vo);
    }

    @Override
    public Response findDashboardPublishArticleStatistics() {
        // 当前时间
        LocalDate currentDate = LocalDate.now();

        // 一年前的时间
        LocalDate startDate = currentDate.minusYears(1);

        List<ArticlePublishCountDO> articlePublishCountDOS = articleMapper.selectDateArticlePublishCount(startDate, currentDate);

        Map<LocalDate,Long> map = null;
        if(!CollectionUtils.isEmpty(articlePublishCountDOS)){
            Map<LocalDate,Long> dateArticleCountMap = articlePublishCountDOS.stream().collect(Collectors.toMap(ArticlePublishCountDO::getDate,ArticlePublishCountDO::getCount));
            map = Maps.newLinkedHashMap();
            for(;startDate.isBefore(currentDate)||startDate.isEqual(currentDate);startDate=startDate.plusDays(1)){
                // 以日期作为 key 从 dateArticleCountMap 中取文章发布总量
                Long count = dateArticleCountMap.get(startDate);
                // 设置到返参 Map
                map.put(startDate, Objects.isNull(count)?0:count);
            }
        }

        return Response.success(map);
    }

    @Override
    public Response findDashboardPVStatistics() {
        List<StatisticsArticlePVDO> articlePVDOS = articlePVMapper.selectLatestWeekRecords();

        Map<LocalDate, Long> pvDateCountMap = Maps.newHashMap();
        if(!CollectionUtils.isEmpty(articlePVDOS)){
            pvDateCountMap = articlePVDOS.stream().collect(Collectors.toMap(StatisticsArticlePVDO::getPvDate,StatisticsArticlePVDO::getPvCount));
        }

        FindDashboardPVStatisticsInfoRspVO vo = null;

        // 日期集合
        List<String> pvDates = Lists.newArrayList();

        // PV 集合
        List<Long> pvCounts = Lists.newArrayList();

        LocalDate currDate = LocalDate.now();

        LocalDate tmpDate = currDate.minusWeeks(1);

        for(;tmpDate.isBefore(currDate)||tmpDate.isEqual(currDate);tmpDate = tmpDate.plusDays(1)){
            pvDates.add(tmpDate.format(Constants.MONTH_DAY_FORMATTER));
            Long pvCount = pvDateCountMap.get(tmpDate);
            pvCounts.add(Objects.isNull(pvCount)?0:pvCount);
        }

        vo = FindDashboardPVStatisticsInfoRspVO.builder()
                .pvDates(pvDates)
                .pvCounts(pvCounts)
                .build();
        return Response.success(vo);
    }
}
