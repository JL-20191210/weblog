package com.shuige.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuige.weblog.admin.model.vo.tag.*;
import com.shuige.weblog.admin.service.AdminTagService;
import com.shuige.weblog.common.domain.dos.TagDO;
import com.shuige.weblog.common.domain.mapper.TagMapper;
import com.shuige.weblog.common.enums.ResponseCodeEnum;
import com.shuige.weblog.common.model.vo.SelectRspVO;
import com.shuige.weblog.common.utils.PageResponse;
import com.shuige.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Response addTags(AddTagReqVO addTagReqVO) {
        // 将标签集合转换为DO
        List<TagDO> tagDOS = addTagReqVO.getTags().stream()
                .map(tagName->TagDO.builder()
                        .name(tagName.trim())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        // 批量保存
        try {
            saveBatch(tagDOS);
        } catch (Exception e) {
            log.warn("标签已存在",e);
        }
        return Response.success(tagDOS,"添加标签成功");
    }

    @Override
    public PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO) {
        Long current = findTagPageListReqVO.getCurrent();
        Long size = findTagPageListReqVO.getSize();
        String name = findTagPageListReqVO.getName();
        LocalDate startDate = findTagPageListReqVO.getStartDate();
        LocalDate endDate = findTagPageListReqVO.getEndDate();

        // 查询
        Page<TagDO> page = tagMapper.selectPageList(current, size, name, startDate, endDate);

        List<TagDO> records = page.getRecords();

        //do 转 vo
        List <FindTagPageListRspVO> vos = null;
        if(!CollectionUtils.isEmpty(records)){
            vos = records.stream()
                    .map(tagDO -> FindTagPageListRspVO.builder()
                            .id(tagDO.getId())
                            .name(tagDO.getName())
                            .createTime(tagDO.getCreateTime())
                            .build())
                    .collect(Collectors.toList());
        }
        return PageResponse.success(page,vos);
    }

    @Override
    public Response deleteTag(DeleteTagReqVO deleteTagReqVO) {
        Long id = deleteTagReqVO.getId();

        int count = tagMapper.deleteById(id);
        return count==1?Response.success():Response.fail(ResponseCodeEnum.TAG_NOT_EXIST);
    }

    @Override
    public Response searchTag(SearchTagReqVO searchTagReqVO) {
        String key = searchTagReqVO.getKey();

        List<TagDO> tagDOS = tagMapper.selectByKey(key);

        List<SelectRspVO> vos = null;
        if(!CollectionUtils.isEmpty(tagDOS)){
            vos = tagDOS.stream()
                    .map(tagDO -> SelectRspVO.builder()
                            .label(tagDO.getName())
                            .value(tagDO.getId())
                            .build())
                    .collect(Collectors.toList());
        }
        return Response.success(vos);
    }
}
