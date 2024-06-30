package com.shuige.weblog.admin.service;

import com.shuige.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.shuige.weblog.common.utils.Response;

public interface AdminUserService {
    /**
     * 修改密码
     * @param updateAdminUserPasswordReqVO
     * @return
     */
    Response updatePassword(UpdateAdminUserPasswordReqVO  updateAdminUserPasswordReqVO);

    /**
     * 查询用户信息
     * @return
     */
    Response findUserInfo();
}
