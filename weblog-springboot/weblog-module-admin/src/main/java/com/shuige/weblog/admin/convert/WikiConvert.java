package com.shuige.weblog.admin.convert;

import com.shuige.weblog.admin.model.vo.wiki.FindWikiPageListRspVO;
import com.shuige.weblog.common.domain.dos.WikiDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author felix
 * @date 2024/9/2 15:49
 */
@Mapper
public interface WikiConvert {
    /**
     * 初始化convert实例
     */
    WikiConvert INSTANCE = Mappers.getMapper(WikiConvert.class);

    /**
     * WikiDO -> FindWikiPageListRspVO
     * @param bean
     * @return
     */
    @Mapping(target = "isTop",expression = "java(bean.getWeight() > 0)")
    FindWikiPageListRspVO convertDOTOVO(WikiDO bean);
}
