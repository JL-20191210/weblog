package com.shuige.weblog.admin.service;

import com.shuige.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.shuige.weblog.common.utils.Response;

public interface AdminBlogSettingsService {

    /**
     * 更新博客设置信息
     * @param updateBlogSettingsReqVO
     * @return
     */
    Response updateBlogSetting(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);

    /**
     * 获取博客设置详情
     * @return
     */
    Response findDetail();
}
