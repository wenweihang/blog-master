package com.zoe.blog.service;

import com.github.pagehelper.PageHelper;
import com.zoe.blog.dao.WebArticleDao;
import com.zoe.blog.dto.WebArchiveDto;
import com.zoe.blog.dto.WebCategoryDto;
import com.zoe.blog.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liuxuelian on 2020/07/29 11:30
 */
@Service
public class WebArticleService {
    @Autowired
    WebArticleDao webArticleDao;

    public List<WebArchiveDto> getArchive() {
        return webArticleDao.getArchive();
    }

    public List<Article> getArticlesByArchive(String date, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return webArticleDao.findArticleCountByArchive(date);
    }

    public List<WebCategoryDto> getArticleCountByCategory() {
        return webArticleDao.findArticleCountByCategory();
    }

    public List<Article> search(String title, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return webArticleDao.findArticlesByTitle(title);
    }

    /**
     * 获取文章总数
     */
    public int getArticleCount() {
        return webArticleDao.getArticleCount();
    }

    public List<Article> getArticleByCategoryId(Integer categoryId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return webArticleDao.findArticleByCategoryId(categoryId);
    }
}
