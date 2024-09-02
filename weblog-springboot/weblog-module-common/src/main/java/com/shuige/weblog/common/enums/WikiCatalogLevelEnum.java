package com.shuige.weblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author felix
 * @date 2024/9/2 10:31
 */
@Getter
@AllArgsConstructor
public enum WikiCatalogLevelEnum {

    // 一级目录
    ONE(1),
    // 二级目录
    TWO(2);

    private Integer value;

}
