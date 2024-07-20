package com.shuige.weblog.web.service.impl;

import com.shuige.weblog.common.domain.dos.BlogSettingsDO;
import com.shuige.weblog.common.domain.mapper.BlogSettingsMapper;
import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.convert.BlogSettingConvert;
import com.shuige.weblog.web.model.vo.blogsetting.FindBlogSettingsDetailRspVO;
import com.shuige.weblog.web.service.BlogSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author felix
 * @date 2024/7/18 21:56
 */
@Service
@Slf4j
public class BlogSettingServiceImpl implements BlogSettingService {

    @Autowired
    private BlogSettingsMapper blogSettingsMapper;

    /**
     * 获取博客设置详情
     * @return
     */
    @Override
    public Response findDetail() {
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        //DO2VO
        FindBlogSettingsDetailRspVO vo = BlogSettingConvert.INSTANCE.convertDO2VO(blogSettingsDO);

        return Response.success(vo);
    }
}
