package com.shuige.weblog.common.utils;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.shuige.weblog.common.exception.BaseExceptionInterface;
import com.shuige.weblog.common.exception.BizException;
import lombok.Data;

import java.io.Serializable;

/**
 * @author felix
 * @date 2024/5/27 22:29
 */
@Data
public class Response<T> implements Serializable {

    //是否成功，默认为true
    private boolean success = true;

    private String message;

    private String errorCode;

    private T data;

    //成功响应
    public static <T> Response<T> success(){
        Response<T> response = new Response<>();
        return response;
    }

    public static <T> Response<T> success(T data){
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    //失败响应
    public static <T> Response<T> fail(){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        return response;
    }

    public static <T> Response<T> fail(String errorMessage){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(errorMessage);
        return response;
    }

    public static <T> Response<T> fail(String errorCode,String errorMessage){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setMessage(errorMessage);
        return response;
    }

    public static <T> Response<T> fail(BizException bizException){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(bizException.getErrorCode());
        response.setMessage(bizException.getErrorMessage());
        return response;
    }

    public static <T> Response<T> fail(BaseExceptionInterface baseExceptionInterface){
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(baseExceptionInterface.getErrorCode());
        response.setMessage(baseExceptionInterface.getErrorMessage());
        return response;
    }
}
