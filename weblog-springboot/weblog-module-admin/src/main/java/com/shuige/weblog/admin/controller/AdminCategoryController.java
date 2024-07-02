package com.shuige.weblog.admin.controller;

import com.shuige.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.shuige.weblog.admin.service.AdminCategoryService;
import com.shuige.weblog.common.aspect.ApiOperationLog;
import com.shuige.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author felix
 * @date 2024/7/1 14:34
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "Admin分类模块")
@SuppressWarnings("all")
public class AdminCategoryController {

    @Autowired
    private AdminCategoryService adminCategoryService;

    @PostMapping("/category/add")
    @ApiOperation(value = "添加分类")
    @ApiOperationLog(description = "添加分类")
    public Response addCategory(@RequestBody @Validated AddCategoryReqVO addCategoryReqVo){
        return adminCategoryService.addCategory(addCategoryReqVo);
    }
}
