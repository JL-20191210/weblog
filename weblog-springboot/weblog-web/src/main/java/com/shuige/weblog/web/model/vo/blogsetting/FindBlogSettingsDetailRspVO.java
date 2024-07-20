package com.shuige.weblog.web.model.vo.blogsetting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author felix
 * @date 2024/7/18 21:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindBlogSettingsDetailRspVO {
    private String logo;
    private String name;
    private String author;
    private String introduction;
    private String avatar;
    private String githubHomepage;
    private String csdnHomepage;
    private String giteeHomepage;
    private String zhihuHomepage;
}