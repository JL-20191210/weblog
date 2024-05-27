package com.shuige.weblog.common.aspect;

import com.shuige.weblog.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author felix
 * @date 2024/5/26 23:54
 * 日志切面类
 */
@Aspect
@Component
@Slf4j
public class ApiOperationLogAspect {

    /**
     * 以自定义@ApiOperationLog注解为切点，凡是添加@ApiOperationLog的方法，都会执行环绕中的代码
     */
    @Pointcut("@annotation(com.shuige.weblog.common.aspect.ApiOperationLog)")
    public void apiOperationLog(){}

    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{

        try {
            //请求开始时间
            long startTime = System.currentTimeMillis();

            //MDC traceId表示跟踪ID，值这里直接用uuid
            MDC.put("traceId", UUID.randomUUID().toString());

            //获取被请求的类和方法
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();

            //请求入参
            Object[] args = joinPoint.getArgs();
            //入参砖JSON字符串
            String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(", "));

            //功能描述信息
            String description = getApiOperationLogDescription(joinPoint);

            //打印请求相关参数
            log.info("==== 请求开始: [{}], 入参: {}, 请求类: {}, 请求方法: {} === ",
                    description, argsJsonStr, className, methodName);

            //执行切点方法
            Object result = joinPoint.proceed();

            //执行耗时
            long executionTime = System.currentTimeMillis()- startTime;

            //打印出参等相关信息
            log.info("=== 请求结束: [{}], 耗时: {}ms, 出参: {} === ",
                    description, executionTime, JsonUtil.toJsonString(result));

            return result;
        } finally {
            //清除MDC值
            MDC.clear();
        }
    }

    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint){
        //1. 从ProceedingJoinPoint获取MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //2. 使用MethodSignature获取当前被注解的Method
        Method method = signature.getMethod();

        //3. 从method中提取LogException注解
        ApiOperationLog apiOperationLog = method.getAnnotation(ApiOperationLog.class);

        //4. 从LogException注解获取description属性
        return apiOperationLog.description();
    }
    /**
     * 转JSON字符串
     * @return
     */
    private Function<Object,String> toJsonStr(){
        return JsonUtil::toJsonString;
    }
}
