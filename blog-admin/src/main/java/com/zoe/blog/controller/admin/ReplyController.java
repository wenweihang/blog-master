package com.zoe.blog.controller.admin;

import com.zoe.blog.model.Reply;
import com.zoe.blog.service.ReplyService;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by liuxuelian on 2020/07/28 15:42
 */
@Api(tags = "ReplyController", description = "回复管理")
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    ReplyService replyService;

    @ApiOperation("添加回复")
    @PostMapping
    public CommonResult insert(@RequestBody @Valid Reply reply) {
        int count = replyService.insert(reply);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("更新回复")
    @PutMapping
    public CommonResult update(@RequestBody @Valid Reply reply) {
        System.out.println("reply = " + reply);

        int count = replyService.update(reply);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除回复")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        int count = replyService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }




}
