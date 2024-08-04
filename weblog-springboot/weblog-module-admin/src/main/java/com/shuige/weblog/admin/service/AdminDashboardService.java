package com.shuige.weblog.admin.service;

import com.shuige.weblog.common.utils.Response;

/**
 * @author felix
 * @date 2024/8/2 17:47
 */
public interface AdminDashboardService{

    Response findDashboardStatistics();

    Response findDashboardPublishArticleStatistics();


    /**
     * 获取文章最近一周 PV 访问量统计信息
     * @return
     */
    Response findDashboardPVStatistics();
}
