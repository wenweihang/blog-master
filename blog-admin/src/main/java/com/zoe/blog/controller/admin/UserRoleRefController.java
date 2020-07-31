package com.zoe.blog.controller.admin;

import com.zoe.blog.dao.RoleDao;
import com.zoe.blog.dto.AssignRoleParam;
import com.zoe.blog.model.Role;
import com.zoe.blog.model.UserRoleRef;
import com.zoe.blog.service.UserRoleRefService;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 11:55
 */
@Secured("ROLE_ADMIN")
@Api(tags = "UserRoleRefController", description = "用户角色关联表管理")
@RestController
@RequestMapping("/assign/role")
public class UserRoleRefController {

    @Autowired
    UserRoleRefService userRoleRefService;

    @Autowired
    RoleDao roleDao;

    @ApiOperation("根据用户id获取角色")
    @GetMapping("/{id}")
    public CommonResult getRoleByUserId(@PathVariable("id") Integer userId) {
        Role roles = userRoleRefService.getRoleByUserId(userId);

        return CommonResult.success(roles);
    }

    @ApiOperation("为用户添加角色")
    @PostMapping
    public CommonResult insert(@RequestBody @Valid AssignRoleParam assignRoleParam) {
        System.out.println("assignRoleParam = " + assignRoleParam);

        Integer roleId = roleDao.findByRoleName(assignRoleParam.getRoleNames()).getId();

        UserRoleRef roleRef = new UserRoleRef();
        roleRef.setUserId(assignRoleParam.getUserId());
        roleRef.setRoleId(roleId);


        int count = userRoleRefService.insert(roleRef);
        if (count > 0) {
            return CommonResult.success(count);
        }

        return CommonResult.failed();
    }



}
