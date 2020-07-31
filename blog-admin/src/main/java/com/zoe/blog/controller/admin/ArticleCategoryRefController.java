package com.zoe.blog.controller.admin;

import com.zoe.blog.dao.CategoryDao;
import com.zoe.blog.dto.AssignCategoryParam;
import com.zoe.blog.model.ArticleCategoryRef;
import com.zoe.blog.model.Category;
import com.zoe.blog.service.ArticleCategoryRefService;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 20:37
 */
@Api(tags = "ArticleCategoryRefController", description = "文章分类管理")
@RestController
@RequestMapping("/assign/category")
public class ArticleCategoryRefController {


    @Autowired
    ArticleCategoryRefService categoryRefService;

    @Autowired
    CategoryDao categoryDao;

    @ApiOperation("根据用户id获取角色")
    @GetMapping("/{id}")
    public CommonResult getRoleByUserId(@PathVariable("id") Integer arcileId) {
        Category category = categoryRefService.getCategoryByArticleId(arcileId);

        return CommonResult.success(category);
    }

    @ApiOperation("为用户添加角色")
    @PostMapping
    public CommonResult insert(@RequestBody @Valid AssignCategoryParam assignCategoryParam) {

        Integer categoryId = categoryDao.findByCategoryName(assignCategoryParam.getCategoryName()).getId();

        ArticleCategoryRef articleCategoryRef = new ArticleCategoryRef();
        articleCategoryRef.setCategoryId(categoryId);
        articleCategoryRef.setArticleId(assignCategoryParam.getArticleId());

        int count = categoryRefService.insert(articleCategoryRef);
        if (count > 0) {
            return CommonResult.success(count);
        }

        return CommonResult.failed();
    }
}
