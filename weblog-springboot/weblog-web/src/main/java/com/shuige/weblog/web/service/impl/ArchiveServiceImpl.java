package com.shuige.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import com.shuige.weblog.common.domain.dos.ArticleDO;
import com.shuige.weblog.common.domain.mapper.ArticleMapper;
import com.shuige.weblog.common.utils.PageResponse;
import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.convert.ArticleConvert;
import com.shuige.weblog.web.model.vo.archive.FindArchiveArticlePageListReqVO;
import com.shuige.weblog.web.model.vo.archive.FindArchiveArticlePageListRspVO;
import com.shuige.weblog.web.model.vo.archive.FindArchiveArticleRspVO;
import com.shuige.weblog.web.service.ArchiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author felix
 * @date 2024/7/20 21:21
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取文章归档分页数据
     * @param findArchiveArticlePageListReqVO
     * @return
     */
    @Override
    public Response findArchivePageList(FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO) {
        Long current = findArchiveArticlePageListReqVO.getCurrent();
        Long size = findArchiveArticlePageListReqVO.getSize();

        // 分页查询
        IPage<ArticleDO> page = articleMapper.selectPageList(current, size, null, null, null,null);
        List<ArticleDO> articleDOS = page.getRecords();

        // 不要随便赋值null
        //List<FindArchiveArticlePageListRspVO> vos = null; 错误写法
        List<FindArchiveArticlePageListRspVO> vos = Lists.newArrayList();
        if(!CollectionUtils.isEmpty(articleDOS)){
            // DO 转 VO
            List<FindArchiveArticleRspVO> archiveArticleRspVOS = articleDOS.stream()
                    .map(articleDO -> ArticleConvert.INSTANCE.convertDO2ArchiveArticleVO(articleDO))
                    .collect(Collectors.toList());

            // 按创建的月份进行分组
            Map<YearMonth, List<FindArchiveArticleRspVO>> map = archiveArticleRspVOS.stream().collect(Collectors.groupingBy(FindArchiveArticleRspVO::getCreateMonth));
            //使用TreeMap按月份倒序排列
            Map<YearMonth, List<FindArchiveArticleRspVO>> sortedMap = new TreeMap<>(Collections.reverseOrder());
            sortedMap.putAll(map);

            //遍历排序后的map，将其转换为归档VO
            sortedMap.forEach((k,v)->vos.add(FindArchiveArticlePageListRspVO.builder().month(k).articles(v).build()));
        }
        return PageResponse.success(page, vos);
    }
}
