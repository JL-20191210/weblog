package com.shuige.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuige.weblog.common.domain.dos.UserDO;

public interface UserMapper extends BaseMapper<UserDO> {
    default UserDO findByUsername(String name){
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername,name);
        return selectOne(wrapper);
    }
}
