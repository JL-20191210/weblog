package com.shuige.weblog.web.service;

import com.shuige.weblog.common.utils.Response;

/**
 * @author felix
 * @date 2024/7/18 21:55
 */
public interface BlogSettingService {

    /**
     * 获取博客设置信息
     * @return
     */
    Response findDetail();
}
