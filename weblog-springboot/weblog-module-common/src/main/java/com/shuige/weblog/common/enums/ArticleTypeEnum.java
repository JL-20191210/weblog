package com.shuige.weblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author felix
 * @date 2024/9/2 10:32
 */
@Getter
@AllArgsConstructor
public enum ArticleTypeEnum {

    NORMAL(1, "普通"),
    WIKI(2, "收录于知识库");

    private Integer value;
    private String description;

}
