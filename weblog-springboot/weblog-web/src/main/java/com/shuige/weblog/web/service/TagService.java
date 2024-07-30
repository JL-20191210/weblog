package com.shuige.weblog.web.service;

import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.model.vo.tag.FindTagArticlePageListReqVO;

/**
 * @author felix
 * @date 2024/7/18 21:45
 */
public interface TagService {
    /**
     * 查询标签列表
     * @return
     */
    Response findTagList();

    /**
     * 查询标签下文章分页数据
     * @param findTagArticlePageListReqVO
     * @return
     */
    Response findTagPageList(FindTagArticlePageListReqVO findTagArticlePageListReqVO);
}
