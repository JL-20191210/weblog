package com.shuige.weblog.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author felix
 * @date 2024/5/27 23:01
 * @description 自定义业务异常
 */
@Getter
@Setter
public class BizException extends RuntimeException{

    // 异常码
    private String errorCode;
    // 错误信息
    private String errorMessage;

    public BizException(BaseExceptionInterface baseExceptionInterface){
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();
    }
}
