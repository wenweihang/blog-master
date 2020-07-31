package com.zoe.blog.controller.admin;

import com.zoe.blog.dto.ArticleQueryParam;
import com.zoe.blog.model.Article;
import com.zoe.blog.service.ArticleService;
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
 * Created by liuxuelian on 2020/07/24 18:44
 */
@Api(tags = "ArticleController", description = "文章管理")
@RestController
@RequestMapping("/article")
public class ArticleController {


    @Autowired
    ArticleService articleService;

    @ApiOperation("查询文章")
    @GetMapping
    public CommonResult<CommonPage<Article>> list( ArticleQueryParam articleQueryParam) {
        List<Article> articles = articleService.list(articleQueryParam);
        return CommonResult.success(CommonPage.restPage(articles));
    }

    @ApiOperation("根据id查询文章")
    @GetMapping("/{id}")
    public CommonResult<Article> getOne(@PathVariable Integer id) {
        Article article = articleService.findById(id);
        return CommonResult.success(article);
    }

    @ApiOperation("添加文章")
    @PostMapping
    public CommonResult insert(@RequestBody @Valid Article article) {
        System.out.println("article = " + article);

        int count = articleService.insert(article);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("更新文章")
    @PutMapping
    public CommonResult update(@RequestBody @Valid Article article) {
        System.out.println("article = " + article);

        int count = articleService.update(article);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("删除文章")
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        System.out.println("id = " + id);

        int count = articleService.delete(id);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

}
