package com.shuige.weblog.admin.service;


import com.shuige.weblog.admin.model.vo.tag.AddTagReqVO;
import com.shuige.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.shuige.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.shuige.weblog.admin.model.vo.tag.SearchTagReqVO;
import com.shuige.weblog.common.utils.PageResponse;
import com.shuige.weblog.common.utils.Response;

public interface AdminTagService {

    /**
     * 添加标签集合
     * @param addTagReqVO
     * @return
     */
    Response addTags(AddTagReqVO addTagReqVO);

    /**
     * 查询标签列表
     * @param findTagPageListReqVO
     * @return
     */
    PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO);

    /**
     * 删除标签
     * @param deleteTagReqVO
     * @return
     */
    Response deleteTag(DeleteTagReqVO deleteTagReqVO);

    /**
     * 标签模糊查询
     * @param searchTagReqVO
     * @return
     */
    Response searchTag(SearchTagReqVO searchTagReqVO);

    /**
     * 查询标签 Select 列表数据
     * @return
     */
    Response findTagSelectList();
}
