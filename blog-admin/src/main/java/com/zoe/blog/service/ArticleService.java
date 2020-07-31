package com.zoe.blog.service;

import com.github.pagehelper.PageHelper;
import com.zoe.blog.dao.ArticleCategoryRefDao;
import com.zoe.blog.dao.ArticleDao;
import com.zoe.blog.dao.UvDao;
import com.zoe.blog.dto.ArticleQueryParam;
import com.zoe.blog.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/24 18:40
 */
@Service
public class ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Autowired
    ArticleCategoryRefDao articleCategoryRefDao;

    @Autowired
    CommentService commentService;

    @Autowired
    UvDao uvDao;


    public List<Article> list(ArticleQueryParam articleQueryParam) {
        PageHelper.startPage(articleQueryParam.getPageNum(), articleQueryParam.getPageSize());
        return articleDao.getList(articleQueryParam);
    }

    public Article findById(Integer id) {
        // 记录访问量
        articleDao.addOneViews(id);
        uvDao.addOneUv();

        return articleDao.findByArticleId(id);
    }

    public int insert(Article article) {
        articleDao.insert(article);
        return article.getId();
    }

    public int update(Article article) {

        return articleDao.update(article);
    }

    @Transactional
    public int delete(Integer id) {
        // 删除文章前删除文章分类关联表里的文章分类
        articleCategoryRefDao.deleteByArticleId(id);

        // 删除文章前删除文章的评论
        commentService.deleteByArticleId(id);

        return articleDao.delete(id);
    }
}
