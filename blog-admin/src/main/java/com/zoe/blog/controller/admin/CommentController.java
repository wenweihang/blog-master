package com.zoe.blog.controller.admin;

import com.zoe.blog.dao.ReplyDao;
import com.zoe.blog.dto.CommentEnabledParam;
import com.zoe.blog.model.Comment;
import com.zoe.blog.service.CommentService;
import com.zoe.blog.utils.CommonPage;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by liuxuelian on 2020/07/28 14:09
 */
@Api(tags = "CommentController", description = "评论管理")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @ApiOperation("查询评论")
    @GetMapping
    public CommonResult<CommonPage<Comment>> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        List<Comment> comments = commentService.list(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(comments));
    }

    @ApiOperation("更新显示状态")
    @PutMapping
    public CommonResult updateEnabled(@RequestBody @Valid CommentEnabledParam commentEnabledParam) {
        int count = commentService.updateEnabled(commentEnabledParam);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除评论")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        int count = commentService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
