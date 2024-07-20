package com.shuige.weblog.web.service;

import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.model.vo.archive.FindArchiveArticlePageListReqVO;

/**
 * @author felix
 * @date 2024/7/20 21:19
 */
public interface ArchiveService {

    /**
     * 获取文章归档分页数据
     * @param findArchiveArticlePageListReqVO
     * @return
     */
    Response findArchivePageList(FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO);

}
