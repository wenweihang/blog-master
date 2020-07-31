package com.zoe.blog.controller.web;

import com.zoe.blog.dto.UvAmountParam;
import com.zoe.blog.model.Uv;
import com.zoe.blog.service.UvService;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuxuelian on 2020/07/31 12:46
 */
@Api(tags = "UvController", description = "访问量和评论数统计")
@RestController
@RequestMapping("/web/uv")
public class UvController {

    @Autowired
    UvService uvService;

    @ApiOperation("查询当前的访问量和评论数")
    @GetMapping("/current")
    public CommonResult<Uv> getCurrentDay() {
        Uv uv = uvService.getCurrentDay();
        return CommonResult.success(uv);
    }

    @ApiOperation("查询总访问量和总评论数")
    @GetMapping("/amount")
    public CommonResult<UvAmountParam> getUvAmount() {
        UvAmountParam uvAmountParam = uvService.getUvAmount();
        return CommonResult.success(uvAmountParam);
    }

}
