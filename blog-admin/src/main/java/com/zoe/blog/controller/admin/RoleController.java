package com.zoe.blog.controller.admin;

import com.zoe.blog.model.Role;
import com.zoe.blog.service.RoleService;
import com.zoe.blog.utils.CommonPage;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 02:35
 */
@Secured("ROLE_ADMIN")
@Api(tags = "RoleController", description = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @ApiOperation("查询角色")
    @GetMapping
    public CommonResult<CommonPage<Role>> list(String roleName,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        List<Role> roles = roleService.list(roleName, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(roles));
    }

    @ApiOperation("搜索角色")
    @GetMapping("/search")
    public CommonResult<CommonPage<Role>> search(String roleName,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        List<Role> roles = roleService.search(roleName, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(roles));
    }


    @ApiOperation("根据id查询角色")
    @GetMapping("/{id}")
    public CommonResult<Role> getOne(@PathVariable Integer id) {
        Role role = roleService.findByRoleId(id);
        return CommonResult.success(role);
    }

    @ApiOperation("添加角色")
    @PostMapping
    public CommonResult insert(@RequestBody @Valid Role role) {
        int count = roleService.insert(role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("更新角色")
    @PutMapping
    public CommonResult update(@RequestBody @Valid Role role) {

        int count = roleService.update(role);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        int count = roleService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
