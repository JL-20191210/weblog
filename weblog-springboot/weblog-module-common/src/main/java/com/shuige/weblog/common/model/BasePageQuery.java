package com.shuige.weblog.common.model;

import lombok.Data;

@Data
public class BasePageQuery {

    /**
     * 当前页
     */
    private Long current = 1L;

    /**
     * 每页大小
     */
    private Long size = 10L;
}
