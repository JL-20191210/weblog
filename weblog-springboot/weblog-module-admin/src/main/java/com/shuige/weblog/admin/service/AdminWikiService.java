package com.shuige.weblog.admin.service;

import com.shuige.weblog.admin.model.vo.wiki.AddWikiReqVO;
import com.shuige.weblog.admin.model.vo.wiki.DeleteWikiReqVO;
import com.shuige.weblog.admin.model.vo.wiki.FindWikiPageListReqVO;
import com.shuige.weblog.admin.model.vo.wiki.UpdateWikiIsTopReqVO;
import com.shuige.weblog.common.utils.Response;

/**
 * @author felix
 * @date 2024/9/2 09:52
 */
public interface AdminWikiService {
    /**
     * 新增知识库
     * @param addWikiReqVO
     * @return
     */
    Response addWiki(AddWikiReqVO addWikiReqVO);

    /**
     * 删除知识库
     * @param deleteWikiReqVO
     * @return
     */
    Response deleteWiki(DeleteWikiReqVO deleteWikiReqVO);

    /**
     * 知识库分页查询
     * @param findWikiPageListReqVO
     * @return
     */
    Response findWikiPageList(FindWikiPageListReqVO findWikiPageListReqVO);

    /**
     * 更新知识库置顶状态
     * @param updateWikiIsTopReqVO
     * @return
     */
    Response updateWikiIsTop(UpdateWikiIsTopReqVO updateWikiIsTopReqVO);

}
