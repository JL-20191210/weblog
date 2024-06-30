package com.shuige.weblog.admin.service.impl;

import com.shuige.weblog.admin.model.vo.user.FindUserInfoRspVO;
import com.shuige.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.shuige.weblog.admin.service.AdminUserService;
import com.shuige.weblog.common.domain.mapper.UserMapper;
import com.shuige.weblog.common.enums.ResponseCodeEnum;
import com.shuige.weblog.common.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 修改密码
     * @param updateAdminUserPasswordReqVO
     * @return
     */
    @Override
    public Response updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO) {
        String username = updateAdminUserPasswordReqVO.getUsername();
        String password = updateAdminUserPasswordReqVO.getPassword();

        String encodePassword = passwordEncoder.encode(password);

        int count = userMapper.updatePasswordByUsername(username,encodePassword);

        return count==1?Response.success():Response.fail(ResponseCodeEnum.USERNAME_NOT_FOUND);
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @Override
    public Response findUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return Response.success(FindUserInfoRspVO.builder().username(username).build());
    }
}
