package com.zoe.blog.controller.web;

import com.zoe.blog.dto.WebArchiveDto;
import com.zoe.blog.dto.WebCategoryDto;
import com.zoe.blog.model.Article;
import com.zoe.blog.service.WebArticleService;
import com.zoe.blog.utils.CommonPage;
import com.zoe.blog.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/29 11:36
 */
@Api(tags = "WebArticleController", description = "对文章进行归档、查询和展示")
@RestController
@RequestMapping("/web/article")
public class WebArticleController {

    @Autowired
    WebArticleService webArticleService;

    @ApiOperation("查询文章归档")
    @GetMapping("/archive")
    public CommonResult<CommonPage<WebArchiveDto>> getArchive() {
        List<WebArchiveDto> archives = webArticleService.getArchive();

        return CommonResult.success(CommonPage.restPage(archives));
    }

    @ApiOperation("根据归档日期获取文章")
    @GetMapping("/date")
    public CommonResult<CommonPage<Article>> getArchiveArticle(String date,
                                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        List<Article> articles = webArticleService.getArticlesByArchive(date, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(articles));
    }

    @ApiOperation("查询分类和分类的文章数")
    @GetMapping("/category")
    public CommonResult<CommonPage<WebCategoryDto>> getArticleCountByCategory() {
        List<WebCategoryDto> categoryDtos = webArticleService.getArticleCountByCategory();
        return CommonResult.success(CommonPage.restPage(categoryDtos));
    }

    @ApiOperation("查询文章通过分类id")
    @GetMapping("/category/{id}")
    public CommonResult<CommonPage<Article>> getArticleByCategoryId(@PathVariable("id") Integer categoryId,
                                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                    @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        List<Article> articles = webArticleService.getArticleByCategoryId(categoryId, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(articles));
    }

    @ApiOperation("获取文章总数")
    @GetMapping("/count")
    public CommonResult getArticleCount() {
        int articleCount = webArticleService.getArticleCount();
        return CommonResult.success(articleCount);
    }


    @ApiOperation("根据文章标题查询文章")
    @GetMapping("/search")
    public CommonResult<CommonPage<Article>> searchArticle(String title,
                                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                           @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        List<Article> articles = webArticleService.search(title, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(articles));
    }




}
