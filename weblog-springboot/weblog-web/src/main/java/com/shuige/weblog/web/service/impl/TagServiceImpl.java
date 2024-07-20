package com.shuige.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shuige.weblog.common.domain.dos.TagDO;
import com.shuige.weblog.common.domain.mapper.TagMapper;
import com.shuige.weblog.common.utils.Response;
import com.shuige.weblog.web.model.vo.tag.FindTagListRspVO;
import com.shuige.weblog.web.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author felix
 * @date 2024/7/18 21:46
 */
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Response findTagList() {
        List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());

        List<FindTagListRspVO> vos = null;
        if(!CollectionUtils.isEmpty(tagDOS)){
            vos = tagDOS.stream()
                    .map(tagDO->FindTagListRspVO.builder()
                            .id(tagDO.getId())
                            .name(tagDO.getName())
                            .build())
                    .collect(Collectors.toList());
        }
        return Response.success(vos);
    }
}
