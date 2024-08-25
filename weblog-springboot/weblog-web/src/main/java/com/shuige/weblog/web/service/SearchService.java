package com.shuige.weblog.web.service;

import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.model.vo.search.SearchArticlePageListReqVO;

public interface SearchService {

    /**
     * 关键词分页搜索
     * @param searchArticlePageListReqVO
     * @return
     */
    Response searchArticlePageList(SearchArticlePageListReqVO searchArticlePageListReqVO);
}