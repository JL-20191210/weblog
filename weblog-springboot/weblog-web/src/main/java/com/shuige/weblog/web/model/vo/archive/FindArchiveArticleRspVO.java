package com.shuige.weblog.web.model.vo.archive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * @author felix
 * @date 2024/7/20 21:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArchiveArticleRspVO {
    private Long id;
    private String cover;
    private String title;
    /**
     * 发布日期
     */
    private LocalDate createDate;

    /**
     * 发布的月份（此字段不需要展示在前端，主要用于按月份分组使用）
     * YearMonth 是 Java 8 中引入的日期时间类，用于表示年份和月份的组合。
     */
    private YearMonth createMonth;
}
