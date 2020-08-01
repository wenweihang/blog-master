package com.zoe.blog.controller.admin;

import com.zoe.blog.dto.LogQueryParam;
import com.zoe.blog.model.Log;
import com.zoe.blog.service.LogService;
import com.zoe.blog.utils.CommonPage;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Created by liuxuelian on 2020/08/01 11:11
 */
@Api(tags = "LogController", description = "日志管理")
@RestControllerAdvice
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogService logService;

    @ApiOperation("查询日志")
    @GetMapping
    public CommonResult<CommonPage<Log>> list(LogQueryParam queryParam) {
        List<Log> logs = logService.list(queryParam);
        return CommonResult.success(CommonPage.restPage(logs));
    }

    @ApiOperation("查询所有日志等级类别")
    @GetMapping("/level")
    public CommonResult<CommonPage<String>> getLevel() {
        List<String> levels= logService.getLevelGroup();
        return CommonResult.success(CommonPage.restPage(levels));
    }

}
