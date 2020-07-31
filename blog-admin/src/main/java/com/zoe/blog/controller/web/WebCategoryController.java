package com.zoe.blog.controller.web;

import com.zoe.blog.dto.WebCategoryDto;
import com.zoe.blog.service.WebCategoryService;
import com.zoe.blog.utils.CommonPage;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/30 20:49
 */
@Api(tags = "WebCategoryController", description = "对文章分类进行统计")
@RestController
@RequestMapping("/web/category")
public class WebCategoryController {

    @Autowired
    WebCategoryService webCategoryService;

    @ApiOperation("统计文章分类")
    @GetMapping
    public CommonResult<CommonPage<WebCategoryDto>> getCategoryCount() {
        List<WebCategoryDto> categoryDtos = webCategoryService.getCategoryCount();
        return CommonResult.success(CommonPage.restPage(categoryDtos));
    }

}
