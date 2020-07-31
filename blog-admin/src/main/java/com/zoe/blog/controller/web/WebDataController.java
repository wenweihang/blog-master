package com.zoe.blog.controller.web;

import com.zoe.blog.model.Uv;
import com.zoe.blog.service.WebDataService;
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
 * Created by liuxuelian on 2020/07/30 17:37
 */
@Api(tags = "WebDataController", description = "首页数据统计")
@RestController
@RequestMapping("/web/data")
public class WebDataController {
    @Autowired
    WebDataService webDataService;

    @ApiOperation("获取最近七天的访问量")
    @GetMapping("/seven")
    public CommonResult<CommonPage<Uv>> getRecentlySevenDays() {
        List<Uv> uvs = webDataService.getRecentlySevenDays();
        return CommonResult.success(CommonPage.restPage(uvs));
    }
}
