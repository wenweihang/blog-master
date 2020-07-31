package com.zoe.blog.controller.admin;

import com.zoe.blog.model.Category;
import com.zoe.blog.service.CategoryService;
import com.zoe.blog.utils.CommonPage;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 19:55
 */
@Api(tags = "CategoryController", description = "分类管理")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @ApiOperation("查询分类")
    @GetMapping
    public CommonResult<CommonPage<Category>> list(String name,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        List<Category> categories = categoryService.list(name, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(categories));
    }

    @ApiOperation("搜索分类")
    @GetMapping("/search")
    public CommonResult<CommonPage<Category>> search(String name,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        List<Category> categories = categoryService.search(name, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(categories));
    }

    @ApiOperation("根据id查询分类")
    @GetMapping("/{id}")
    public CommonResult<Category> getOne(@PathVariable Integer id) {
        Category category = categoryService.findById(id);
        return CommonResult.success(category);
    }

    @ApiOperation("添加分类")
    @PostMapping
    public CommonResult insert(@RequestBody @Valid Category category) {
        int count = categoryService.insert(category);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("更新分类")
    @PutMapping
    public CommonResult update(@RequestBody @Valid Category category) {

        int count = categoryService.update(category);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除分类")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        int count = categoryService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }
}
