package com.shuige.weblog.web.convert;

import com.shuige.weblog.common.domain.dos.BlogSettingsDO;
import com.shuige.weblog.web.model.vo.blogsetting.FindBlogSettingsDetailRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author felix
 * @date 2024/7/18 21:54
 */
@Mapper
public interface BlogSettingConvert {

    /**
     * 初始化convert实例
     */
    BlogSettingConvert INSTANCE = Mappers.getMapper(BlogSettingConvert.class);

    FindBlogSettingsDetailRspVO convertDO2VO(BlogSettingsDO bean);

}
