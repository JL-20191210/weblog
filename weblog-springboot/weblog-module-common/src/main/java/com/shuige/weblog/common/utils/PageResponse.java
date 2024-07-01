package com.shuige.weblog.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> extends Response<List<T>> {

    /**
     * 总记录数
     */
    private long total = 0l;

    /**
     * 每页记录数
     */
    private long size = 10l;

    /**
     * 当前页
     */
    private long current;

    /**
     * 总页数
     */
    private long pages;

    public static <T> PageResponse<T> success(IPage page, List<T> data){
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setCurrent(Objects.isNull(page)?1L:page.getCurrent());
        response.setSize(Objects.isNull(page)?10L:page.getSize());
        response.setPages(Objects.isNull(page)?0L:page.getPages());
        response.setTotal(Objects.isNull(page)?0L:page.getTotal());
        response.setData(data);
        return response;
    }
}
