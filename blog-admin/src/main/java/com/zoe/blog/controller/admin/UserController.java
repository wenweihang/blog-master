package com.zoe.blog.controller.admin;

import com.zoe.blog.dto.ModifyPasswordParam;
import com.zoe.blog.model.User;
import com.zoe.blog.service.UserService;
import com.zoe.blog.utils.CommonPage;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 11:26
 */
@Api(tags = "UserController", description = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @ApiOperation("查询用户")
    @GetMapping
    public CommonResult<CommonPage<User>> list(String username,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        System.out.println("username = " + username);
        List<User> users = userService.list(username, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(users));
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("搜索用户")
    @GetMapping("/search")
    public CommonResult<CommonPage<User>> search(String username,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        List<User> users = userService.search(username, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(users));
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("根据id查询用户")
    @GetMapping("/{id}")
    public CommonResult<User> getOne(@PathVariable Integer id) {
        User user = userService.findByUserId(id);
        return CommonResult.success(user);
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("添加用户")
    @PostMapping
    public CommonResult insert(@RequestBody @Valid User user) {
        int count = userService.insert(user);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("更新用户")
    @PutMapping
    public CommonResult update(@RequestBody @Valid User user) {
        System.out.println("user = " + user);

        int count = userService.update(user);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("更新用户密码")
    @PostMapping("/password")
    public CommonResult updatePassword(@RequestBody @Valid ModifyPasswordParam modifyPasswordParam) {
        System.out.println("modifyPasswordParam = " + modifyPasswordParam);

        int count = userService.updatePassword(modifyPasswordParam.getId(),
                                                modifyPasswordParam.getRawPassword(),
                                                modifyPasswordParam.getNewPassword());
        if (count > 0) {
            return CommonResult.success(count);
        } else if (count == -1){
            return CommonResult.validateFailed("原密码错误");
        } else {
            return CommonResult.validateFailed();
        }

    }

    @Secured("ROLE_ADMIN")
    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        int count = userService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
