package com.shuige.weblog.web.controller;

import com.shuige.weblog.common.aspect.ApiOperationLog;
import com.shuige.weblog.common.enums.ResponseCodeEnum;
import com.shuige.weblog.common.exception.BizException;
import com.shuige.weblog.common.utils.JsonUtil;
import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

/**
 * @author felix
 * @date 2024/5/27 00:27
 */
@RestController
@Slf4j
@Api(tags = "首页模块")
public class TestController {

    @PostMapping("/test1")
    @ApiOperationLog(description = "测试接口")
    public User test1(@RequestBody User user){
        return user;
    }

    @PostMapping("/test2")
    @ApiOperationLog(description = "测试接口")
    public ResponseEntity<String> test2(@RequestBody @Validated User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg  = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errorMsg);
        }
        return ResponseEntity.ok("参数没问题");
    }

    @PostMapping("/test3")
    @ApiOperationLog(description = "测试接口")
    public Response test3(@RequestBody @Validated User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg  = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return Response.fail(errorMsg);
        }
        return Response.success("参数没问题");
    }

    @PostMapping("/test4")
    @ApiOperationLog(description = "测试接口")
    public Response test4(@RequestBody @Validated User user, BindingResult bindingResult) {
        throw new BizException(ResponseCodeEnum.PRODUCT_NOT_FOUND);
    }

    @PostMapping("/test5")
    @ApiOperationLog(description = "测试接口")
    public Response test5(@RequestBody @Validated User user, BindingResult bindingResult) {
        // 主动定义一个运行时异常，分母不能为零
        int i = 1 / 0;
        return Response.success();
    }

    @PostMapping("/test6")
    @ApiOperationLog(description = "测试接口")
    public Response test6(@RequestBody @Validated User user) {
        return Response.success();
    }

    @PostMapping("/test7")
    @ApiOperationLog(description = "测试接口Log")
    @ApiOperation(value = "测试接口")
    public Response test7(@RequestBody @Validated User user){

        log.info(JsonUtil.toJsonString(user));

        user.setCreateTime(LocalDateTime.now());
        user.setUpdateDate(LocalDate.now());
        user.setTime(LocalTime.now());

        return Response.success(user);
    }
}
