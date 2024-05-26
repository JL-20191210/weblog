package com.shuige.weblog.common.aspect;

import java.lang.annotation.*;

/**
 * @author felix
 * @date 2024/5/26 23:47
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiOperationLog {

    /**
     * API 功能描述
     * @return
     *
     */
    String description() default "";
}
