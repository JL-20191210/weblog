package com.shuige.weblog.admin.service;

import com.shuige.weblog.admin.model.vo.article.*;
import com.shuige.weblog.common.utils.Response;

/**
 * @author felix
 * @date 2024/7/9 14:47
 */
public interface AdminArticleService {

    /**
     *
     * @param publishArticleReqVO
     * @return
     */
    Response publishArticle(PublishArticleReqVO publishArticleReqVO);

    /**
     * 删除文章
     * @param deleteArticleReqVO
     * @return
     */
    Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO);

    Response findArticlePageList(FindArticlePageListReqVO findArticlePageListReqVO);

    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);

    Response updateArticle(UpdateArticleReqVO updateArticleReqVO);
}
