package com.zoe.blog.controller.web;

import com.zoe.blog.controller.admin.ArticleController;
import com.zoe.blog.model.User;
import com.zoe.blog.service.WebUserDetailService;
import com.zoe.blog.utils.CommonResult;
import com.zoe.blog.utils.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuxuelian on 2020/07/29 16:50
 */
@Api(tags = "WebUserDetailController", description = "获取展示在博客页面的博主信息")
@RestController
@RequestMapping("/web/user")
public class WebUserDetailController {

    private static final Logger LOGGER =  Logger.getLogger(ArticleController.class);

    @Autowired
    WebUserDetailService webUserDetailService;

    @ApiOperation("获取博主信息")
    @GetMapping
    public CommonResult<User> getUserDetail() {
        LogUtil.create();
        LOGGER.info("访问博客");

        User user = webUserDetailService.getUserDetail();
        return CommonResult.success(user);
    }
}
