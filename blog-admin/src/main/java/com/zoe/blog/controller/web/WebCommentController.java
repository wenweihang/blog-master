package com.zoe.blog.controller.web;

import com.zoe.blog.controller.admin.ArticleController;
import com.zoe.blog.model.Comment;
import com.zoe.blog.service.WebCommentService;
import com.zoe.blog.utils.CommonPage;
import com.zoe.blog.utils.CommonResult;
import com.zoe.blog.utils.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by liuxuelian on 2020/07/30 11:56
 */
@Api(tags = "WebCommentController", description = "博客展示评论管理")
@RestController
@RequestMapping("/web/comment")
public class WebCommentController {
    private static final Logger LOGGER =  Logger.getLogger(ArticleController.class);

    @Autowired
    WebCommentService webCommentService;

    @ApiOperation("查询评论")
    @GetMapping("/{id}")
    public CommonResult<CommonPage<Comment>> list(@PathVariable("id") Integer articleId) {
        List<Comment> comments = webCommentService.list(articleId);
        return CommonResult.success(CommonPage.restPage(comments));
    }

    @ApiOperation("获取最新的前8条评论")
    @GetMapping("/eight")
    public CommonResult<CommonPage<Comment>> getCommentsLimitEight() {
        List<Comment> comments = webCommentService.getCommentLimitEight();
        return CommonResult.success(CommonPage.restPage(comments));
    }


    @ApiOperation("添加评论")
    @PostMapping
    public CommonResult insert(@RequestBody @Valid Comment comment) {
        LogUtil.create(comment.toString());
        LOGGER.info("添加评论");

        int count = webCommentService.insert(comment);
        if (count > 0 ) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
