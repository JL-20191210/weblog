package com.shuige.weblog.web.controller;

import com.shuige.weblog.common.aspect.ApiOperationLog;
import com.shuige.weblog.web.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author felix
 * @date 2024/5/27 00:27
 */
@RestController
@Slf4j
public class TestController {

    @PostMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public User test(@RequestBody User user){
        return user;
    }}
