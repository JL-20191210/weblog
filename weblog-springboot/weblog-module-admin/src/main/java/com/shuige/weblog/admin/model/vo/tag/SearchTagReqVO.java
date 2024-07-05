package com.shuige.weblog.admin.model.vo.tag;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author felix
 * @date 2024/7/1 12:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "模糊查询标签VO")
public class SearchTagReqVO {

    @NotBlank(message = "标签关键字不能为空")
    private String key;
}
