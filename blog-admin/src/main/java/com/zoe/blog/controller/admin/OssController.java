package com.zoe.blog.controller.admin;

import com.zoe.blog.dto.OssCallbackResult;
import com.zoe.blog.dto.OssPolicyResult;
import com.zoe.blog.service.OssService;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuxuelian on 2020/07/24 00:47
 */
@Api(tags = "OssController", description = "Oss管理")
@RestController
@RequestMapping("/oss")
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation(value = "oss上传签名生成")
    @GetMapping("/policy")
    public CommonResult<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.policy();
        return CommonResult.success(result);
    }

    @ApiOperation(value = "oss上传成功回调")
    @PostMapping("/callback")
    public CommonResult<OssCallbackResult> callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return CommonResult.success(ossCallbackResult);
    }
}
